package uk.co.tobymellor.compress.models.authors;

import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

public class Author {
    private final String name;
    private final NewsOutlet newsOutlet;

    public Author(String name, NewsOutlet newsOutlet) {
        this.name = name;
        this.newsOutlet = newsOutlet;
    }

    public String getName() {
        return name;
    }

    public NewsOutlet getNewsOutlet() {
        return newsOutlet;
    }
}