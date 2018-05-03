package uk.co.tobymellor.compress.models.articles;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import uk.co.tobymellor.compress.JSONTask;
import uk.co.tobymellor.compress.models.Manager;

public class ArticleManager extends Manager {
    private HashSet<Article> articles = new HashSet<>();

    private final static String ENDPOINT = "article";
    private final static String ENDPOINT_PLURAL = "articles";

    public ArticleManager() throws InterruptedException, ExecutionException, JSONException {
        HashMap<String, String> params = new HashMap<>();

        params.put("date", "2018-04-02 00:00:00");     // TODO: Replace with cached date
        params.put("news_outlet_genre_ids", "1,2,14"); // TODO: Replace with current news_outlet_genre_ids

        AsyncTask<String, String, String> task = new JSONTask().execute(super.formUrl(ArticleManager.ENDPOINT, params));

        JSONObject json = new JSONObject(task.get()); // TODO: Deal with this asynchronously instead of calling .get

        JSONArray articles = json.getJSONArray(ArticleManager.ENDPOINT_PLURAL);

        for (int i = 0; i < articles.length(); i++) {
            JSONObject jsonArticle = articles.getJSONObject(i);

            Article article = new Article(new JSONArticleInput(jsonArticle));

            add(article);
        }
    }

    public HashSet<Article> getArticles() {
        return articles;
    }

    @Override
    public void add(Object article) {
        if (article instanceof Article) {
            articles.add((Article) article);
        }
    }

    @Override
    public void remove(Object article) {
        if (article instanceof Article) {
            articles.remove(article);
        }
    }

    @Override
    public String getEndpoint() {
        return ENDPOINT;
    }
}
