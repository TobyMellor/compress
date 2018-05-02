package uk.co.tobymellor.compress.models.genres;

import java.util.HashSet;

import uk.co.tobymellor.compress.models.articles.Article;

public class Genre {
    private final String slug;
    private final String name;
    private HashSet<Article> articles;

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

    public void addArticle(Article article) {
        articles.add(article);
    }
}
