package uk.co.tobymellor.compress.models.authors;

import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

public class Author {
    private final String name;
    private final String imageLink;
    private final NewsOutlet newsOutlet;

    public Author(String name, String imageLink, NewsOutlet newsOutlet) {
        this.name       = name;
        this.imageLink  = imageLink != null ? imageLink : newsOutlet.getImageLink();
        this.newsOutlet = newsOutlet;
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