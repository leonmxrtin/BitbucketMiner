
package aiss.bitbucketminer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

    @JsonProperty("uuid")
    private String id;
    @JsonProperty("name")
    private String name;
    private String web_url;
    private List<Commit> commits;
    private List<Issue> issues;

    public Project() {
        commits = new ArrayList<>();
        issues = new ArrayList<>();
    }

    @JsonProperty("uuid")
    public String getId() {
        return id;
    }

    @JsonProperty("uuid")
    public void setId(String uuid) {
        this.id = uuid;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public String getWeb_url() { return web_url; }

    public void setWeb_url(String web_url) { this.web_url = web_url; }

    public List<Commit> getCommits() { return commits; }

    public void setCommits(List<Commit> commits) { this.commits = commits; }

    public List<Issue> getIssues() { return issues; }

    public void setIssues(List<Issue> issues) { this.issues = issues; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Project.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("commits");
        sb.append('=');
        sb.append(((this.commits == null)?"<null>":this.commits));
        sb.append(',');
        sb.append("issues");
        sb.append('=');
        sb.append(((this.issues == null)?"<null>":this.issues));
        sb.append(',');

        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
