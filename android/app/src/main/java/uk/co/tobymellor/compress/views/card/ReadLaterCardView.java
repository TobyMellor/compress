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

        initReadLaterListener(context, super.getView());
    }

    private void initReadLaterListener(final Context context, final View view) {
        final ReadLaterCardView cardView = this;

        view.findViewById(R.id.image_button_toggle_read_later).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final View card = cardView.articleCardView;

                DisplayMetrics displayMetrics = new DisplayMetrics();

                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                ObjectAnimator leftAnimation = ObjectAnimator.ofFloat(card, "x", 0, displayMetrics.widthPixels);

                leftAnimation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        ValueAnimator upAnimation = ValueAnimator.ofInt(card.getMeasuredHeight(), -1);

                        upAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                int val = (Integer) valueAnimator.getAnimatedValue();

                                ViewGroup.LayoutParams layoutParams = card.getLayoutParams();
                                layoutParams.height = val;

                                card.setLayoutParams(layoutParams);
                            }
                        });

                        upAnimation.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);

                                cardView.adapterContainer.remove(cardView.article);
                            }
                        });

                        upAnimation.setDuration(250);
                        upAnimation.start();
                    }
                });

                leftAnimation.setDuration(250);
                leftAnimation.start();
            }
        });
    }
}
