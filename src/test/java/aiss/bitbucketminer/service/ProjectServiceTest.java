package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.PageNotFoundException;
import aiss.bitbucketminer.exception.ProjectNotFoundException;
import aiss.bitbucketminer.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectServiceTest {

    @Autowired
    ProjectService projectService;

    @Test
    @DisplayName("Get a project")
    void getProject() throws ProjectNotFoundException, PageNotFoundException {
        String workspace = "gentlero";
        String repo_slug = "bitbucket-api";
        Integer nCommits = 4;
        Integer nIssues = 5;
        Integer maxPages = 2;

        String expectedProjectName = "bitbucket-api";
        String expectedProjectId = "{3dbfb660-9d49-4481-80b8-27dfc33d4246}";
        String expectedProjectWebUrl = "https://bitbucket.org/gentlero/bitbucket-api";
        String expectedProjectCommitId = "67a0362b29f34c45251ce88c5851756fb30a65cc";
        String expectedProjectIssueId = "87";

        Project project = projectService.getProject(workspace, repo_slug, nCommits, nIssues, maxPages);
        assertNotNull(project);
        assertNotNull(project.getCommits());
        assertNotNull(project.getIssues());
        assertFalse(project.getCommits().isEmpty());
        assertFalse(project.getIssues().isEmpty());

        assertEquals(expectedProjectName, project.getName());
        assertEquals(expectedProjectId, project.getId());
        assertEquals(expectedProjectWebUrl, project.getWebUrl());
        assertTrue(project.getCommits().stream().anyMatch(c -> c.getId().equals(expectedProjectCommitId)));
        assertTrue(project.getIssues().stream().anyMatch(i -> i.getId().equals(expectedProjectIssueId)));

        System.out.println(project);
    }

    @Test
    @DisplayName("Create a project in GitMiner")
    void createProject() throws ProjectNotFoundException, PageNotFoundException {
        String workspace = "gentlero";
        String repo_slug = "bitbucket-api";
        Integer nCommits = 4;
        Integer nIssues = 5;
        Integer maxPages = 2;

        String expectedProjectName = "bitbucket-api";
        String expectedProjectId = "{3dbfb660-9d49-4481-80b8-27dfc33d4246}";
        String expectedProjectWebUrl = "https://bitbucket.org/gentlero/bitbucket-api";
        String expectedProjectCommitId = "67a0362b29f34c45251ce88c5851756fb30a65cc";
        String expectedProjectIssueId = "87";

        Project project = projectService.createProject(workspace, repo_slug, nCommits, nIssues, maxPages);
        assertNotNull(project);
        assertNotNull(project.getCommits());
        assertNotNull(project.getIssues());
        assertFalse(project.getCommits().isEmpty());
        assertFalse(project.getIssues().isEmpty());

        assertEquals(expectedProjectName, project.getName());
        assertEquals(expectedProjectId, project.getId());
        assertEquals(expectedProjectWebUrl, project.getWebUrl());
        assertTrue(project.getCommits().stream().anyMatch(c -> c.getId().equals(expectedProjectCommitId)));
        assertTrue(project.getIssues().stream().anyMatch(i -> i.getId().equals(expectedProjectIssueId)));

        System.out.println(project);
    }
}