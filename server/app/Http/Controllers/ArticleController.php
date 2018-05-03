<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;

use Validator;

use App\Article;
use App\CheckCache;
use App\NewsOutlet;
use App\NewsOutletGenre;

use Carbon\Carbon;
use GuzzleHttp\Client;

date_default_timezone_set('Europe/London');

class ArticleController extends Controller
{
    public function index(Request $request) {
        $this->validate($request, [
            'date'                  => 'nullable|date',
            'news_outlet_genre_ids' => 'required'
        ]);

        $fromDate           = $request->input('from_date');
        $newsOutletGenreIds = explode(',', $request->input('news_outlet_genre_ids'));

        $this->shouldCrawl($newsOutletGenreIds);

        if ($fromDate) {
            $articles = Article::where('date', '>=', $fromDate)
                               ->orderBy('date', 'desc')
                               ->limit(20)
                               ->get();
        } else {
            $articles = Article::limit(20)
                               ->get();
        }

        return response()->json([
            'success'  => true,
            'articles' => $articles
        ]);
    }


    /**
     * If a crawl has not been made in the past two minutes
     * for a news genre, it will perform a crawl.
     *
     * @return void
     */
    private function shouldCrawl($newsOutletGenres) {

        // If there are any news outlets to crawl
        if ($newsOutletGenres) {

            // Return the news outlets that source the given news outlet genres
            $newsOutletGenres = NewsOutletGenre::whereIn('news_outlet_genre.id', $newsOutletGenres)
                                         ->groupBy('news_outlet_id');

            if ($newsOutletGenres->count() > 0) {
                $newsOutletSlugs = $newsOutletGenres
                                           ->join('news_outlet', 'news_outlet.id', '=', 'news_outlet_genre.news_outlet_id')
                                           ->pluck('news_outlet.slug'); // The Slugs of those news outlets

                $latestCrawls = CheckCache::whereIn('news_outlet_slug', $newsOutletSlugs)
                                           ->orderBy('id', 'desc')
                                           ->get()
                                           ->unique('news_outlet_slug'); // Gets the latest crawl for each news outlet

                // It's possible a news outlet has never been crawled before
                // Assume no news outlet has been cached, until we loop
                // through latestCrawls
                $toCrawl         = array_values($newsOutletSlugs->toArray());
                $oldestCrawlDate = null;

                foreach ($latestCrawls as $latestCrawl) {
                    $crawlDate = Carbon::parse($latestCrawl->created_at);

                    if (!$oldestCrawlDate || $oldestCrawlDate->gt($crawlDate)) {
                        $oldestCrawlDate = $crawlDate;
                    }

                    // If a crawl has not been made for that news outlet in the past 2 minutes
                    if ($crawlDate->gt(Carbon::now()->subMinutes(1))) {
                        $toCrawl = array_diff($toCrawl, [$latestCrawl->news_outlet->slug]); // Remove the news outlet from notCrawled, since it's been crawled before
                    }
                }

                if (sizeOf($toCrawl) > 0) {

                    // Crawl the news outlets that haven't been crawled before
                    $this->crawl($toCrawl, $oldestCrawlDate ? $oldestCrawlDate->format('Y-m-d H:i:s') : null);
                }
            }
        }
    }

    /**
     * Performs a crawl for the given news outlet,
     * and caches the result.
     *
     * @return void
     */
    private function crawl($newsOutletSlugs, $from) {
        $client = new Client();

        $queryParams = [
            'apiKey'   => env('COMPRESS_NEWSAPI_KEY'),
            'sources'  => implode(',', $newsOutletSlugs),
            'pageSize' => 20
        ];

        if ($from) {
            $queryParams['from'] = $from;
        }

        try {
            $response = json_decode($client->request('GET', 'https://newsapi.org/v2/everything', [
                'query' => $queryParams
            ])->getBody()->getContents());

            foreach ($response->articles as $article) {
                if ($article->author) {
                    // TODO: Create author
                }

                $article = new Article([
                    'title'                  => $article->title,
                    'author_summary'         => $article->description,
                    'three_sentence_summary' => 'Coming soon...',
                    'seven_sentence_summary' => 'Coming soon...',
                    'article_link'           => $article->url,
                    'author_id'              => null,
                    'news_outlet_genre_id'   => 1,
                    'date'                   => $article->publishedAt
                ]);

                $article->save();
            }

            // Indicate to future requests that we have now cached this news outlet
            foreach ($newsOutletSlugs as $slug) {
                $checkCache = new CheckCache([
                    'news_outlet_slug' => $slug
                ]);

                $checkCache->save();
            }
        } catch (\GuzzleHttp\Exception\ClientException $e) {
            var_dump($e->getResponse()->getBody()->getContents());
        }
    }
}
