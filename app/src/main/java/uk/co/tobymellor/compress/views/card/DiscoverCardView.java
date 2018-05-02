package uk.co.tobymellor.compress.views.card;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import uk.co.tobymellor.compress.R;
import uk.co.tobymellor.compress.models.articles.Article;

public class DiscoverCardView extends ComPressCardView {
    public DiscoverCardView(Context context, ViewGroup container, @NonNull Article article) {
        super(context, container, article, R.layout.card_discover);
    }
}
