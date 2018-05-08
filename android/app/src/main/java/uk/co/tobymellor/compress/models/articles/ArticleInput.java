package uk.co.tobymellor.compress.models.articles;

public interface ArticleInput {
    int getId();

    String getTitle();

    String getAuthorSummary();

    String getShortSentenceSummary();

    String getLongSentenceSummary();

    String getArticleLink();

    String getDate();

    String getHumanDate();

    int getNewsOutletGenreId();

    int getAuthorId();

    String getAuthorName();
}
