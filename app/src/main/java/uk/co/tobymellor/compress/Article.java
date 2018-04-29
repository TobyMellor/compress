package uk.co.tobymellor.compress;

public class Article {
    private final String title;
    private final String authorSummary;
    private final String authorCompany;
    private final String authorName;

    public Article(String title, String authorSummary, String authorCompany, String authorName) {
        this.title = title;
        this.authorSummary = authorSummary;
        this.authorCompany = authorCompany;
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorSummary() {
        return authorSummary;
    }

    public String getAuthorCompany() {
        return authorCompany;
    }

    public String getAuthorName() {
        return String.format("By %s", authorName);
    }
}
