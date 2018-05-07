<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;

use Validator;

use App\Article;
use App\Author;
use App\CheckCache;
use App\NewsOutlet;
use App\NewsOutletGenre;

use DB;

use App\Http\Controllers\CacheController;
use App\Http\Controllers\ArticleCrawler;
use App\Http\Controllers\ArticleShortener;

use Carbon\Carbon;
use GuzzleHttp\Client;

date_default_timezone_set('GMT');

class ArticleController extends Controller {
    const NEWSAPI_ENDPOINT = 'https://newsapi.org/v2/everything';
    const PAGE_SIZE = 3;
    const NEWSAPI_PAGE_SIZE = 20;

    private $newsOutletsGenreIds = [];
    private $sessionCaches = [];
    private $cacheController;

    public function __construct() {
        $this->cacheController = new CacheController();
    }

    public function index(Request $request) {
        $this->validate($request, [
            'from_date'             => 'nullable|date',
            'to_date'               => 'nullable|date',
            'news_outlet_genre_ids' => 'required'
        ]);

        $from = $request->has('from_date') ? $request->input('from_date') : null;
        $to   = $request->has('to_date')   ? $request->input('to_date')   : Carbon::now();

        $this->newsOutletsGenreIds = explode(',', $request->input('news_outlet_genre_ids'));

        if ($this->shouldCrawl($from, $to)) {
            $this->initiateCrawl($from, $to);
        }

        $articles = Article::whereNotNull('news_outlet_genre_id')
                           ->whereIn('news_outlet_genre_id', $this->newsOutletsGenreIds);

        if ($from) {
            $articles = $articles->where('date', '>=', $from);
        }

        if ($to) {
            $articles = $articles->where('date', '<=', $to);
        }

        return response()->json([
            'success'  => true,
            'articles' => $articles
                            ->orderBy('date', 'desc')
                            ->limit(self::PAGE_SIZE)
                            ->with('author')
                            ->get()
        ]);
    }

    /**
     * If a crawl has not been made in the past two minutes
     * for a news genre, it will perform a crawl.
     *
     * @return void
     */
    private function shouldCrawl(?Carbon $from, Carbon $to) {
        $newsOutlets = $this->getNewsOutletsFromGenreIds($this->newsOutletsGenreIds);

        $widestCheckCaches = $this->getWidestCheckCaches($newsOutlets, $to)->get();
        $twoMinutesFromTo  = $to->subMinutes(2);

        foreach ($widestCheckCaches as $widestCheckCache) {
            if ($widestCheckCache->upper_bound->lt($twoMinutesFromTo)) return true;
        }
        
        return sizeOf($widestCheckCaches) === 0;
    }

    private function initiateCrawl(?Carbon $from, Carbon $to) {
        $newsOutlets = $this->getNewsOutletsFromGenreIds($this->newsOutletsGenreIds);

        $matchedArticlesCount = 0;
        $safeGuard = 0;

        $searchTo = $to;
        $searchFrom;

        while ($matchedArticlesCount < self::PAGE_SIZE && $safeGuard < 4) {
            $widestCheckCache = $this->getWidestCheckCaches($newsOutlets, $searchTo)->whereNotIn('id', $this->sessionCaches)->first();

            if ($widestCheckCache) {
                $searchFrom = $widestCheckCache->upper_bound;

                if ($searchFrom->eq($searchTo)) break;

                $matchedArticlesCount += $this->crawlNewsAPI($widestCheckCache->news_outlet, $searchFrom, $searchTo);

                $searchFrom = $this->getLatestCheckCache()->lower_bound;
            } else {
                $searchFrom = null;
            }

            foreach ($newsOutlets as $newsOutlet) {
                if ($widestCheckCache && $newsOutlet->slug === $widestCheckCache->news_outlet_slug) continue;

                $matchedArticlesCount += $this->crawlNewsAPI($newsOutlet, $searchFrom, $searchTo);
                $latestCheckCache = $this->getLatestCheckCache();

                if (!$searchFrom || $latestCheckCache->lower_bound->gt($searchFrom)) {
                    $searchFrom = $latestCheckCache->lower_bound;
                }
            }

            if ($to && $from) {
                if (!$this->cacheController->isFullyCached(array_column($newsOutlets->toArray(), 'slug'), $from, $to)) break;
            }

            $searchTo = $searchFrom;
            $safeGuard++;
        }

        $unshortenedArticles = Article::where('date', '<=', $to)
                        ->whereNull('short_sentence_summary')
                        ->whereNotNull('news_outlet_genre_id')
                        ->whereIn('news_outlet_genre_id', $this->newsOutletsGenreIds);

        if ($from) {
            $unshortenedArticles->where('date', '>=', $from);
        }

        $unshortenedArticles = $unshortenedArticles
                        ->orderBy('date', 'desc')
                        ->limit(self::PAGE_SIZE)
                        ->get();

        foreach ($unshortenedArticles as $unshortenedArticle) {
            // echo 'Would shorten ' . $unshortenedArticle->article_link . '<br/>';
            // continue;
            $articleShortener = new ArticleShortener($unshortenedArticle->article_link);

            $unshortenedArticle->update([
                'short_sentence_summary' => $articleShortener->getShortSentenceSummary(),
                'long_sentence_summary'  => $articleShortener->getLongSentenceSummary()
            ]);
        }
    }

