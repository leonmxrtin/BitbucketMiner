package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.IssueNotFoundException;
import aiss.bitbucketminer.exception.PageNotFoundException;
import aiss.bitbucketminer.model.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Test
    @DisplayName("Get issue comments")
    void getIssueComments() throws IssueNotFoundException, PageNotFoundException {
        String workspace = "gentlero";
        String repo_slug = "bitbucket-api";
        String issueId = "87";
        Integer maxPages = 2;
        Integer length = 5;
        String id = "57887979";
        String username = "stephane-lou";
        List<Comment> comments = commentService.getIssueComments(workspace, repo_slug, issueId, length, maxPages);

        assertNotNull(comments);
        assertFalse(comments.isEmpty());
        assertTrue(comments.stream().anyMatch(c -> c.getId().equals(id) && c.getAuthor().getUsername().equals(username)));
        System.out.println(comments);
    }
}