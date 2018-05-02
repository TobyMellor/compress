<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Genre extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'slug',
        'name'
    ];

    /**
     * Get the articles that fall under this Genre
     */
    public function article() {
        return $this->hasMany(Article::class, 'genre_id', 'id');
    }
}