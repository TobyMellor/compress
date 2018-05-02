<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Article;
use App\CheckCache;

use Carbon\Carbon;

class ArticleController extends Controller
{
    public function index(Request $request) {
        $fromDate = $request->input('from_date');
        $articles;

        $this->crawlNewArticles();

        if ($fromDate) {
            $articles = Article::where('date', '>=', $fromDate)->get();
        } else {
            $articles = Article::all();
        }

        return response()->json([
            'success'  => true,
            'articles' => $articles
        ]);
    }

    private function crawlNewArticles() {
        $latestCrawl = CheckCache::orderBy('created_at', 'desc')->first();

        // If no check has been made, or the last crawl occurred more than 2 minutes ago
        if (!$latestCrawl || Carbon::parse($latestCrawl->created_at)->lt(Carbon::now()->subMinutes(2))) {
            (new CheckCache())->save();

            // TODO: crawl new articles here
        }
    }
}
