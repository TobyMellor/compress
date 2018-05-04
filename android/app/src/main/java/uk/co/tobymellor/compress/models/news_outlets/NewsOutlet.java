package uk.co.tobymellor.compress.models.news_outlets;

import uk.co.tobymellor.compress.models.authors.Author;

public class NewsOutlet extends Author {
    private final String name;
    private final String slug;

    public NewsOutlet(String name, String slug) {
        super(name);

        this.name = name;
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    @Override
    public String toString() {
        return name;
    }
}
