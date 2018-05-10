package uk.co.tobymellor.compress.models.articles;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import uk.co.tobymellor.compress.JSONTask;
import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.models.Manager;

public class ArticleManager extends Manager {
    public final static String ENDPOINT = "articles";

    private ArrayList<Article> articles = new ArrayList<>();

    public ArticleManager() throws InterruptedException, ExecutionException, JSONException, ReflectiveOperationException {
        HashMap<String, String> params = new HashMap<>();

        params.put("date", "2018-04-02 00:00:00");     // TODO: Replace with cached date
        params.put("news_outlet_genre_ids", "1,2,12"); // TODO: Replace with current news_outlet_genre_ids

        AsyncTask<String, String, String> task = new JSONTask().execute(super.formUrl(ArticleManager.ENDPOINT, params));

        super.populateFromJSON(this, Article.class, JSONArticleInput.class, task.get());
    }

    public ArrayList<Article> getArticles() {
        ArrayList<Article> articles = new ArrayList<>(getCachedArticles());

        articles.removeAll(MainActivity.getReadLaterManager().getReadLaterArticles());

        return articles;
    }

    public ArrayList<Article> getCachedArticles() {
        return articles;
    }

    public Article get(int id) {
        for (Article article : articles) {
            if (article.getId() == id) return article;
        }

        return null;
    }

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
