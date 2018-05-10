<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Article;

class ReadLaterController extends Controller {
    public function __construct() {
        $this->cacheController = new CacheController();
    }

    public function index(Request $request) {
        $this->validate($request, [
            'article_ids' => 'required'
        ]);

        $articleIds = explode(',', $request->input('article_ids'));

        $articles = Article::whereIn('id', $articleIds)
                           ->with('author')
                           ->get();

        return response()->json([
            'success'  => true,
            'articles' => $articles
        ]);
    }
}