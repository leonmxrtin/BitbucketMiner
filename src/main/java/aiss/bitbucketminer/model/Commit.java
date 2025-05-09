package aiss.bitbucketminer.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commit {

    @JsonAlias("hash")
    private String id;

    private String title;

    private String message;

    @JsonProperty("message")
    private void parseMessage(String message) {
        this.message = this.title = message;

        // if multi-line message, set title to its first line
        if (message.lines().count() > 1) {
            this.title = message.lines().findFirst().get();
        }
    }

    @JsonProperty("author_name")
    private String authorName;

    @JsonProperty("author_email")
    private String authorEmail;

    @JsonProperty("author")
    private void unpackAuthor(JsonNode author) {
        this.authorName = author.get("user").get("display_name").asText();

        Matcher emailRegex = Pattern.compile(".* <(.*)>").matcher(author.get("raw").asText());
        if (emailRegex.find()) {
            this.authorEmail = emailRegex.group(1);
        }
    }

    @JsonAlias("date")
    @JsonProperty("authored_date")
    private String authoredDate;

    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("links")
    private void unpackWebUrl(JsonNode links) {
        this.webUrl = links.get("html").get("href").asText();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthoredDate() {
        return authoredDate;
    }

    public void setAuthoredDate(String authoredDate) {
        this.authoredDate = authoredDate;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                ", authoredDate='" + authoredDate + '\'' +
                ", webUrl='" + webUrl + '\'' +
                '}';
    }
}
