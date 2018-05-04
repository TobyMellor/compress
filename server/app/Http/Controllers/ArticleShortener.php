<?php

namespace App\Http\Controllers;

class ArticleShortener {
    private $fullArticleBody;

    // The attributes that will be populated by this class
    private $threeSentenceSummary;
    private $sevenSentenceSummary;

    public function __construct($fullArticleBody) {
        $this->articleLink    = $articleLink;

        $this->shorten();
    }
    
    public function getFullArticleBody() {
        return $this->fullArticleBody;
    }

    public function getThreeSentenceSummary() {
        return $this->threeSentenceSummary;
    }

    public function getSevenSentenceSummary() {
        return $this->sevenSentenceSummary;
    }

    private function crawl() {
        $client         = new Client();
        $newsOutletSlug = $this->newsOutletSlug;

        try {
            $response = $client->request('GET', $this->articleLink, [
                'query' => [
                    
                ]
            ])->getBody()->getContents();
        } catch (\GuzzleHttp\Exception\ClientException $e) {
            var_dump($e->getResponse()->getBody()->getContents());
        }
    }
}