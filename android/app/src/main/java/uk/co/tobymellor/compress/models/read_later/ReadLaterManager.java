package uk.co.tobymellor.compress.models.read_later;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;

import org.json.JSONException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import uk.co.tobymellor.compress.JSONTask;
import uk.co.tobymellor.compress.models.Manager;
import uk.co.tobymellor.compress.models.articles.Article;
import uk.co.tobymellor.compress.models.articles.ArticleManager;
import uk.co.tobymellor.compress.models.articles.JSONArticleInput;

public class ReadLaterManager extends Manager {
    public final static String PREFERENCES_FILE = "read_later";
    private final static String PREFERENCE_ARTICLE_IDS = "article_ids";
    private final static String ENDPOINT = "read_later_articles";

    private final SharedPreferences sharedPreferences;

    public ReadLaterManager(SharedPreferences sharedPreferences) throws InterruptedException, ExecutionException, JSONException, ReflectiveOperationException {
        this.sharedPreferences = sharedPreferences;

        String articleIds = getReadLaterArticleIdsString();

        if (articleIds.length() > 0) {
            HashMap<String, String> params = new HashMap<>();

            params.put("article_ids", articleIds);

            AsyncTask<String, String, String> task = new JSONTask().execute(super.formUrl(ReadLaterManager.ENDPOINT, params));

            super.populateFromJSON(this, Article.class, JSONArticleInput.class, task.get());
        }
    }

    public void addToReadLater(Article article) {
        List<String> articleIds = getReadLaterArticleIds();

        articleIds.add(Integer.toString(article.getId()));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERENCE_ARTICLE_IDS, TextUtils.join(",", articleIds));
        editor.apply();
    }

    private String getReadLaterArticleIdsString() {
        return sharedPreferences.getString(ReadLaterManager.PREFERENCE_ARTICLE_IDS, "");
    }

    private List<String> getReadLaterArticleIds() {
        String preference = getReadLaterArticleIdsString();

        return Arrays.asList(preference.split("\\s*,\\s*"));
    }

    @Override
    public void add(Object object) {
        //
    }

    @Override
    public void remove(Object object) {
        //
    }

    @Override
    public String getEndpoint() {
        return ReadLaterManager.ENDPOINT;
    }
}
