package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.PageNotFoundException;
import aiss.bitbucketminer.model.Issue;
import aiss.bitbucketminer.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Integer length = 5;
        Integer maxPages = 2;

        String expectedIssueId = "87";
        String expectedIssueTitle = "Auto-convert responses ?";
        String expectedIssueDescription = "Liking this library a lot but I have a question, sorry maybe I did not understand correctly, but can we auto return proper objects when calling the API, like an array without json\\_decode ourselves ? Or setup a listener to do so ? ";
        String expectedIssueState = "duplicate";
        User expectedIssueAssignee = null;
        String expectedIssueCommentId = "57887979";
        String expectedIssueAuthorId = "{ef8e4111-1bef-40cd-9e4e-d50546ca5a50}";
        String expectedIssueCreatedAt = "2020-06-24T08:49:17.338315+00:00";
        String expectedIssueUpdatedAt = "2020-06-24T10:13:00.882866+00:00";
        String expectedIssueClosedAt = null;
        Integer expectedIssueVotes = 0;
        List<String> expectedIssueLabels = Arrays.asList("bug", "minor");

        List<Issue> issues = issueService.getIssues(workspace, repo_slug, length, maxPages);
        assertNotNull(issues);
        assertFalse(issues.isEmpty());

        Optional<Issue> optIssue = issues.stream().filter(i -> i.getId().equals(expectedIssueId)).findFirst();
        assertTrue(optIssue.isPresent());

        Issue issue = optIssue.get();
        assertEquals(expectedIssueId, issue.getId());
        assertEquals(expectedIssueTitle, issue.getTitle());
        assertTrue(issue.getDescription().contains(expectedIssueDescription));
        assertEquals(expectedIssueState, issue.getState());
        assertEquals(expectedIssueAssignee, issue.getAssignee());
        assertEquals(expectedIssueAuthorId, issue.getAuthor().getId());
        assertEquals(expectedIssueCreatedAt, issue.getCreatedAt());
        assertEquals(expectedIssueUpdatedAt, issue.getUpdatedAt());
        assertEquals(expectedIssueClosedAt, issue.getClosedAt());
        assertEquals(expectedIssueVotes, issue.getVotes());
        assertEquals(expectedIssueLabels, issue.getLabels());

        assertNotNull(issue.getComments());
        assertFalse(issue.getComments().isEmpty());
        assertTrue(issue.getComments().stream().anyMatch(c -> c.getId().equals(expectedIssueCommentId)));

        System.out.println(issues);
    }
}