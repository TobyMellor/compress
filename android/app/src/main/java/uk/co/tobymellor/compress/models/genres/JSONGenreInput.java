package uk.co.tobymellor.compress.models.genres;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONGenreInput implements GenreInput {
    private int id;
    private String name;
    private String slug;

    public JSONGenreInput(JSONObject json) throws JSONException {
        this.id   = json.getInt("id");
        this.name = json.getString("name");
        this.slug = json.getString("slug");
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSlug() {
        return slug;
    }
}
