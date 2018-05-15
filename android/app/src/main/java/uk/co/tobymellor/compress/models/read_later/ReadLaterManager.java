package uk.co.tobymellor.compress.models.read_later;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.TextUtils;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import uk.co.tobymellor.compress.JSONTask;
import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.models.Manager;
import uk.co.tobymellor.compress.models.articles.Article;
import uk.co.tobymellor.compress.models.articles.JSONArticleInput;

public class ReadLaterManager extends Manager {
    private final static String ENDPOINT = "read_later_articles";

    public ReadLaterManager(final Context context) throws InterruptedException, ExecutionException, JSONException, ReflectiveOperationException {
       String uncachedArticleIds = getStringFromList(getUncachedArticleIds(context));

        if (uncachedArticleIds.length() > 0) {
            HashMap<String, String> params = new HashMap<>();

            params.put("article_ids", uncachedArticleIds);

            AsyncTask<String, String, String> task = new JSONTask().execute(super.formUrl(ReadLaterManager.ENDPOINT, params));

            super.populateFromJSON(this, Article.class, JSONArticleInput.class, task.get());
        }
    }

    private ArrayList<Integer> getReadLaterArticleIds(final Context context) {
        ArrayList<Integer> articleIds = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(
                ReadLaterContract.ReadLaterTable.CONTENT_URI,
                null,
                null,
                null,
                ReadLaterContract.ReadLaterTable._ID + " COLLATE LOCALIZED DESC"
        );

        if (cursor.moveToFirst()) {
            do {
                int articleIdIndex = cursor.getColumnIndex(ReadLaterContract.ReadLaterTable.COLUMN_ARTICLE_ID);

                articleIds.add(cursor.getInt(articleIdIndex));
            } while (cursor.moveToNext());
        }

        return articleIds;
    }

    private ArrayList<Integer> getUncachedArticleIds(final Context context) {
        ArrayList<Integer> uncachedIds      = getReadLaterArticleIds(context);
        ArrayList<Article> cachedArticles   = MainActivity.getArticleManager().getCachedArticles();
        ArrayList<Integer> cachedArticleIds = new ArrayList<>();

        for (Article cachedArticle : cachedArticles) cachedArticleIds.add(cachedArticle.getId());

        uncachedIds.removeAll(cachedArticleIds);

        return uncachedIds;
    }

    public ArrayList<Article> getReadLaterArticles(final Context context) {
        ArrayList<Article> readLaterArticles = new ArrayList<>();
        ArrayList<Article> articles          = MainActivity.getArticleManager().getCachedArticles();

        for (int readLaterArticleId : getReadLaterArticleIds(context)) {
            for (Article article : articles) {
                if (article.getId() == readLaterArticleId) {
                    readLaterArticles.add(article); break;
                }
            }
        }

        return readLaterArticles;
    }

    private ArrayList<Integer> getListFromString(String idString) {
        ArrayList<Integer> idList = new ArrayList<>();

        if (idString == "") return idList;

        List<String> idStringList = Arrays.asList(idString.split("\\s*,\\s*"));

        for (String id : idStringList) idList.add(Integer.valueOf(id));

        return idList;
    }

    private String getStringFromList(ArrayList<Integer> idList) {
        return TextUtils.join(",", idList);
    }

    @Override
    public void add(Object object) {
        //
    }

    public void add(final Context context, final Article article) {
        int articleId   = article.getId();

        if (MainActivity.getReadLaterFragment() != null) {
            MainActivity.getReadLaterFragment().getArticleAdapter().add(article);
        }

        if (MainActivity.getArticleManager().get(articleId) == null) {
            MainActivity.getArticleManager().add(article);
        }

        ContentValues values = new ContentValues();

        values.put(ReadLaterContract.ReadLaterTable.COLUMN_ARTICLE_ID, articleId);

        context.getContentResolver().insert(ReadLaterContract.ReadLaterTable.CONTENT_URI, values);
    }

    @Override
    public void remove(Object object) {
        //
    }

    public void remove(final Context context, final Article article) {
        int articleId = article.getId();

        if (MainActivity.getReadLaterFragment() != null) {
            MainActivity.getDiscoverFragment().getArticleAdapter().add(article);
        }

        context.getContentResolver().delete(ReadLaterContract.ReadLaterTable.buildReadLaterUriWithId(articleId), null, null);
    }

    @Override
    public String getEndpoint() {
        return ReadLaterManager.ENDPOINT;
    }
}