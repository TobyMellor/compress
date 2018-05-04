package uk.co.tobymellor.compress.models.genres;

import android.os.AsyncTask;

import org.json.JSONException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import uk.co.tobymellor.compress.JSONTask;
import uk.co.tobymellor.compress.models.Manager;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

public class GenreManager extends Manager {
    private final static String ENDPOINT = "genres";

    private HashSet<Genre> genres = new HashSet<>();

    public GenreManager() throws InterruptedException, ExecutionException, JSONException, ReflectiveOperationException {
        HashMap<String, String> params = new HashMap<>();

        AsyncTask<String, String, String> task = new JSONTask().execute(super.formUrl(GenreManager.ENDPOINT, params));

        super.populateFromJSON(this, Genre.class, JSONGenreInput.class, task.get());
    }

    @Override
    public Genre get(int id) {
        for (Genre genre : genres) {
            if (genre.getId() == id) return genre;
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
