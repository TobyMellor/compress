package uk.co.tobymellor.compress.models.articles;

import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import uk.co.tobymellor.compress.ComPressAsyncLoader;
import uk.co.tobymellor.compress.JSONTask;
import uk.co.tobymellor.compress.MainActivity;

public class ArticleLoader extends ComPressAsyncLoader<ArrayList<Article>> {

    private static Date lastRequest = null;
    private final static String ENDPOINT = "articles";

    public ArticleLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Article> loadInBackground() {
        HashMap<String, String> params = new HashMap<>();

        params.put("news_outlet_genre_ids", "1,2,12"); // TODO: Replace with current news_outlet_genre_ids

        if (ArticleLoader.lastRequest != null) {
            params.put("from_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK).format(lastRequest));
        }

        final AsyncTask<String, String, String> task = new JSONTask().execute(super.formUrl(ArticleLoader.ENDPOINT, params));

        ArticleLoader.lastRequest = new Date();

        try {
            populateFromJSON(MainActivity.getArticleManager(), Article.class, JSONArticleInput.class, task.get());
        } catch (InterruptedException | ExecutionException | JSONException | ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return null;
    }
}