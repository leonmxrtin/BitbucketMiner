package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.PageNotFoundException;
import aiss.bitbucketminer.model.Issue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IssueServiceTest {

    @Autowired
    IssueService issueService;

    @Test
    @DisplayName("Get all issues")
    void getIssues() throws PageNotFoundException {
        String workspace = "gentlero";
        String repo_slug = "bitbucket-api";
        Integer maxPages = 2;
        Integer length = 5;
        String id = "87";
        String title = "Auto-convert responses ?";
        List<Issue> issues = issueService.getIssues(workspace, repo_slug, length, maxPages);

        assertNotNull(issues);
        assertFalse(issues.isEmpty());
        assertTrue(issues.stream().anyMatch(i -> i.getId().equals(id) && i.getTitle().equals(title)));
        System.out.println(issues);
    }
}