package uk.co.tobymellor.compress.models.news_outlet_genres;

public interface NewsOutletGenreInput {
    String getTitle();

    String getAuthorSummary();

    String getThreeSentenceSummary();

    String getSevenSentenceSummary();

    String getArticleLink();

    String getDate();

    String getHumanDate();

    String getNewsOutletGenreId();

    String getAuthorId();
}
