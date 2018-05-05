<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

use Carbon\Carbon;

class CheckCache extends Model
{
    protected $table   = 'check_cache';
    public $timestamps = false;
    
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'news_outlet_slug',
        'lower_bound',
        'upper_bound'
    ];

    public function news_outlet() {
        return $this->belongsTo(NewsOutlet::class, 'news_outlet_slug', 'slug');
    }

    public function getLowerBoundAttribute() {
        return Carbon::parse($this->attributes['lower_bound']);
    }

    public function getUpperBoundAttribute() {
        return Carbon::parse($this->attributes['upper_bound']);
    }
}