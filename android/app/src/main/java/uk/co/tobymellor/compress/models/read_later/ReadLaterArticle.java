package uk.co.tobymellor.compress.models.read_later;

import java.util.Date;

import uk.co.tobymellor.compress.models.articles.Article;
import uk.co.tobymellor.compress.models.articles.ArticleInput;

public class ReadLaterArticle extends Article {
    private final Date addedAt;

    public ReadLaterArticle(ReadLaterArticleInput readLaterArticleInput) {
        super(readLaterArticleInput);

        this.addedAt = readLaterArticleInput.getAddedAt();
    }

    public Date addedAt() {
        return addedAt;
    }
}
