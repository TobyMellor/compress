package uk.co.tobymellor.compress.models.genres;

import uk.co.tobymellor.compress.models.Model;

public class Genre extends Model {
    private final String slug;
    private final String name;

    public Genre(GenreInput input) {
        this.slug = input.getSlug();
        this.name = input.getName();
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }
}
