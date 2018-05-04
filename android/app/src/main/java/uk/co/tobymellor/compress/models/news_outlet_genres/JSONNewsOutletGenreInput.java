package uk.co.tobymellor.compress.models.news_outlet_genres;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONNewsOutletGenreInput implements NewsOutletGenreInput {
    private int id;
    private int newsOutletId;
    private int genreId;

    public JSONNewsOutletGenreInput(JSONObject json) throws JSONException {
        this.id           = json.getInt("id");
        this.newsOutletId = json.getInt("news_outlet_id");
        this.genreId      = json.getInt("genre_id");
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getNewsOutletId() {
        return newsOutletId;
    }

    @Override
    public int getGenreId() {
        return genreId;
    }
}