    /**
     * Performs a crawl for the given news outlet,
     * and caches the result.
     *
     * @return void
     */
    private function crawlNewsAPI(NewsOutlet $newsOutlet, ?Carbon $from, Carbon $to) {
        $client = new Client();
        $matchedArticlesCount = 0;
        $cacheRanges;

        $newsOutletGenreIds = NewsOutletGenre::whereIn('id', $this->newsOutletsGenreIds)
                                            ->where('news_outlet_slug', $newsOutlet->slug)
                                            ->get()
                                            ->pluck('id')
                                            ->toArray();

        $cacheRanges = $this->cacheController->getNewsOutletCacheRanges($newsOutlet->slug, $from, $to);

        foreach ($cacheRanges as $cacheRange) {
            if ($cacheRange['cached']) {
                $matchedArticlesCount += Article::whereIn('news_outlet_genre_id', $newsOutletGenreIds)
                                                ->where('date', '>=', $cacheRange['from'])
                                                ->where('date', '<=', $cacheRange['to'])
                                                ->count();
            } else {
                $queryParams = [
                    'apiKey'   => env('COMPRESS_NEWSAPI_KEY'),
                    'sources'  => $newsOutlet->slug,
                    'to'       => $cacheRange['to']->subSecond(1)->format('Y-m-d\TH:i:s'),
                    'pageSize' => self::NEWSAPI_PAGE_SIZE
                ];

                if (isset($cacheRange['from'])) {
                    $queryParams['from'] = $cacheRange['from']->format('Y-m-d\TH:i:s');
                }

                try {
                    $response = json_decode($client->request('GET', self::NEWSAPI_ENDPOINT, [
                        'query' => $queryParams
                    ])->getBody()->getContents());

                    $articles = $response->articles;

                    foreach ($articles as $article) {
                        $articleCrawler = new ArticleCrawler($article->url, $article->source->id);

                        // If genre is supported
                        if ($articleCrawler->getNewsOutletGenreId() !== null) {
                            $author = null;

                            // Don't create authors that: are null, are a url or contain the company name
                            if ($article->author
                                    && !filter_var($article->author, FILTER_VALIDATE_URL)
                                    && strpos(strtolower($newsOutlet->name), strtolower($article->author)) === false) {
                                $author = Author::updateOrCreate(
                                    [
                                        'name'             => $article->author,
                                        'news_outlet_slug' => $newsOutlet->slug
                                    ],
                                    [
                                        'name'             => $article->author,
                                        'news_outlet_slug' => $newsOutlet->slug
                                    ]
                                )->id;
                            }

                            $article = new Article([
                                'title'                  => $article->title,
                                'author_summary'         => $article->description,
                                'article_link'           => $article->url,
                                'author_id'              => $author,
                                'news_outlet_genre_id'   => $articleCrawler->getNewsOutletGenreId(),
                                'date'                   => $article->publishedAt
                            ]);

                            $article->save();

                            if (in_array($articleCrawler->getNewsOutletGenreId(), $this->newsOutletsGenreIds)) {
                                $matchedArticlesCount++;
                            }
                        }
                    }

                    $lowerBound;

                    if (sizeOf($articles) > 0 && (!isset($cacheRange['from']) || $response->totalResults > self::NEWSAPI_PAGE_SIZE)) {
                        $lowerBound = $articles[sizeOf($articles) - 1]->publishedAt;
                    } else {
                        $lowerBound = $cacheRange['from'];
                    }

                    $checkCache = new CheckCache([
                        'news_outlet_slug' => $newsOutlet->slug,
                        'upper_bound'      => $cacheRange['to']->addSecond(1),
                        'lower_bound'      => $lowerBound
                    ]); 

                    $checkCache->save();

                    $this->sessionCaches[] = $checkCache->id;
                } catch (\GuzzleHttp\Exception\ClientException $e) {
                    die($e->getResponse()->getBody()->getContents());
                }
            }

            if ($matchedArticlesCount >= self::PAGE_SIZE) return $matchedArticlesCount;
        }

        return $matchedArticlesCount;
    }

    private function getWidestCheckCaches($newsOutlets, $searchTo) {
        return CheckCache::whereIn('check_cache.news_outlet_slug', array_column($newsOutlets->toArray(), 'slug'))
                        ->where('check_cache.upper_bound', '<=', $searchTo)
                        ->join(
                            DB::raw('
                                (SELECT MAX(upper_bound) AS upper_bound, news_outlet_slug
                                FROM check_cache
                                GROUP BY news_outlet_slug) `inner`
                            '),
                            function($join) {
                                $join->on('check_cache.upper_bound', '=', 'inner.upper_bound');
                                $join->on('check_cache.news_outlet_slug', '=', 'inner.news_outlet_slug');
                            }
                        )
                        ->orderBy('check_cache.upper_bound', 'asc');
    }

    private function getNewsOutletsFromGenreIds($newsOutletsGenreIds) {
        return NewsOutlet::whereHas('news_outlet_genre', function($query) use ($newsOutletsGenreIds) {
                        $query->whereIn('id', $newsOutletsGenreIds);
                    })
                    ->groupBy('slug')
                    ->get();
    }

    private function getLatestCheckCache() {
        return CheckCache::orderBy('id', 'desc')->first();
    }
}
