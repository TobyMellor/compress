package uk.co.tobymellor.compress.views.card;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import uk.co.tobymellor.compress.R;
import uk.co.tobymellor.compress.models.articles.Article;

public class DiscoverCardView extends ComPressCardView {
    public DiscoverCardView(Context context, ViewGroup container, @NonNull Article article, ArticleAdapter adapterContainer) {
        super(context, container, article, adapterContainer, R.layout.card_discover);

        initReadLaterListener(context, super.getView());
    }

    private void initReadLaterListener(Context context, View view) {
        view.findViewById(R.id.image_button_toggle_read_later).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
