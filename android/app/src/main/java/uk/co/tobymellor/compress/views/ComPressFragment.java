package uk.co.tobymellor.compress.views;

import android.view.View;

import uk.co.tobymellor.compress.views.card.ArticleAdapter;

abstract class ComPressFragment {
    View fragment;
    ArticleAdapter adapter;

    abstract public View getView();

    public View getFragment() {
        return fragment;
    }

    public ArticleAdapter getArticleAdapter() {
        return adapter;
    }
}
