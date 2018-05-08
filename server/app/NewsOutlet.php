<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class NewsOutlet extends Model
{
    protected $table      = 'news_outlet';
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

    protected $appends = [
        'image_link'
    ];

    /**
     * Get the articles by this news outlet
     */
    public function article() {
        return $this->hasMany(Article::class, 'news_outlet_slug', 'slug');
    }

    public function news_outlet_genre() {
        return $this->hasMany(NewsOutletGenre::class, 'news_outlet_slug', 'slug');
    }

    public function getGenreSlugs($newsOutletGenreIds) {
        return Genre::whereHas('news_outlet_genre', function($query) use ($newsOutletGenreIds) {
                        $query->whereIn('id', $newsOutletGenreIds);
                    })
                    ->pluck('slug')
                    ->toArray();
    }

    public function getImageLinkAttribute() {
        return url('images/news_outlets/' . $this->attributes['slug'] . '.jpg');
    }
}