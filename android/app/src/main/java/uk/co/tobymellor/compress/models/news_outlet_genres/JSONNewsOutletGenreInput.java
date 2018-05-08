package uk.co.tobymellor.compress.models.news_outlet_genres;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONNewsOutletGenreInput implements NewsOutletGenreInput {
    private int id;
    private String newsOutletSlug;
    private String genreSlug;

    public JSONNewsOutletGenreInput(JSONObject json) throws JSONException {
        this.id             = json.getInt("id");
        this.newsOutletSlug = json.getString("news_outlet_slug");
        this.genreSlug      = json.getString("genre_slug");
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNewsOutletSlug() {
        return newsOutletSlug;
    }

    @Override
    public String getGenreSlug() {
        return genreSlug;
    }
}
