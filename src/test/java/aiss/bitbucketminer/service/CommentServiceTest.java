package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.PageNotFoundException;
import aiss.bitbucketminer.model.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Test
    @DisplayName("Get issue comments")
    void getIssueComments() throws PageNotFoundException {
        String workspace = "gentlero";
        String repo_slug = "bitbucket-api";
        String issueId = "87";
        Integer length = 5;
        Integer maxPages = 2;

        String expectedCommentId = "57889140";
        String expectedCommentBody = "No, there are no model objects available. You must consume de raw data and/or build your own models.";
        String expectedCommentCreatedAt = "2020-06-24T10:12:47.502123+00:00";
        String expectedCommentUpdatedAt = null;
        String expectedCommentAuthorId = "{a5cc4eaa-5665-4e36-9a20-8978e9272f12}";

        List<Comment> comments = commentService.getIssueComments(workspace, repo_slug, issueId, length, maxPages);
        assertNotNull(comments);
        assertFalse(comments.isEmpty());

        Optional<Comment> optComment = comments.stream().filter(c -> c.getId().equals(expectedCommentId)).findFirst();
        assertTrue(optComment.isPresent());

        Comment comment = optComment.get();
        assertEquals(expectedCommentId, comment.getId());
        assertEquals(expectedCommentBody, comment.getBody());
        assertEquals(expectedCommentCreatedAt, comment.getCreatedAt());
        assertEquals(expectedCommentUpdatedAt, comment.getUpdatedAt());
        assertEquals(expectedCommentAuthorId, comment.getAuthor().getId());
        System.out.println(comments);
    }
}