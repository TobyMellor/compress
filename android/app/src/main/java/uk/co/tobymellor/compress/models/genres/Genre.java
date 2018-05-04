package uk.co.tobymellor.compress.models.genres;

import uk.co.tobymellor.compress.models.Model;

public class Genre extends Model {
    private final int id;
    private final String slug;
    private final String name;

    public Genre(GenreInput input) {
        this.id   = input.getId();
        this.slug = input.getSlug();
        this.name = input.getName();
    }

    public int getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }
}
