<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class FirebaseToken extends Model
{
    protected $table   = 'firebase_token';
    public $timestamps = false;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'token'
    ];
}