package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.ProjectNotFoundException;
import aiss.bitbucketminer.model.Commit;
import aiss.bitbucketminer.model.Issue;
import aiss.bitbucketminer.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;

    private final String baseUri = "https://api.bitbucket.org";
    private final String apiVersion = "/2.0";

    // TODO: add commit and issue logic
    public Project getProject(String workspace, String repoSlug, Integer nCommits, Integer nIssues, Integer maxPages)
            throws ProjectNotFoundException {
        String uri = baseUri + apiVersion + "/repositories/" + workspace + "/" + repoSlug + "?nCommits=" + nCommits + "&nIssues=" + nIssues + "&maxPages=" + maxPages;
        ResponseEntity<Project> response = restTemplate.getForEntity(uri, Project.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ProjectNotFoundException();
        }
        Project project = response.getBody();
        project.setWeb_url(baseUri + project.getName());
//        Commit[] commits = restTemplate.getForObject(uri + "/commits", Commit[].class);
//        Issue[] issues = restTemplate.getForObject(uri + "/issued", Issue[].class);
//        if (!isNull(commits)) {
//            project.setCommits(Arrays.stream(commits).toList());
//        }
//        if (!isNull(issues)) {
//            project.setIssues(Arrays.stream(issues).toList());
//        }
        return project;

    }
}
