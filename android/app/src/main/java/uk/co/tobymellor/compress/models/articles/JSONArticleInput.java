package uk.co.tobymellor.compress.models.articles;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.tobymellor.compress.models.JSONInput;

class JSONArticleInput extends JSONInput implements ArticleInput {
    private final String title;
    private final String authorSummary;
    private final String threeSentenceSummary;
    private final String sevenSentenceSummary;
    private final String articleLink;
    private final String date;
    private final String humanDate;
    private final String newsOutletGenreId;
    private final String authorName;

    JSONArticleInput(JSONObject json) throws JSONException {
        title                = json.getString("title");
        authorSummary        = json.getString("author_summary");
        threeSentenceSummary = json.getString("three_sentence_summary");
        sevenSentenceSummary = json.getString("seven_sentence_summary");
        articleLink          = json.getString("article_link");
        date                 = json.getString("date");
        humanDate            = json.getString("human_date");
        newsOutletGenreId    = json.getString("news_outlet_genre_id");

        if (json.getString("author_id").equals("null")) {
            authorName = json.getJSONObject("author").getString("name");
        } else {
            authorName = null;
        }
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
    public String getNewsOutletGenreId() {
        return newsOutletGenreId;
    }

    @Override
    public String getAuthorName() {
        return authorName;
    }
}
