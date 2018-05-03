package uk.co.tobymellor.compress.models.news_outlet_genres;

import uk.co.tobymellor.compress.models.Model;
import uk.co.tobymellor.compress.models.genres.Genre;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

public class NewsOutletGenre extends Model {
    private final int id;
    private final Genre genre;
    private final NewsOutlet newsOutlet;

    public NewsOutletGenre(int id, Genre genre, NewsOutlet newsOutlet) {
        this.id = id;
        this.genre = genre;
        this.newsOutlet = newsOutlet;
    }

    public int getId() {
        return id;
    }

    public Genre getGenre() {
        return genre;
    }

    public NewsOutlet getNewsOutlet() {
        return newsOutlet;
    }
}
