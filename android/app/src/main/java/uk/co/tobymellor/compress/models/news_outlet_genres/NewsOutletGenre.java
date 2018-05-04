package uk.co.tobymellor.compress.models.news_outlet_genres;

import uk.co.tobymellor.compress.MainActivity;
import uk.co.tobymellor.compress.models.Model;
import uk.co.tobymellor.compress.models.genres.Genre;
import uk.co.tobymellor.compress.models.news_outlets.NewsOutlet;

public class NewsOutletGenre extends Model {
    private final int id;
    private final Genre genre;
    private final NewsOutlet newsOutlet;

    public NewsOutletGenre(NewsOutletGenreInput input) {
        this.id         = input.getId();
        this.genre      = MainActivity.getGenreManager().get(input.getGenreId());
        this.newsOutlet = MainActivity.getNewsOutletManager().get(input.getNewsOutletId()); // input.getNewsOutletId();
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
