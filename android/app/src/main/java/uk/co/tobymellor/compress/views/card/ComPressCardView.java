package uk.co.tobymellor.compress.views.card;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.tobymellor.compress.R;
import uk.co.tobymellor.compress.models.articles.Article;
import uk.co.tobymellor.compress.models.authors.Author;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

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

        authorCompany.setText(article.getNewsOutletGenre().getNewsOutlet().toString());

        Author author = article.getAuthor();

        if (!(author instanceof NewsOutlet)) {
            authorName.setText(article.getAuthor().getName());
        } else {
            authorName.setText("");
        }
    }

    public View getView() {
        return articleCardView;
    }
}
