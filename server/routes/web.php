<?php

use App\NewsOutlet;
use App\Genre;
use App\NewsOutletGenre;

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It is a breeze. Simply tell Lumen the URIs it should respond to
| and give it the Closure to call when that URI is requested.
|
*/

$router->get('/', function () use ($router) {
    return $router->app->version();
});

$router->group(['prefix' => 'api'], function () use ($router) {
    $router->get('articles', 'ArticleController@index');

    $router->get('news_outlet_genres', function() {
        return NewsOutletGenre::all();
    });

    $router->get('news_outlets', function() {
        return NewsOutlet::all();
    });

    $router->get('genres', function() {
        return Genre::all();
    });
});