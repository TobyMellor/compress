package uk.co.tobymellor.compress.models.genres;

import uk.co.tobymellor.compress.models.Model;

public class Genre extends Model {
    private final String slug;
    private final String name;
    private final String imageLink;

    public Genre(GenreInput input) {
        this.slug      = input.getSlug();
        this.name      = input.getName();
        this.imageLink = input.getImageLink();
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getImageLink() {
        return imageLink;
    }
}
