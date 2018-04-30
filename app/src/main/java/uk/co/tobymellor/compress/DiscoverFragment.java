package uk.co.tobymellor.compress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class DiscoverFragment {
    View fragment;

    public DiscoverFragment(LayoutInflater inflater, ViewGroup container) {
        fragment = inflater.inflate(R.layout.fragment_discover, container, false);
    }

    public View getView() {
        final Article[] articles = {
                new Article("Title test", "Author summary :)", "Company nice", "Author name"),
                new Article("Title test", "Author summary :)", "Company nice", "Author name"),
                new Article("Title test", "Author summary :)", "Company nice", "Author name"),
                new Article("Title test", "Author summary :)", "Company nice", "Author name")
        };

        ListView list = fragment.findViewById(R.id.list_cards);

        list.setAdapter(
                new ArticleAdapter(fragment.getContext(), articles, list)
        );

        return fragment;
    }
}
