package uk.co.tobymellor.compress.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import uk.co.tobymellor.compress.JSONTask;
import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.R;
import uk.co.tobymellor.compress.models.Manager;
import uk.co.tobymellor.compress.models.articles.Article;
import uk.co.tobymellor.compress.models.articles.ArticleManager;
import uk.co.tobymellor.compress.views.card.ArticleAdapter;

public class DiscoverFragment {
    View fragment;

    public DiscoverFragment(LayoutInflater inflater, ViewGroup container) {
        fragment = inflater.inflate(R.layout.fragment_discover, container, false);
    }

    public View getView() {
        Article[] articles = (Article[]) MainActivity.getArticleManager()
                .getArticles()
                .toArray();

        ListView list = fragment.findViewById(R.id.list_cards);

        list.setAdapter(
                new ArticleAdapter(fragment.getContext(), articles, list)
        );

        return fragment;
    }
}
