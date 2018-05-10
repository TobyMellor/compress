package uk.co.tobymellor.compress.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.R;
import uk.co.tobymellor.compress.models.articles.Article;
import uk.co.tobymellor.compress.models.authors.Author;
import uk.co.tobymellor.compress.models.genres.Genre;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;
import uk.co.tobymellor.compress.views.card.ArticleAdapter;
import uk.co.tobymellor.compress.views.card.DiscoverCardView;
import uk.co.tobymellor.compress.views.card.ReadLaterCardView;

public class ReadLaterFragment {
    private View fragment;

    public ReadLaterFragment(LayoutInflater inflater, ViewGroup container) {
        fragment = inflater.inflate(R.layout.fragment_articles, container, false);
    }

    public View getView() {
        ListView list = fragment.findViewById(R.id.list_cards);

        list.setAdapter(
                new ArticleAdapter(fragment.getContext(), MainActivity.getReadLaterManager().getReadLaterArticles(), ReadLaterCardView.class)
        );

        return fragment;
    }
}
