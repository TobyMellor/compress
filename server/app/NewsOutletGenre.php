<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class NewsOutletGenre extends Model
{
    protected $table = 'news_outlet_genre';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'genre_id',
        'news_outlet_id'
    ];

    /**
     * Get the news outlet this record is referencing
     */
    public function news_outlet() {
        return $this->belongsTo(NewsOutlet::class, 'news_outlet_id', 'id');
    }

    /**
     * Get the genre this record is referencing
     */
    public function genre() {
        return $this->belongsTo(Genre::class, 'genre_id', 'id');
    }
}