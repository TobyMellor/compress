package uk.co.tobymellor.compress.models.news_outlet_genres;

import android.os.AsyncTask;

import org.json.JSONException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import uk.co.tobymellor.compress.JSONTask;
import uk.co.tobymellor.compress.models.Manager;
import uk.co.tobymellor.compress.models.articles.Article;

public class NewsOutletGenreManager extends Manager {
    private final static String ENDPOINT = "news_outlet_genres";

    private HashSet<NewsOutletGenre> newsOutletGenres = new HashSet<>();

    public NewsOutletGenreManager() throws InterruptedException, ExecutionException, JSONException, ReflectiveOperationException {
        HashMap<String, String> params = new HashMap<>();

        AsyncTask<String, String, String> task = new JSONTask().execute(super.formUrl(NewsOutletGenreManager.ENDPOINT, params));

        super.populateFromJSON(this, NewsOutletGenre.class, JSONNewsOutletGenreInput.class, task.get());
    }

    public HashSet<NewsOutletGenre> getNewsOutletGenres() {
        return newsOutletGenres;
    }

    @Override
    public NewsOutletGenre get(int id) {
        for (NewsOutletGenre newsOutletGenre : newsOutletGenres) {
            if (newsOutletGenre.getId() == id) return newsOutletGenre;
        }

        return null;
    }

    @Override
    public void add(Object newsOutletGenre) {
        if (newsOutletGenre instanceof NewsOutletGenre) {
            newsOutletGenres.add((NewsOutletGenre) newsOutletGenre);
        }
    }

    @Override
    public void remove(Object article) {
        //
    }

    @Override
    public String getEndpoint() {
        return NewsOutletGenreManager.ENDPOINT;
    }
}
