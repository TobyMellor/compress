package uk.co.tobymellor.compress.views.card;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import uk.co.tobymellor.compress.DownloadImageTask;
import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.R;
import uk.co.tobymellor.compress.models.articles.Article;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

public class ArticleAdapter extends ArrayAdapter<Article> {
    private final HashMap<Integer, View> cardViews = new HashMap<>();

    private HashMap<Integer, Integer> idMap = new HashMap<>();
    public ArrayList<Article> articles;

    private final ConstraintLayout fullArticle;
    private final FloatingActionButton floatingCloseButton;
    private final Class<? extends ComPressCardView> compressCardViewClass;

    private Article currentArticle;

    public ArticleAdapter(Context context, ArrayList<Article> articles, Class<? extends ComPressCardView> compressCardViewClass) {
        super(context, 0, articles);

        fullArticle = ((Activity) context).findViewById(R.id.constraint_full_article);
        floatingCloseButton = fullArticle.findViewById(R.id.floating_close_button);

        initHideArticleListener(context);
        initFullArticleListeners(context);

        this.articles = articles;
        this.compressCardViewClass = compressCardViewClass;

        for (int i = 0; i < articles.size(); i++) {
            idMap.put(articles.get(i).getId(), i);
        }
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup container) {
        if (convertView == null || getItemId(position) == -1) {
            Article article = articles.get(position);

            try {
                convertView = compressCardViewClass.getConstructor(Context.class, ViewGroup.class, Article.class, ArticleAdapter.class).newInstance(getContext(), container, article, this).getView();

                cardViews.put(article.getId(), convertView);

                initShowArticleListener(getContext(), (CardView) convertView.findViewById(R.id.card_view), article);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        Article article = getItem(position);

        if (article == null) return -1;

        return idMap.get(article.getId());
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void remove(Article articleToRemove) {
        for (Iterator<Article> it = articles.iterator(); it.hasNext(); ) {
            Article article = it.next();

            if (article.equals(articleToRemove)) it.remove();
        }

        this.notifyDataSetChanged();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initShowArticleListener(final Context context, final CardView cardView, final Article article) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateFullArticle(fullArticle, article);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cardView.setOnTouchListener(new View.OnTouchListener() {
                private int MAX_CLICK_MOVEMENT = 150;
                private int startX;
                private int startY;

                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startX = (int) event.getRawX();
                            startY = (int) event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            int endX = (int) event.getRawX();
                            int endY = (int) event.getRawY();

                            if (hasClicked(endX, endY) && fullArticle.getVisibility() == View.INVISIBLE) {
                                performCircularReveal(context, endX, endY);
                            }
                    }

                    return false;
                }

                private boolean hasClicked(int endX, int endY) {
                    int deltaX = Math.abs(startX - endX);
                    int deltaY = Math.abs(startY - endY);

                    return deltaX <= MAX_CLICK_MOVEMENT && deltaY <= MAX_CLICK_MOVEMENT;
                }
            });
        } else {
            cardView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    fullArticle.setVisibility(View.VISIBLE);

                    return false;
                }
            });
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initHideArticleListener(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            floatingCloseButton.setOnTouchListener(new View.OnTouchListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP && fullArticle.getVisibility() == View.VISIBLE) {
                        performCircularHide(context, (int) event.getRawX(), (int) event.getRawY());
                    }

                    return false;
                }
            });
        } else {
            floatingCloseButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        fullArticle.setVisibility(View.INVISIBLE);
                    }

                    return false;
                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void performCircularReveal(Context context, int cx, int cy) {
        Animator anim = getCircularAnimation(fullArticle, cx, cy, 0, getCircularAnimationRadius(context, cx, cy));

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

                fullArticle.setVisibility(View.VISIBLE);
            }
        });

        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void performCircularHide(Context context, int cx, int cy) {
        Animator anim = getCircularAnimation(fullArticle, cx, cy, getCircularAnimationRadius(context, cx, cy), 0);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationStart(animation);

                fullArticle.setVisibility(View.INVISIBLE);
            }
        });

        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Animator getCircularAnimation(View v, int cx, int cy, float startRadius, float finalRadius) {
        Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, startRadius, finalRadius);

        anim.setDuration(350);
        anim.setInterpolator(new FastOutSlowInInterpolator());

        return anim;
    }

    private float getCircularAnimationRadius(Context context, int cx, int cy) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = Math.max(displayMetrics.widthPixels - cx, cx);
        int height = Math.max(displayMetrics.heightPixels - cy, cy);

        return (float) Math.hypot(width, height);
    }

    private void initFullArticleListeners(final Context context) {
        fullArticle.findViewById(R.id.text_read_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textSummary = fullArticle.findViewById(R.id.text_summary);
                TextView textReadMore = fullArticle.findViewById(R.id.text_read_more);
                TextView textReadFullArticle = fullArticle.findViewById(R.id.text_read_full_article);

                textSummary.setText(currentArticle.getLongSentenceSummary());
                textReadMore.setVisibility(View.INVISIBLE);
                textReadFullArticle.setVisibility(View.VISIBLE);
            }
        });

        initExternalArticleListener(context, fullArticle.findViewById(R.id.text_title));
        initExternalArticleListener(context, fullArticle.findViewById(R.id.text_read_full_article));
    }

    private void initExternalArticleListener(final Context context, final View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent externalArticle = new Intent(Intent.ACTION_VIEW);

                externalArticle.setData(Uri.parse(currentArticle.getArticleLink()));

                context.startActivity(externalArticle);
            }
        });
    }

    private void populateFullArticle(ConstraintLayout fullArticle, Article article) {
        this.currentArticle = article;

        ImageView articleImage = fullArticle.findViewById(R.id.image_article);
        ImageView authorImage = fullArticle.findViewById(R.id.image_author);

        TextView textTitle = fullArticle.findViewById(R.id.text_title);
        TextView textAuthorDetails = fullArticle.findViewById(R.id.text_author_details);
        TextView textSummary = fullArticle.findViewById(R.id.text_summary);
        TextView textReadMore = fullArticle.findViewById(R.id.text_read_more);
        TextView textReadFullArticle = fullArticle.findViewById(R.id.text_read_full_article);

        // Reuse existing images instead of reloading from the server
        View cardView = cardViews.get(article.getId());

        // Reuse the articles image
        ImageView existingArticleImageView  = cardView.findViewById(R.id.image_article);
        BitmapDrawable existingArticleImage = (BitmapDrawable) existingArticleImageView.getDrawable();
        
        articleImage.setImageBitmap(existingArticleImage.getBitmap());

        // Reuse the authors image
        ImageView existingAuthorImageView  = cardView.findViewById(R.id.image_author);
        BitmapDrawable existingAuthorImage = (BitmapDrawable) existingAuthorImageView.getDrawable();

        authorImage.setImageBitmap(existingAuthorImage.getBitmap());

        textTitle.setText(article.getTitle());
        textAuthorDetails.setText(article.getAuthorSignature());
        textSummary.setText(article.getShortSentenceSummary() != null ? article.getShortSentenceSummary() : article.getAuthorSummary());


        textReadMore.setVisibility(View.VISIBLE);
        textReadFullArticle.setVisibility(View.INVISIBLE);
    }
}