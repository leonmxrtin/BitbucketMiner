package aiss.bitbucketminer.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Issue {

    public Issue() {
        labels = new ArrayList<>();
    }

    private String id;
    private String title;
    private String state;
    private Integer votes;
    private User author;
    private User assignee;
    private List<Comment> comments;

    private String description;
    @JsonProperty("content")
    private void unpackDescription(JsonNode content) {
        this.description = content.get("raw").asText();
    }

    private List<String> labels;
    @JsonProperty("kind")
    private void parseKind(String kind) {
        labels.add(kind);
    }
    @JsonProperty("priority")
    private void parsePriority(String priority) {
        labels.add(priority);
    }

    @JsonProperty("created_at")
    @JsonAlias("created_on")
    private String createdAt;

    @JsonProperty("updated_at")
    @JsonAlias("updated_on")
    private String updatedAt;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("closed_at")
    public String getClosedAt() {
        return getState().equals("closed") ? getUpdatedAt() : null;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", state='" + state + '\'' +
                ", votes=" + votes +
                ", author=" + author +
                ", assignee=" + assignee +
                ", comments=" + comments +
                ", description='" + description + '\'' +
                ", labels=" + labels +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
