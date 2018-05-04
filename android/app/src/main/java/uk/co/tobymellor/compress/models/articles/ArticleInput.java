package uk.co.tobymellor.compress.models.articles;

public interface ArticleInput {
    int getId();

    String getTitle();

    String getAuthorSummary();

    String getThreeSentenceSummary();

    String getSevenSentenceSummary();

    String getArticleLink();

    String getDate();

    String getHumanDate();

    int getNewsOutletGenreId();

    int getAuthorId();

    String getAuthorName();
}
