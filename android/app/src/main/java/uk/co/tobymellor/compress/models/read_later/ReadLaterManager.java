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

        System.out.println(getReadLaterIdString());

        String uncachedArticleIds = getStringFromList(getUncachedArticleIds());

        System.out.println(uncachedArticleIds);

        if (uncachedArticleIds.length() > 0) {
            HashMap<String, String> params = new HashMap<>();

            params.put("article_ids", uncachedArticleIds);

            AsyncTask<String, String, String> task = new JSONTask().execute(super.formUrl(ReadLaterManager.ENDPOINT, params));

            super.populateFromJSON(MainActivity.getArticleManager(), Article.class, JSONArticleInput.class, task.get());
        }

        System.out.println(getReadLaterIdString());
    }

    private String getReadLaterIdString() {
        return sharedPreferences.getString(ReadLaterManager.PREFERENCE_ARTICLE_IDS, "");
    }

    private HashSet<Integer> getUncachedArticleIds() {
        HashSet<Integer> uncachedIds           = getListFromString(getReadLaterIdString());
        HashSet<Article> cachedArticles        = MainActivity.getArticleManager().getArticles();
        HashSet<Integer> cachedArticleIds      = new HashSet<>();

        for (Article cachedArticle : cachedArticles) cachedArticleIds.add(cachedArticle.getId());

        uncachedIds.removeAll(cachedArticleIds);

        return uncachedIds;
    }

    public HashSet<Article> getReadLaterArticles() {
        HashSet<Article> readLaterArticles = new HashSet<>();
        HashSet<Article> articles          = MainActivity.getArticleManager().getArticles();

        for (int readLaterArticleId : getListFromString(getReadLaterIdString())) {
            for (Article article : articles) {
                if (article.getId() == readLaterArticleId) {
                    readLaterArticles.add(article); break;
                }
            }
        }

        return readLaterArticles;
    }

    private HashSet<Integer> getListFromString(String idString) {
        List<String> idStringList = Arrays.asList(idString.split("\\s*,\\s*"));
        HashSet<Integer> idList      = new HashSet<>();

        for (String id : idStringList) idList.add(Integer.valueOf(id));

        return idList;
    }

    private String getStringFromList(HashSet<Integer> idList) {
        return TextUtils.join(",", idList);
    }

    @Override
    public void add(Object object) {
        if (object instanceof Article) {
            Article article = (Article) object;
            int articleId = article.getId();

            if (!getListFromString(getReadLaterIdString()).contains(articleId)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                HashSet<Integer> readLaterIdList = getListFromString(getReadLaterIdString());
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
