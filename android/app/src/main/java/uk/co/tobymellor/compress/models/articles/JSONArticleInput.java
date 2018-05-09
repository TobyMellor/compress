package uk.co.tobymellor.compress.models.articles;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONArticleInput implements ArticleInput {
    protected int id;
    protected String title;
    protected String authorSummary;
    protected String shortSentenceSummary;
    protected String longSentenceSummary;
    protected String articleImageLink;
    protected String articleLink;
    protected String date;
    protected String humanDate;
    protected int newsOutletGenreId;
    protected int authorId;
    protected String authorName;
    protected String authorImageLink;

    public JSONArticleInput() {}

    public JSONArticleInput(JSONObject json) throws JSONException {
        id                   = json.getInt("id");
        title                = json.getString("title");
        authorSummary        = json.getString("author_summary");
        shortSentenceSummary = getNullableString(json, "short_sentence_summary");
        longSentenceSummary  = getNullableString(json, "long_sentence_summary");
        articleImageLink     = json.getString("article_image_link");
        articleLink          = json.getString("article_link");
        date                 = json.getString("date");
        humanDate            = json.getString("human_date");
        newsOutletGenreId    = json.getInt("news_outlet_genre_id");

        if (!json.getString("author_id").equals("null")) {
            JSONObject author = json.getJSONObject("author");

            authorId   = author.getInt("id");
            authorName = author.getString("name");
            authorImageLink = getNullableString(author, "image_link");
        } else {
            authorId = -1;
            authorName = null;
            authorImageLink = null;
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

    private String getNullableString(JSONObject json, String property) throws JSONException {
        return json.has(property) && !json.isNull(property) ? json.getString(property) : null;
    }
}
