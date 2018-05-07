<?php

namespace App\Http\Controllers;

use GuzzleHttp\Client;
use GuzzleHttp\TransferStats;

class ArticleShortener {
    const SMMRY_ENDPOINT       = 'http://api.smmry.com';
    const SHORT_SENTENCE_COUNT = 3;
    const LONG_SENTENCE_COUNT  = 7;

    private $articleLink;

    // The attributes that will be populated by this class
    private $shortSentenceSummary = null;
    private $longSentenceSummary = null;

    public function __construct($articleLink) {
        $this->articleLink = $articleLink;

        $this->shortSentenceSummary = $this->shorten(self::SHORT_SENTENCE_COUNT);

        // Don't bother retrieving the longer summary if the article couldn't be shortened to self::SHORT_SENTENCE_COUNT sentences
        if (substr_count($this->shortSentenceSummary, '[BREAK]') <= self::SHORT_SENTENCE_COUNT) {
            $longSentenceSummary = $this->shorten(self::LONG_SENTENCE_COUNT);

            if ($longSentenceSummary !== $this->shortSentenceSummary) {
                $this->longSentenceSummary = $longSentenceSummary;
            }
        }
    }
    
    public function getArticleLink() {
        return $this->articleLink;
    }

    public function getShortSentenceSummary() {
        return $this->shortSentenceSummary;
    }

    public function getLongSentenceSummary() {
        return $this->longSentenceSummary;
    }

    private function shorten($sentenceCount) {
        $client = new Client();

        try {
            $response = json_decode($client->request('GET', self::SMMRY_ENDPOINT, [
                'query' => [
                    'SM_API_KEY'    => env('COMPRESS_SMMRY_KEY'),
                    'SM_LENGTH'     => $sentenceCount,
                    'SM_WITH_BREAK' => true,
                    'SM_URL'        => $this->articleLink
                ]
            ])->getBody()->getContents());

            if (isset($response->sm_api_error)) {
                return $response->sm_api_error;
            }

            return $response->sm_api_content;
        } catch (\GuzzleHttp\Exception\ClientException $e) {
            var_dump($e->getResponse()->getBody()->getContents());
        }

        return null;
    }
}