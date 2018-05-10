package uk.co.tobymellor.compress.views.card;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    private void populate(Article article) {
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
}
