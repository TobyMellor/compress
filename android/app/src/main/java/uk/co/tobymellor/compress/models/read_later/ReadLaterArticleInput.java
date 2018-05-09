package uk.co.tobymellor.compress.models.read_later;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uk.co.tobymellor.compress.models.Model;
import uk.co.tobymellor.compress.models.articles.Article;
import uk.co.tobymellor.compress.models.articles.ArticleInput;

public class ReadLaterArticleInput implements ArticleInput {
    private final int id;
    private final String title;
    private final String authorSummary;
    private final String shortSentenceSummary;
    private final String longSentenceSummary;
    private final String articleImageLink;
    private final String articleLink;
    private final String date;
    private final String humanDate;
    private final int newsOutletGenreId;
    private final int authorId;
    private final String authorName;
    private final String authorImageLink;
    private final Date addedAt;

    public ReadLaterArticleInput(Article article, Date addedAt) {
        this.id                   = article.getId();
        this.title                = article.getTitle();
        this.authorSummary        = article.getAuthorSummary();
        this.shortSentenceSummary = article.getShortSentenceSummary();
        this.longSentenceSummary  = article.getLongSentenceSummary();
        this.articleImageLink     = article.getArticleImageLink();
        this.articleLink          = article.getArticleLink();
        this.date                 = new SimpleDateFormat(Model.MYSQL_DATE_FORMAT, Locale.UK).format(article.getDate());
        this.humanDate            = article.getHumanDate();
        this.newsOutletGenreId    = article.getNewsOutletGenre().getId();
        this.authorId             = article.getAuthor().getId();
        this.authorName           = article.getAuthor().getName();
        this.authorImageLink      = article.getAuthor().getImageLink();
        this.addedAt              = addedAt;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthorSummary() {
        return authorSummary;
    }

    @Override
    public String getShortSentenceSummary() {
        return shortSentenceSummary;
    }

    @Override
    public String getLongSentenceSummary() {
        return longSentenceSummary;
    }

    @Override
    public String getArticleImageLink() {
        return articleImageLink;
    }

    @Override
    public String getArticleLink() {
        return articleLink;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getHumanDate() {
        return humanDate;
    }

    @Override
    public int getNewsOutletGenreId() {
        return newsOutletGenreId;
    }

    @Override
    public int getAuthorId() {
        return authorId;
    }

    @Override
    public String getAuthorName() {
        return authorName;
    }

    @Override
    public String getAuthorImageLink() {
        return authorImageLink;
    }
}
