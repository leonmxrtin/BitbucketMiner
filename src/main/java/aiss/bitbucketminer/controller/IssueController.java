package aiss.bitbucketminer.controller;

import aiss.bitbucketminer.exception.ProjectNotFoundException;
import aiss.bitbucketminer.model.Issue;
import aiss.bitbucketminer.model.Project;
import aiss.bitbucketminer.service.IssueService;
import aiss.bitbucketminer.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bitbucket")
public class IssueController {

    @Autowired
    IssueService issueService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/{workspace}/{repo_slug}")
    public Issue getIssue(@PathVariable String workspace,
                          @PathVariable String repo_slug,
                          @RequestParam String id)
            throws ProjectNotFoundException {
        return issueService.getIssue(id);
    }
}


