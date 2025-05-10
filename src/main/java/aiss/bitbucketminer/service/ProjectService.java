package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.PageNotFoundException;
import aiss.bitbucketminer.exception.ProjectNotFoundException;
import aiss.bitbucketminer.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CommitService commitService;

    @Autowired
    IssueService issueService;

    @Value("${bitbucket.uri}")
    private String bitbucketUri;

    @Value("${gitminer.uri}")
    private String gitminerUri;

    public Project getProject(String workspace, String repoSlug, Integer nCommits, Integer nIssues, Integer maxPages)
            throws ProjectNotFoundException, PageNotFoundException {
        String repoUri = bitbucketUri + "/repositories/" + workspace + "/" + repoSlug;
        ResponseEntity<Project> response = restTemplate.getForEntity(repoUri, Project.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new ProjectNotFoundException();
        }

        Project project = response.getBody();

        project.setCommits(commitService.getCommits(workspace, repoSlug, nCommits, maxPages));
        project.setIssues(issueService.getIssues(workspace, repoSlug, nIssues, maxPages));

        return project;

    }

    public Project createProject(String workspace, String repoSlug, Integer nCommits, Integer nIssues, Integer maxPages)
            throws ProjectNotFoundException, PageNotFoundException {
        Project project = getProject(workspace, repoSlug, nCommits, nIssues, maxPages);
        return restTemplate.postForObject(gitminerUri + "/projects", project, Project.class);
    }
}
