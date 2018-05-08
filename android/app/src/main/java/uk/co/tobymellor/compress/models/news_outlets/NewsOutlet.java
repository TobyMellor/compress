package uk.co.tobymellor.compress.models.news_outlets;

import uk.co.tobymellor.compress.models.authors.Author;

public class NewsOutlet extends Author {
    private final String name;
    private final String slug;

    public NewsOutlet(NewsOutletInput input) {
        super(input.getName());

        this.name = input.getName();
        this.slug = input.getSlug();
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
