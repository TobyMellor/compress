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

        initReadLaterListener(context, super.getView(), this,  false);
    }
}
