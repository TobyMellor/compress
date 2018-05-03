package uk.co.tobymellor.compress.models.articles;

import org.json.JSONException;
import org.json.JSONObject;

class JSONArticleInput implements ArticleInput {
    private final String title;
    private final String authorSummary;
    private final String threeSentenceSummary;
    private final String sevenSentenceSummary;
    private final String articleLink;
    private final String date;
    private final String humanDate;
    private final String genre;
    private final String author;

    public JSONArticleInput(JSONObject json) throws JSONException {
        title                = json.getString("title");
        authorSummary        = json.getString("author_summary");
        threeSentenceSummary = json.getString("three_sentence_summary");
        sevenSentenceSummary = json.getString("seven_sentence_summary");
        articleLink          = json.getString("article_link");
        date                 = json.getString("date");
        humanDate            = json.getString("human_date");
        genre                = json.getString("genre");
        author               = json.getString("author");
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
    public String getThreeSentenceSummary() {
        return threeSentenceSummary;
    }

    @Override
    public String getSevenSentenceSummary() {
        return sevenSentenceSummary;
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
    public String getGenre() {
        return genre;
    }

    @Override
    public String getAuthor() {
        return author;
    }
}
