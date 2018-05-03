package uk.co.tobymellor.compress.models.genres;

public class Genre {
    private final String slug;
    private final String name;

    public Genre(String slug, String name) {
        this.slug = slug;
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }
}
