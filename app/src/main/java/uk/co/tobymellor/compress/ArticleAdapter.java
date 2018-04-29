package uk.co.tobymellor.compress;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ArticleAdapter extends ArrayAdapter<Article> {
    private Article[] articles;
    private ViewGroup container;

    public ArticleAdapter(@NonNull Context context, Article[] articles, ViewGroup container) {
        super(context, 0, articles);

        this.articles = articles;
        this.container = container;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup container) {
        if (convertView == null) {
            convertView = new DiscoverCardView(getContext(), container, articles[position])
                    .getView();
        }

        return convertView;
    }
}
