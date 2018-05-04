package uk.co.tobymellor.compress.models.news_outlet_genres;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.tobymellor.compress.models.JSONInput;

class JSONNewsOutletGenreInput extends JSONInput implements NewsOutletGenreInput {
    private String id;
    private String newsOutletId;
    private String genreId;

    public JSONNewsOutletGenreInput(JSONObject json) throws JSONException {
        title                = json.getString("title");
        authorSummary        = json.getString("author_summary");
        threeSentenceSummary = json.getString("three_sentence_summary");
        sevenSentenceSummary = json.getString("seven_sentence_summary");
        articleLink          = json.getString("article_link");
        date                 = json.getString("date");
        humanDate            = json.getString("human_date");
        newsOutletGenreId    = json.getString("news_outlet_genre_id");
        authorId             = json.getString("author_id");
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getNewsOutletId() {
        return null;
    }

    @Override
    public String getGenreId() {
        return null;
    }
}
