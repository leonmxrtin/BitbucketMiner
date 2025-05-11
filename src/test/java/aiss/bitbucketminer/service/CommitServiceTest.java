package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.PageNotFoundException;
import aiss.bitbucketminer.model.Commit;
import aiss.bitbucketminer.model.Issue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommitServiceTest {

    @Autowired
    CommitService commitService;

    @Test
    @DisplayName("Get all commits")
    void getCommits() throws PageNotFoundException {
        String workspace = "gentlero";
        String repo_slug = "bitbucket-api";
        Integer maxPages = 2;
        Integer maxLength = 5;
        String id = "67a0362b29f34c45251ce88c5851756fb30a65cc";
        String title = "Added PHP 7.3, 7.4 and nightly to CI matrix";

        List<Commit> commits = commitService.getCommits(workspace, repo_slug, maxLength, maxPages);
        assertNotNull(commits);
        assertFalse(commits.isEmpty());
        assertTrue(commits.stream().anyMatch(c -> c.getId().equals(id) && c.getTitle().equals(title)));
        System.out.println(commits);
    }
}