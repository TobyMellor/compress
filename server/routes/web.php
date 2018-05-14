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

$router->get('/', function() {
    return view('user-guide');
});

$router->group(['prefix' => 'api'], function () use ($router) {
    $router->get('articles', 'ArticleController@index');
    $router->get('read_later_articles', 'ReadLaterController@index');

    $router->get('news_outlet_genres', function() {
        return response()->json([
            'success'            => true,
            'news_outlet_genres' => NewsOutletGenre::all()
        ]);
    });

    $router->get('news_outlets', function() {
        return response()->json([
            'success'      => true,
            'news_outlets' => NewsOutlet::all()
        ]);
    });

    $router->get('genres', function() {
        return response()->json([
            'success' => true,
            'genres'  => Genre::all()
        ]);
    });

    $router->post('store-firebase-token', 'FirebaseController@store_token');
});