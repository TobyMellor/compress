package uk.co.tobymellor.compress.models.articles;

public interface ArticleInput {
    String getTitle();

    String getAuthorSummary();

    String getThreeSentenceSummary();

    String getSevenSentenceSummary();

    String getArticleLink();

    String getDate();

    String getHumanDate();

    String getNewsOutletGenreId();

    String getAuthorName();
}
