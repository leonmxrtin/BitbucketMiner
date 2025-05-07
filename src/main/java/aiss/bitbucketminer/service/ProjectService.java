package aiss.bitbucketminer.service;

import aiss.bitbucketminer.controller.ProjectController;
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

    private final String gitMinerUri = "http://localhost:8080/gitminer/projects";

    // TODO: add commit and issue logic
    public Project getProject(String workspace, String repoSlug, Integer nCommits, Integer nIssues, Integer maxPages)
            throws ProjectNotFoundException {
        String uri = baseUri + apiVersion + "/repositories/" + workspace + "/" + repoSlug + "?pageLen=" + maxPages;
        ResponseEntity<Project> response = restTemplate.getForEntity(uri, Project.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new ProjectNotFoundException();
        }

        Project project = response.getBody();
//        Commit[] commits = restTemplate.getForObject(uri + "/commits", Commit[].class);
//        Issue[] issues = restTemplate.getForObject(uri + "/issues", Issue[].class);
//        if (!isNull(commits)) {
//            commits = Arrays.stream(commits).toList().limit(nCommits);
//            project.setCommits(commits);
//        }
//        if (!isNull(issues)) {
//            issues = Arrays.stream(issues).toList().limit(nIssues);
//            project.setIssues(issues);
//        }
        return project;

    }

    public Project createProject(String workspace, String repoSlug, Integer nCommits, Integer nIssues, Integer maxPages)
            throws ProjectNotFoundException {
        Project project = getProject(workspace, repoSlug, nCommits, nIssues, maxPages);
        return restTemplate.postForObject(gitMinerUri, project, Project.class);
    }
}
