package uk.co.tobymellor.compress.models.news_outlets;

import uk.co.tobymellor.compress.models.authors.Author;

public class NewsOutlet extends Author {
    private final String name;
    private final String slug;
    private final String imageLink;

    public NewsOutlet(NewsOutletInput input) {
        super(input.getName(), input.getImageLink(), null);

        this.name      = input.getName();
        this.slug      = input.getSlug();
        this.imageLink = input.getImageLink();
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    @Override
    public String getImageLink() {
        return imageLink;
    }

    @Override
    public String toString() {
        return name;
    }
}
