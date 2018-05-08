<?php

namespace App\Http\Controllers;

use App\NewsOutletGenre;

use GuzzleHttp\Client;

use DOMWrap\Document;

class ArticleCrawler {
    const USER_AGENT = 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1';

    private $articleLink;
    private $newsOutletSlug;

    // The attributes that will be populated by this class
    private $newsOutletGenreId;
    private $authorImageLink = null;

    private $excludedUrls = [
        'jobs.mashable.com',
        'bbc.co.uk/sport/'
    ];

    public function __construct($articleLink, $newsOutletSlug, $shouldCrawlAuthor) {
        $this->articleLink     = $articleLink;
        $this->newsOutletSlug  = $newsOutletSlug;

        foreach ($this->excludedUrls as $excludedUrl) {
            if (strpos($articleLink, $excludedUrl) !== false) {
                $this->newsOutletGenreId = null;

                return;
            }
        }


        $this->crawl($shouldCrawlAuthor);
    }

    public function getArticleLink() {
        return $this->articleLink;
    }

    public function getNewsOutletSlug() {
        return $this->newsOutletSlug;
    }

    public function getNewsOutletGenreId() {
        return $this->newsOutletGenreId;
    }

    public function getAuthorImageLink() {
        return $this->authorImageLink;
    }

    private function crawl($shouldCrawlAuthor) {
        $client         = new Client();
        $newsOutletSlug = $this->newsOutletSlug;

        try {
            $response = $client->request('GET', $this->articleLink, [
                'headers' => [
                    'User-Agent' => self::USER_AGENT
                ]
            ])->getBody()->getContents();

            $crawlMethod = 'crawl' . $this->getCamelCaseFromSlug($newsOutletSlug);
            
            if (method_exists($this, $crawlMethod)) {
                $doc = new Document();
                $doc->html($response);
                
                $genre     = call_user_func([$this, $crawlMethod], $doc);
                $genreSlug = strtolower(str_replace(' ', '-', $genre));
                
                $newsOutletGenres = NewsOutletGenre::whereHas('news_outlet', function($query) use ($newsOutletSlug) {
                        $query->where('news_outlet.slug', $newsOutletSlug);
                    })
                    ->whereHas('genre', function($query) use ($genreSlug) {
                        $query->where('genre.slug', $genreSlug);
                    })
                    ->first();

                if ($newsOutletGenres) {
                    $this->newsOutletGenreId = $newsOutletGenres->id;

                    if ($shouldCrawlAuthor) {
                        $crawlMethodAuthorPicture = $crawlMethod . 'AuthorPicture';
                        
                        if (method_exists($this, $crawlMethodAuthorPicture)) {
                            $this->authorImageLink = call_user_func([$this, $crawlMethodAuthorPicture], $doc);
                        }
                    }
                }
            }
        } catch (\GuzzleHttp\Exception\ClientException $e) {
            var_dump($e->getResponse()->getBody()->getContents());
        }
    }

    private function crawlBBCNews(Document $doc) {
        return $doc->find('.navigation-wide-list__link.navigation-arrow--open span')->text();
    }

    private function crawlMashable(Document $doc) {
        return $doc->find('.page-header > h2')->text();
    }

    private function crawlBBCNewsAuthorPicture(Document $doc) {
        return null;
    }

    private function crawlMashableAuthorPicture(Document $doc) {
        return $doc->find('.article-info img.author_image')->attr('src');
    }

    private function getCamelCaseFromSlug($slug) {
        return ucfirst(preg_replace_callback('/[-_](.)/', function($matches) {
            return strtoupper($matches[1]);
        }, $slug));
    }
}