package uk.co.tobymellor.compress.models.genres;

import android.os.AsyncTask;

import org.json.JSONException;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import uk.co.tobymellor.compress.JSONTask;
import uk.co.tobymellor.compress.models.Manager;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

public class GenreManager extends Manager {
    private final static String ENDPOINT = "genres";

    private ArrayList<Genre> genres = new ArrayList<>();

    public GenreManager() throws InterruptedException, ExecutionException, JSONException, ReflectiveOperationException {
        HashMap<String, String> params = new HashMap<>();

        AsyncTask<String, String, String> task = new JSONTask().execute(super.formUrl(GenreManager.ENDPOINT, params));

        super.populateFromJSON(this, Genre.class, JSONGenreInput.class, task.get());
    }

    public Genre get(String slug) {
        for (Genre genre : genres) {
            if (genre.getSlug().equals(slug)) return genre;
        }

        return null;
    }

    @Override
    public void add(Object genre) {
        if (genre instanceof Genre) {
            genres.add((Genre) genre);
        }
    }

    @Override
    public void remove(Object genre) {

    }

    @Override
    public String getEndpoint() {
        return GenreManager.ENDPOINT;
    }
}
