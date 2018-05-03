<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class CheckCache extends Model
{
    protected $table = 'check_cache';
    
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'news_outlet_id'
    ];

    public function news_outlet() {
        return $this->belongsTo(NewsOutlet::class, 'news_outlet_id', 'id');
    }
}