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
        Project project = projectService.getProject(workspace, repo_slug, nCommits, nIssues, maxPages);
        assertNotNull(project);
        assertEquals(nCommits * maxPages, project.getCommits().size());
        assertEquals(nIssues * maxPages, project.getIssues().size());
        System.out.println(project);
    }

    @Test
    void createProject() throws ProjectNotFoundException, PageNotFoundException {
        String workspace = "gentlero";
        String repo_slug = "bitbucket-api";
        Integer nCommits = 4;
        Integer nIssues = 5;
        Integer maxPages = 2;
        Project project = projectService.getProject(workspace, repo_slug, nCommits, nIssues, maxPages);
        assertNotNull(project);
        assertEquals(nCommits * maxPages, project.getCommits().size());
        assertEquals(nIssues * maxPages, project.getIssues().size());
        System.out.println(project);
    }
}