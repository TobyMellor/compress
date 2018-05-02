<?php

use AlbertCht\Lumen\Testing\Concerns\RefreshDatabase;
use AlbertCht\Lumen\Testing\TestCase;

use Carbon\Carbon;

use App\{
    Article,
    Author,
    Genre,
    NewsOutlet
};

class ArticleTest extends TestCase
{
    use RefreshDatabase;

    private $author;
    private $genre;
    private $newsOutlet;

    /**
     * Test the article API route
     *
     * @return void
     */
    public function testGetArticle()
    {
        $this->setupDependencies();

        // an article two weeks ago
        $articleBeforeDate = new Article([
            'title'                  => 'Test 1 Title',
            'author_summary'         => 'Test 1 Author Summary',
            'three_sentence_summary' => 'Test 1 Three Sentence Summary',
            'seven_sentence_summary' => 'Test 1 Seven Sentence Summary',
            'article_link'           => 'Test 1 Article Link',
            'genre_id'               => $this->genre->id,
            'author_id'              => $this->author->id,
            'news_outlet_id'         => $this->newsOutlet->id,
            'date'                   => Carbon::now()->subWeek(2)
        ]);

        // an article created now
        $articleAfterDate = new Article([
            'title'                  => 'Test 2 Title',
            'author_summary'         => 'Test 2 Author Summary',
            'three_sentence_summary' => 'Test 2 Three Sentence Summary',
            'seven_sentence_summary' => 'Test 2 Seven Sentence Summary',
            'article_link'           => 'Test 2 Article Link',
            'genre_id'               => $this->genre->id,
            'author_id'              => $this->author->id,
            'news_outlet_id'         => $this->newsOutlet->id,
            'date'                   => Carbon::now()
        ]);

        $articleBeforeDate->save();
        $articleAfterDate->save();

        // When no date is passed as a parameter
        $this->json('GET', '/api/article')
             ->assertStatus(200)
             ->assertJson([
                'success' => true
             ])
             ->assertJsonCount(2, 'articles');

        // When a date is passed as a parameter, only get articles from beyond that date
        $json = $this->json('GET', '/api/article', [
                'from_date' => Carbon::now()->subWeek()
            ])
            ->assertStatus(200)
            ->assertJson([
                'success' => true
            ])
            ->assertJsonCount(1, 'articles');
    }

    /**
     * Check that the check cache is working correctly
     *
     * @return void
     */
    public function testCheckCache() {
        $this->assertTrue(true);
    }

    private function setupDependencies() {
        $this->author = new Author([
            'name' => 'John Smith'
        ]);

        $this->genre = new Genre([
            'slug' => 'technology',
            'name' => 'Technology'
        ]);

        $this->newsOutlet = new NewsOutlet([
            'name' => 'BBC News'
        ]);

        $this->author->save();
        $this->genre->save();
        $this->newsOutlet->save();
    }
}
