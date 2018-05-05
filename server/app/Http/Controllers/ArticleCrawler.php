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

    public function __construct($articleLink, $newsOutletSlug) {
        $this->articleLink    = $articleLink;
        $this->newsOutletSlug = $newsOutletSlug;

        $this->crawl();
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

    private function crawl() {
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
                    });

                if ($newsOutletGenres->count() > 0) {
                    $this->newsOutletGenreId = $newsOutletGenres->first()->id;
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

    private function getCamelCaseFromSlug($slug) {
        return ucfirst(preg_replace_callback('/[-_](.)/', function($matches) {
            return strtoupper($matches[1]);
        }, $slug));
    }
}