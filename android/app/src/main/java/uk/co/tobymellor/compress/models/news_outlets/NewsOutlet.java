package uk.co.tobymellor.compress.models.news_outlets;

public class NewsOutlet {
    private final String name;
    private final String slug;

    public NewsOutlet(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }
}
