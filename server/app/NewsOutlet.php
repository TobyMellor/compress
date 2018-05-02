<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class NewsOutlet extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'name'
    ];

    /**
     * Get the articles by this news outlet
     */
    public function article() {
        return $this->hasMany(Article::class, 'news_outlet_id', 'id');
    }
}