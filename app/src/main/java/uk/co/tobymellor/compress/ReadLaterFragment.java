package uk.co.tobymellor.compress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ReadLaterFragment {
    View fragment;

    public ReadLaterFragment(LayoutInflater inflater, ViewGroup container) {
        fragment = inflater.inflate(R.layout.fragment_read_later, container, false);
    }

    public View getView() {
        final Article[] articles = {
                new Article("Read later title", "Summary of the read later article here", "BBC News", "John Smith")
        };

        ListView list = fragment.findViewById(R.id.list_cards);

        list.setAdapter(
                new ArticleAdapter(fragment.getContext(), articles, list)
        );

        return fragment;
    }
}
