package aiss.bitbucketminer.controller;

import aiss.bitbucketminer.exception.PageNotFoundException;
import aiss.bitbucketminer.exception.ProjectNotFoundException;
import aiss.bitbucketminer.model.Project;
import aiss.bitbucketminer.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bitbucket")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Operation(summary = "Retrieve a project",
            description = "Get a project given its workspace and slug",
            tags = {"projects", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")})
    })
    @GetMapping("/{workspace}/{repo_slug}")
    public Project getProject(@PathVariable String workspace,
                              @PathVariable String repo_slug,
                              @RequestParam(defaultValue = "5") Integer nCommits,
                              @RequestParam(defaultValue = "5") Integer nIssues,
                              @RequestParam(defaultValue = "2") Integer maxPages)
            throws ProjectNotFoundException, PageNotFoundException {
        return projectService.getProject(workspace, repo_slug, nCommits, nIssues, maxPages);
    }

    @Operation(summary = "Retrieve and store a project",
            description = "Get a project given its workspace and slug and send it to GitMiner",
            tags = {"projects", "post"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")})
    })
    @PostMapping("/{workspace}/{repo_slug}")
    public Project createProject(@PathVariable String workspace,
                                 @PathVariable String repo_slug,
                                 @RequestParam(defaultValue = "5") Integer nCommits,
                                 @RequestParam(defaultValue = "5") Integer nIssues,
                                 @RequestParam(defaultValue = "2") Integer maxPages)
            throws ProjectNotFoundException, PageNotFoundException {
        return projectService.createProject(workspace, repo_slug, nCommits, nIssues, maxPages);
    }
}

