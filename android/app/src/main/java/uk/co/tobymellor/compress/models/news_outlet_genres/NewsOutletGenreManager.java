package uk.co.tobymellor.compress.models.news_outlet_genres;

import android.os.AsyncTask;

import org.json.JSONException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import uk.co.tobymellor.compress.JSONTask;
import uk.co.tobymellor.compress.models.Manager;
import uk.co.tobymellor.compress.models.genres.Genre;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

public class NewsOutletGenreManager extends Manager {
    private HashSet<NewsOutletGenre> newsOutletGenres = new HashSet<>();
    private HashSet<NewsOutlet> newsOutlets = new HashSet<>();
    private HashSet<Genre> genres = new HashSet<>();

    private final static String ENDPOINT = "news_outlet_genre";

    public NewsOutletGenreManager() throws InterruptedException, ExecutionException {
        HashMap<String, String> params = new HashMap<>();

        AsyncTask<String, String, String> task = new JSONTask().execute(super.formUrl(NewsOutletGenreManager.ENDPOINT, params));

        try {
            super.populateFromJSON(this, NewsOutletGenre.class, JSONNewsOutletGenreInput.class, task.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashSet<NewsOutletGenre> getNewsOutletGenres() {
        return newsOutletGenres;
    }

    @Override
    public void add(Object article) {
        //
    }

    @Override
    public void remove(Object article) {
        //
    }

    @Override
    public String getEndpoint() {
        return ENDPOINT;
    }
}
