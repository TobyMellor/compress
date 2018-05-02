<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Article extends Model
{
    protected $table = 'article';

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
        'author_id',
        'news_outlet_genre_id',
        'date'
    ];

    /**
     * Get the news outlet/genre this record is referencing
     */
    public function news_outlet_genre() {
        return $this->belongsTo(NewsOutletGenre::class, 'news_outlet_genre_id', 'id');
    }

    /**
     * Get the Author that wrote this article
     */
    public function author() {
        return $this->belongsTo(Author::class, 'author_id', 'id');
    }
}