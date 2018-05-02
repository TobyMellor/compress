package uk.co.tobymellor.compress.models.news_outlets;

import java.util.HashSet;

import uk.co.tobymellor.compress.models.authors.Author;

public class NewsOutlet {
    private final String name;
    private HashSet<Author> authors;

    public NewsOutlet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }
}
