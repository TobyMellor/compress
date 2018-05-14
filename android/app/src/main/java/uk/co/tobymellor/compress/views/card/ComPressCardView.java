package uk.co.tobymellor.compress.views.card;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import uk.co.tobymellor.compress.DownloadImageTask;
import uk.co.tobymellor.compress.R;
import uk.co.tobymellor.compress.models.articles.Article;
import uk.co.tobymellor.compress.models.authors.Author;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

public abstract class ComPressCardView {
    protected final View articleCardView;
    protected final Article article;
    protected final ArticleAdapter adapterContainer;

    private ImageView articleImage;
    private ImageView authorImage;
    private TextView title;
    private TextView authorSummary;
    private TextView authorCompany;
    private TextView authorName;

    ComPressCardView(final Context context, ViewGroup container, @NonNull Article article, ArticleAdapter adapterContainer, int layout) {
        articleCardView       = LayoutInflater.from(context).inflate(layout, container, false);

        this.article          = article;
        this.adapterContainer = adapterContainer;

        articleImage  = articleCardView.findViewById(R.id.image_article);
        authorImage   = articleCardView.findViewById(R.id.image_author);
        title         = articleCardView.findViewById(R.id.text_title);
        authorSummary = articleCardView.findViewById(R.id.text_author_summary);
        authorCompany = articleCardView.findViewById(R.id.text_author_company);
        authorName    = articleCardView.findViewById(R.id.text_author_name);

        populate(article);
    }

    protected void initReadLaterListener(MultiOnClickListener multiOnClickListener, final View view, final ComPressCardView cardView, final Boolean shouldMoveLeft) {
        multiOnClickListener.addOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View card = cardView.articleCardView;

                DisplayMetrics displayMetrics = new DisplayMetrics();

                ((Activity) v.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                ObjectAnimator leftAnimation = ObjectAnimator.ofFloat(card, "x", 0, shouldMoveLeft ? -displayMetrics.widthPixels : displayMetrics.widthPixels);

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

                                cardView.adapterContainer.remove(cardView);
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

        view.findViewById(R.id.image_button_toggle_read_later).setOnClickListener(multiOnClickListener);
    }

    private void populate(Article article) {
        System.out.println(article);
        new DownloadImageTask(articleImage).execute(article.getArticleImageLink());
        new DownloadImageTask(authorImage).execute(article.getAuthor().getImageLink());

        title.setText(article.getTitle());
        authorSummary.setText(article.getAuthorSummary());

        authorCompany.setText(article.getNewsOutletGenre().getNewsOutlet().getName());
        authorName.setText(article.getAuthorSignature());
    }

    public View getView() {
        return articleCardView;
    }

    protected final class MultiOnClickListener implements View.OnClickListener {
        List<View.OnClickListener> listeners;

        public MultiOnClickListener() {
            listeners = new ArrayList<View.OnClickListener>();
        }

        public void addOnClickListener(View.OnClickListener listener) {
            listeners.add(listener);
        }

        @Override
        public void onClick(View v) {
            for (View.OnClickListener listener : listeners) {
                listener.onClick(v);
            }
        }
    }
}