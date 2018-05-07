<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Genre extends Model
{
    protected $table      = 'genre';
    protected $primaryKey = 'slug';

    public $timestamps   = false;
    public $incrementing = false;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'slug',
        'name'
    ];

    public function news_outlet_genre() {
        return $this->hasMany(NewsOutletGenre::class, 'genre_slug', 'slug');
    }
}