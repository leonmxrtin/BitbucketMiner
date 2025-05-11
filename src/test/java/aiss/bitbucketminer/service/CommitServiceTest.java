package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.PageNotFoundException;
import aiss.bitbucketminer.model.Commit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

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
        Integer maxLength = 5;
        Integer maxPages = 2;

        String expectedCommitId = "67a0362b29f34c45251ce88c5851756fb30a65cc";
        String expectedCommitWebUrl = "https://bitbucket.org/gentlero/bitbucket-api/commits/67a0362b29f34c45251ce88c5851756fb30a65cc";
        String expectedCommitAuthorName = "Alexandru Guzinschi";
        String expectedCommitAuthorEmail = "b3nyb3ny@gmail.com";
        String expectedCommitAuthoredDate = "2020-06-23T12:29:38+00:00";
        String expectedCommitTitle = "Added PHP 7.3, 7.4 and nightly to CI matrix";
        String expectedCommitMessage = """
                Added PHP 7.3, 7.4 and nightly to CI matrix
                
                - added PHP 7.3, 7.4 and nightly to CI matrix
                - updated phpunit config
                - use null coalescing operator in `OAuthPlugin::initToken` in order to avoid precedence issues on PHP 7.4
                """;

        List<Commit> commits = commitService.getCommits(workspace, repo_slug, maxLength, maxPages);
        assertNotNull(commits);
        assertFalse(commits.isEmpty());

        Optional<Commit> optCommit = commits.stream().filter(c -> c.getId().equals(expectedCommitId)).findFirst();
        assertTrue(optCommit.isPresent());

        Commit commit = optCommit.get();
        assertEquals(expectedCommitId, commit.getId());
        assertEquals(expectedCommitWebUrl, commit.getWebUrl());
        assertEquals(expectedCommitAuthorName, commit.getAuthorName());
        assertEquals(expectedCommitAuthorEmail, commit.getAuthorEmail());
        assertEquals(expectedCommitAuthoredDate, commit.getAuthoredDate());
        assertEquals(expectedCommitTitle, commit.getTitle());
        assertEquals(expectedCommitMessage, commit.getMessage());
        System.out.println(commits);
    }
}