package aiss.bitbucketminer.service;

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

    @Value("${bitbucket.uri}")
    private String bitbucketUri;

    @Value("${gitminer.uri}")
    private String gitminerUri;

    // TODO: add commit and issue logic
    public Project getProject(String workspace, String repoSlug, Integer nCommits, Integer nIssues, Integer maxPages)
            throws ProjectNotFoundException {
        String repoUri = bitbucketUri + "/repositories/" + workspace + "/" + repoSlug;
        ResponseEntity<Project> response = restTemplate.getForEntity(repoUri, Project.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new ProjectNotFoundException();
        }

        Project project = response.getBody();

        project.setCommits(commitService.getCommits(workspace, repoSlug, nCommits, maxPages));

//        Issue[] issues = restTemplate.getForObject(uri + "/issues", Issue[].class);
//        if (!isNull(issues)) {
//            issues = Arrays.stream(issues).toList().limit(nIssues);
//            project.setIssues(issues);
//        }
        return project;

    }

    public Project createProject(String workspace, String repoSlug, Integer nCommits, Integer nIssues, Integer maxPages)
            throws ProjectNotFoundException {
        Project project = getProject(workspace, repoSlug, nCommits, nIssues, maxPages);
        return restTemplate.postForObject(gitminerUri + "/projects", project, Project.class);
    }
}
