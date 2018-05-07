<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class NewsOutletGenre extends Model
{
    protected $table     = 'news_outlet_genre';

    public $timestamps   = false;
    public $incrementing = false;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'genre_slug',
        'news_outlet_slug'
    ];

    /**
     * Get the news outlet this record is referencing
     */
    public function news_outlet() {
        return $this->belongsTo(NewsOutlet::class, 'news_outlet_slug', 'slug');
    }

    /**
     * Get the genre this record is referencing
     */
    public function genre() {
        return $this->belongsTo(Genre::class, 'genre_slug', 'slug');
    }
}