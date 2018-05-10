package uk.co.tobymellor.compress.models.read_later;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;

import org.json.JSONException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import uk.co.tobymellor.compress.JSONTask;
import uk.co.tobymellor.compress.MainActivity;
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

        String uncachedArticleIds = getStringFromList(getUncachedArticleIds());

        if (uncachedArticleIds.length() > 0) {
            HashMap<String, String> params = new HashMap<>();

            params.put("article_ids", uncachedArticleIds);

            AsyncTask<String, String, String> task = new JSONTask().execute(super.formUrl(ReadLaterManager.ENDPOINT, params));

            super.populateFromJSON(this, Article.class, JSONArticleInput.class, task.get());
        }
    }

    private String getReadLaterIdString() {
        return sharedPreferences.getString(ReadLaterManager.PREFERENCE_ARTICLE_IDS, "");
    }

    private ArrayList<Integer> getUncachedArticleIds() {
        ArrayList<Integer> uncachedIds           = getListFromString(getReadLaterIdString());
        ArrayList<Article> cachedArticles        = MainActivity.getArticleManager().getCachedArticles();
        ArrayList<Integer> cachedArticleIds      = new ArrayList<>();

        for (Article cachedArticle : cachedArticles) cachedArticleIds.add(cachedArticle.getId());

        uncachedIds.removeAll(cachedArticleIds);

        return uncachedIds;
    }

    public ArrayList<Article> getReadLaterArticles() {
        ArrayList<Article> readLaterArticles = new ArrayList<>();
        ArrayList<Article> articles          = MainActivity.getArticleManager().getCachedArticles();

        for (int readLaterArticleId : getListFromString(getReadLaterIdString())) {
            for (Article article : articles) {
                if (article.getId() == readLaterArticleId) {
                    readLaterArticles.add(article); break;
                }
            }
        }

        return readLaterArticles;
    }

    private ArrayList<Integer> getListFromString(String idString) {
        List<String> idStringList = Arrays.asList(idString.split("\\s*,\\s*"));
        ArrayList<Integer> idList      = new ArrayList<>();

        for (String id : idStringList) idList.add(Integer.valueOf(id));

        return idList;
    }

    private String getStringFromList(ArrayList<Integer> idList) {
        return TextUtils.join(",", idList);
    }

    @Override
    public void add(Object object) {
        if (object instanceof Article) {
            Article article = (Article) object;
            int articleId = article.getId();

            if (MainActivity.getArticleManager().get(articleId) == null) {
                MainActivity.getArticleManager().add(article);
            }

            if (!getListFromString(getReadLaterIdString()).contains(articleId)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                ArrayList<Integer> readLaterIdList = getListFromString(getReadLaterIdString());
                readLaterIdList.add(articleId);

                editor.putString(PREFERENCE_ARTICLE_IDS, getStringFromList(readLaterIdList));
                editor.apply();
            }
        }
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
