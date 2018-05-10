package uk.co.tobymellor.compress.models.articles;

import java.util.Date;

import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.models.Model;
import uk.co.tobymellor.compress.models.authors.Author;
import uk.co.tobymellor.compress.models.genres.Genre;
import uk.co.tobymellor.compress.models.news_outlet_genres.NewsOutletGenre;
import uk.co.tobymellor.compress.models.news_outlet_genres.NewsOutletGenreManager;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

public class Article extends Model {
    private final int id;
    private final String title;
    private final String authorSummary;
    private final String shortSentenceSummary;
    private final String longSentenceSummary;
    private final String articleImageLink;
    private final String articleLink;
    private final Date date;
    private final String humanDate;
    private final NewsOutletGenre newsOutletGenre;
    private final Author author;

    private static final String EXCLUDE_FROM_SUMMARY = "SEE\\ ALSO(.*)";

    public Article(ArticleInput articleInput) {
        this.newsOutletGenre = MainActivity.getNewsOutletGenreManager().get(articleInput.getNewsOutletGenreId());

        if (articleInput.getAuthorId() != -1) {
            this.author = new Author(articleInput.getAuthorId(), articleInput.getAuthorName(), articleInput.getAuthorImageLink(), newsOutletGenre.getNewsOutlet());
        } else {
            this.author = newsOutletGenre.getNewsOutlet();
        }

        this.date = getDateFromMySQLFormat(articleInput.getDate());

        this.id                   = articleInput.getId();
        this.title                = articleInput.getTitle();
        this.authorSummary        = articleInput.getAuthorSummary();
        this.shortSentenceSummary = replaceBreaks(articleInput.getShortSentenceSummary());
        this.longSentenceSummary  = replaceBreaks(articleInput.getLongSentenceSummary());
        this.articleImageLink     = articleInput.getArticleImageLink();
        this.articleLink          = articleInput.getArticleLink();
        this.humanDate            = articleInput.getHumanDate();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorSummary() {
        return authorSummary
                .replace(". ", "\n\n")
                .replaceAll(Article.EXCLUDE_FROM_SUMMARY, "")
                .trim();
    }

    public String getShortSentenceSummary() {
        return shortSentenceSummary;
    }

    public String getLongSentenceSummary() {
        return longSentenceSummary;
    }

    public String getArticleImageLink() {
        return articleImageLink;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public Date getDate() {
        return date;
    }

    public String getHumanDate() { return humanDate; }

    public NewsOutletGenre getNewsOutletGenre() {
        return newsOutletGenre;
    }

    public Author getAuthor() {
        return author;
    }

    public String getAuthorSignature() {
        if (author instanceof NewsOutlet) {
            return String.format("From %s", author.getName());
        }

        return String.format("%s from %s", author.getName(), newsOutletGenre.getNewsOutlet().getName());
    }

    private String replaceBreaks(String sentences) {
        if (sentences == null) return sentences;

        return sentences.replace("[BREAK] ", "\n\n").replace("[BREAK]", "");
    }
}
