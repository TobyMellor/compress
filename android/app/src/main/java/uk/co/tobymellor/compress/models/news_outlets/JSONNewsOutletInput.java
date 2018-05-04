package uk.co.tobymellor.compress.models.news_outlets;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONNewsOutletInput implements NewsOutletInput {
    private int id;
    private String name;
    private String slug;

    public JSONNewsOutletInput(JSONObject json) throws JSONException {
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
