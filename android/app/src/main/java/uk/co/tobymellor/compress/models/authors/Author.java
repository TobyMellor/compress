package uk.co.tobymellor.compress.models.authors;

import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

public class Author {
    private final int id;
    private final String name;
    private final String imageLink;
    private final NewsOutlet newsOutlet;

    public Author(int id, String name, String imageLink, NewsOutlet newsOutlet) {
        this.id         = id;
        this.name       = name;
        this.imageLink  = imageLink != null ? imageLink : newsOutlet.getImageLink();
        this.newsOutlet = newsOutlet;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public NewsOutlet getNewsOutlet() {
        return newsOutlet;
    }
}