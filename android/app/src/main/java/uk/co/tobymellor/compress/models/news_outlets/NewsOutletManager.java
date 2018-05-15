package uk.co.tobymellor.compress.models.news_outlets;

import android.os.AsyncTask;

import org.json.JSONException;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import uk.co.tobymellor.compress.JSONTask;
import uk.co.tobymellor.compress.models.Manager;

public class NewsOutletManager extends Manager {
    private final static String ENDPOINT = "news_outlets";

    private ArrayList<NewsOutlet> newsOutlets = new ArrayList<>();

    public NewsOutletManager() throws InterruptedException, ExecutionException, JSONException, ReflectiveOperationException {
        HashMap<String, String> params = new HashMap<>();

        AsyncTask<String, String, String> task = new JSONTask().execute(super.formUrl(NewsOutletManager.ENDPOINT, params));

        super.populateFromJSON(this, NewsOutlet.class, JSONNewsOutletInput.class, task.get());
    }

    public NewsOutlet get(String slug) {
        for (NewsOutlet newsOutlet : newsOutlets) {
            if (newsOutlet.getSlug().equals(slug)) return newsOutlet;
        }

        return null;
    }

    @Override
    public void add(Object newsOutlet) {
        if (newsOutlet instanceof NewsOutlet) {
            newsOutlets.add((NewsOutlet) newsOutlet);
        }
    }

    @Override
    public void remove(Object newsOutlet) {

    }

    @Override
    public String getEndpoint() {
        return NewsOutletManager.ENDPOINT;
    }
}
