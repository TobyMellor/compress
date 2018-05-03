<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Article;
use App\CheckCache;
use App\NewsOutlet;
use App\NewsOutletGenre;

use Carbon\Carbon;

class ArticleController extends Controller
{
    public function index(Request $request) {
        $this->validate($request, [
            'date'                  => 'nullable|date',
            'news_outlet_genre_ids' => 'required'
        ]);

        $fromDate           = $request->input('from_date');
        $newsOutletGenreIds = explode(',', $request->input('news_outlet_genre_ids'));

        $this->crawlNewsOutlets($newsOutletGenreIds);

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


    /**
     * If a crawl has not been made in the past two minutes
     * for a news genre, it will perform a crawl.
     *
     * @return void
     */
    private function crawlNewsOutlets($newsOutletGenres) {

        // If there are any news outlets to crawl
        if ($newsOutletGenres) {

            // Return the news outlets that source the given news outlet genres
            $newsOutlet = NewsOutletGenre::whereIn('id', $newsOutletGenres)
                                         ->groupBy('news_outlet_id');

            if ($newsOutlet->count() > 0) {
                $newsOutletIds = $newsOutlet->pluck('news_outlet_id'); // The IDs of those news outlets
                $latestCrawls  = CheckCache::whereIn('news_outlet_id', $newsOutletIds)
                                           ->orderBy('id', 'desc')
                                           ->get()
                                           ->unique('news_outlet_id'); // Gets the latest crawl for each news outlet

                // It's possible a news outlet has never been crawled before
                // Assume no news outlet has been cached, until we loop
                // through latestCrawls
                $notCrawled = array_values($newsOutletIds->toArray());

                foreach ($latestCrawls as $latestCrawl) {
                    $newsOutletId = $latestCrawl->news_outlet->id;
                    $notCrawled = array_diff($notCrawled, [$newsOutletId]); // Remove the news outlet from notCrawled, since it's been crawled before

                    // If a crawl has not been made for that news outlet in the past 2 minutes
                    if (Carbon::parse($latestCrawl->created_at)->lt(Carbon::now()->subMinutes(2))) {
                        $this->crawlNewsOutlet($newsOutletId);
                    }
                }

                // Crawl the news outlets that haven't been crawled before
                foreach ($notCrawled as $newsOutletId) {
                    $this->crawlNewsOutlet($newsOutletId);
                }
            }
        }
    }

    /**
     * Performs a crawl for the given news outlet,
     * and caches the result.
     *
     * @return void
     */
    private function crawlNewsOutlet($newsOutletId) {


        // Indicate to future requests that we have now cached this news outlet
        $checkCache = new CheckCache([
            'news_outlet_id' => $newsOutletId
        ]);

        $checkCache->save();
    }
}
