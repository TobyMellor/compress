package uk.co.tobymellor.compress.models.news_outlet_genres;

public interface NewsOutletGenreInput {
    String getId();

    String getNewsOutletId();

    String getNewsOutletName();

    String getNewsOutletSlug();

    String getGenreId();

    String getGenreName();

    String getGenreSlug();
}
