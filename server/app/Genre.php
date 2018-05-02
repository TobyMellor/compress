<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Genre extends Model
{
    protected $table = 'genre';

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