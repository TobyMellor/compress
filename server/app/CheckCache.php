<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class CheckCache extends Model
{
    protected $table = 'check_cache';
    public $timestamps = false;
    
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'news_outlet_slug'
    ];

    public function news_outlet() {
        return $this->belongsTo(NewsOutlet::class, 'news_outlet_slug', 'slug');
    }
}