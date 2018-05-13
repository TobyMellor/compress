package uk.co.tobymellor.compress.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.HashSet;

import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.R;
import uk.co.tobymellor.compress.models.articles.Article;
import uk.co.tobymellor.compress.views.card.ArticleAdapter;
import uk.co.tobymellor.compress.views.card.DiscoverCardView;

public class DiscoverFragment extends ComPressFragment {
    public DiscoverFragment(LayoutInflater inflater, ViewGroup container) {
        fragment = inflater.inflate(R.layout.fragment_articles, container, false);

        adapter = new ArticleAdapter(fragment.getContext(), MainActivity.getArticleManager().getArticles(), DiscoverCardView.class);
    }

    public View getView() {
        ListView list = fragment.findViewById(R.id.list_cards);

        list.setAdapter(adapter);

        return fragment;
    }
}
