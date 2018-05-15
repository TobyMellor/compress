package uk.co.tobymellor.compress.models.articles;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.ArrayList;

import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.models.Manager;

public class ArticleManager extends Manager {
    public final static String ENDPOINT = "articles";

    private ArrayList<Article> articles = new ArrayList<>();

    public ArticleManager(MainActivity mainActivity) {
        pullNewArticles(mainActivity);
    }

    public void pullNewArticles(final MainActivity mainActivity) {
        LoaderManager.LoaderCallbacks<ArrayList<Article>> loaderCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<Article>>() {
            @NonNull
            @Override
            public Loader<ArrayList<Article>> onCreateLoader(int id, @Nullable Bundle args) {
                return new ArticleLoader(mainActivity);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<ArrayList<Article>> loader, ArrayList<Article> data) {

            }

            @Override
            public void onLoaderReset(@NonNull Loader<ArrayList<Article>> loader) {

            }
        };

        mainActivity.getSupportLoaderManager().restartLoader(0, null, loaderCallbacks);
    }


    public ArrayList<Article> getArticles(Context context) {
        ArrayList<Article> articles = new ArrayList<>(getCachedArticles());

        articles.removeAll(MainActivity.getReadLaterManager().getReadLaterArticles(context));

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

    public void add(Object object) {
        if (object instanceof Article) {
            Article article = (Article) object;

            articles.add(article);

            if (MainActivity.getDiscoverFragment() != null && MainActivity.getDiscoverFragment().getArticleAdapter() != null) {
                MainActivity.getDiscoverFragment().getArticleAdapter().add(article);
            }

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
