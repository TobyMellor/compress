package uk.co.tobymellor.compress.views.card;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ListView;

import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.R;
import uk.co.tobymellor.compress.models.articles.Article;

public class ReadLaterCardView extends ComPressCardView {
    public ReadLaterCardView(Context context, ViewGroup container, @NonNull Article article, ArticleAdapter adapterContainer) {
        super(context, container, article, adapterContainer, R.layout.card_read_later);

        initReadLaterListener(super.getView(), this,  false);
    }

    protected void initReadLaterListener(final View view, final ComPressCardView cardView, final Boolean shouldMoveLeft) {
        final Article article = super.article;

        MultiOnClickListener multiOnClickListener = new MultiOnClickListener();

        multiOnClickListener.addOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getReadLaterManager().remove(article);
            }
        });

        super.initReadLaterListener(multiOnClickListener, view, cardView, shouldMoveLeft);
    }
}