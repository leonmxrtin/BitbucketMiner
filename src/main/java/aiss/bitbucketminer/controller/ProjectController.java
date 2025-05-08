package aiss.bitbucketminer.controller;

import aiss.bitbucketminer.exception.ProjectNotFoundException;
import aiss.bitbucketminer.model.Project;
import aiss.bitbucketminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bitbucket")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/{workspace}/{repo_slug}")
    public Project getProject(@PathVariable String workspace,
                              @PathVariable String repo_slug,
                              @RequestParam(defaultValue = "5") Integer nCommits,
                              @RequestParam(defaultValue = "5") Integer nIssues,
                              @RequestParam(defaultValue = "2") Integer maxPages)
            throws ProjectNotFoundException {
        return projectService.getProject(workspace, repo_slug, nCommits, nIssues, maxPages);
    }

    @PostMapping("/{workspace}/{repo_slug}")
    public Project createProject(@PathVariable String workspace,
                                 @PathVariable String repo_slug,
                                 @RequestParam(defaultValue = "5") Integer nCommits,
                                 @RequestParam(defaultValue = "5") Integer nIssues,
                                 @RequestParam(defaultValue = "2") Integer maxPages)
            throws ProjectNotFoundException {
        return projectService.createProject(workspace, repo_slug, nCommits, nIssues, maxPages);
    }
}

