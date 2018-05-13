package uk.co.tobymellor.compress.views.card;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.R;
import uk.co.tobymellor.compress.models.articles.Article;

public class DiscoverCardView extends ComPressCardView {
    public DiscoverCardView(Context context, ViewGroup container, @NonNull Article article, ArticleAdapter adapterContainer) {
        super(context, container, article, adapterContainer, R.layout.card_discover);

        initReadLaterListener(super.getView(), this,  true);
    }

    protected void initReadLaterListener(final View view, final ComPressCardView cardView, final Boolean shouldMoveLeft) {
        final Article article = super.article;

        MultiOnClickListener multiOnClickListener = new MultiOnClickListener();

        multiOnClickListener.addOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getReadLaterManager().add(article);
            }
        });

        super.initReadLaterListener(multiOnClickListener, view, cardView, shouldMoveLeft);
    }
}
