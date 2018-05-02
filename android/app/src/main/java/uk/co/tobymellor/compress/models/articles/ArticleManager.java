package uk.co.tobymellor.compress.models.articles;

import java.util.HashSet;

import uk.co.tobymellor.compress.models.Manager;

public class ArticleManager implements Manager {
    HashSet<Article> articles;

    public ArticleManager(HashSet<Article> articles) {
        this.articles = articles;
    }

    @Override
    public HashSet<Article> get() {
        return articles;
    }

    @Override
    public void add(Object article) {
        if (article instanceof Manager) articles.add((Article) article);
    }

    @Override
    public void remove(Object article) {
        if (article instanceof Manager) articles.remove(article);
    }
}
