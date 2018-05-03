package uk.co.tobymellor.compress.models.articles;

import java.util.Date;

import uk.co.tobymellor.compress.models.Model;
import uk.co.tobymellor.compress.models.authors.Author;
import uk.co.tobymellor.compress.models.genres.Genre;
import uk.co.tobymellor.compress.models.news_outlet_genres.NewsOutletGenre;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

public class Article extends Model {
    private final String title;
    private final String authorSummary;
    private final String threeSentenceSummary;
    private final String sevenSentenceSummary;
    private final String articleLink;
    private final Date date;
    private final String humanDate;
    private final NewsOutletGenre newsOutletGenre;
    private final Author author;

    Article(ArticleInput articleInput) {
        this.newsOutletGenre  = null;
        this.author = null;
        this.date   = getDateFromMySQLFormat(articleInput.getDate());

        this.title                = articleInput.getTitle();
        this.authorSummary        = articleInput.getAuthorSummary();
        this.threeSentenceSummary = articleInput.getThreeSentenceSummary();
        this.sevenSentenceSummary = articleInput.getSevenSentenceSummary();
        this.humanDate            = articleInput.getHumanDate();
        this.articleLink          = articleInput.getArticleLink();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorSummary() {
        return authorSummary;
    }

    public String getThreeSentenceSummary() {
        return threeSentenceSummary;
    }

    public String getSevenSentenceSummary() {
        return sevenSentenceSummary;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public Date getDate() {
        return date;
    }

    public String getHumanDate() { return humanDate; }

    public NewsOutletGenre getGenre() {
        return newsOutletGenre;
    }

    public Author getAuthor() {
        return author;
    }
}
