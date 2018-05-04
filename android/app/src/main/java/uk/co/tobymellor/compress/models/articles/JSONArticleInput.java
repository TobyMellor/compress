package uk.co.tobymellor.compress.models.articles;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONArticleInput implements ArticleInput {
    private final int id;
    private final String title;
    private final String authorSummary;
    private final String threeSentenceSummary;
    private final String sevenSentenceSummary;
    private final String articleLink;
    private final String date;
    private final String humanDate;
    private final int newsOutletGenreId;
    private final String authorName;
    private final int authorId;

    public JSONArticleInput(JSONObject json) throws JSONException {
        id                   = json.getInt("id");
        title                = json.getString("title");
        authorSummary        = json.getString("author_summary");
        threeSentenceSummary = json.getString("three_sentence_summary");
        sevenSentenceSummary = json.getString("seven_sentence_summary");
        articleLink          = json.getString("article_link");
        date                 = json.getString("date");
        humanDate            = json.getString("human_date");
        newsOutletGenreId    = json.getInt("news_outlet_genre_id");

        if (!json.getString("author_id").equals("null")) {
            JSONObject author = json.getJSONObject("author");

            authorId   = author.getInt("id");
            authorName = author.getString("name");
        } else {
            authorId   = -1;
            authorName = null;
        }
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
    public int getNewsOutletGenreId() {
        return newsOutletGenreId;
    }

    @Override
    public String getAuthorName() {
        return authorName;
    }

    @Override
    public int getAuthorId() {
        return authorId;
    }
}
