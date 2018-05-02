<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Article extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'title',
        'author_summary',
        'three_sentence_summary',
        'seven_sentence_summary',
        'article_link',
        'genre_id',
        'author_id',
        'news_outlet_id',
        'date'
    ];

    /**
     * Get the Genre this article is in
     */
    public function genre() {
        return $this->belongsTo(Genre::class, 'genre_id', 'id');
    }

    /**
     * Get the Author that wrote this article
     */
    public function author() {
        return $this->belongsTo(Author::class, 'author_id', 'id');
    }

    /**
     * Get the News Outlet this article is from
     */
    public function news_outlet() {
        return $this->belongsTo(NewsOutlet::class, 'news_outlet_id', 'id');
    }
}