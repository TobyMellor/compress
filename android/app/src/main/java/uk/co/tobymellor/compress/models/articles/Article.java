package uk.co.tobymellor.compress.models.articles;

import java.util.Date;

import uk.co.tobymellor.compress.models.authors.Author;
import uk.co.tobymellor.compress.models.genres.Genre;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

public class Article {
    private final String title;
    private final String authorSummary;
    private final String threeSentenceSummary;
    private final String sevenSentenceSummary;
    private final String articleLink;
    private final Date date;
    private final String humanDate;
    private final Genre genre;
    private final Author author;

    public Article(
            String title,
            String authorSummary,
            String threeSentenceSummary,
            String sevenSentenceSummary,
            String articleLink,
            Date date,
            String humanDate,
            Genre genre,
            Author author) {
        this.title = title;
        this.authorSummary = authorSummary;
        this.threeSentenceSummary = threeSentenceSummary;
        this.sevenSentenceSummary = sevenSentenceSummary;
        this.date = date;
        this.humanDate = humanDate;
        this.articleLink = articleLink;
        this.genre = genre;
        this.author = author;
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

    public Genre getGenre() {
        return genre;
    }

    public Author getAuthor() {
        return author;
    }
}
