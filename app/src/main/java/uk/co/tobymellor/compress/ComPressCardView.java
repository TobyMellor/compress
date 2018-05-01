package uk.co.tobymellor.compress;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressWarnings("FieldCanBeLocal")

public abstract class ComPressCardView {
    private TextView title;
    private TextView authorSummary;
    private TextView authorCompany;
    private TextView authorName;

    private View articleCardView;

    ComPressCardView(final Context context, ViewGroup container, @NonNull Article article, int layout) {
        articleCardView = LayoutInflater.from(context).inflate(layout, container, false);

        title         = articleCardView.findViewById(R.id.text_title);
        authorSummary = articleCardView.findViewById(R.id.text_author_summary);
        authorCompany = articleCardView.findViewById(R.id.text_author_company);
        authorName    = articleCardView.findViewById(R.id.text_author_name);

        populate(article);
    }

    private void populate(Article article) {
        title.setText(article.getTitle());
        authorSummary.setText(article.getAuthorSummary());

        authorCompany.setText(article.getAuthorCompany());
        authorName.setText(article.getAuthorName());
    }

    public View getView() {
        return articleCardView;
    }
}
