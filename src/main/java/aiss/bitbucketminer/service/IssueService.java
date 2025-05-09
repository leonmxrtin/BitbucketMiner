package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.IssueNotFoundException;
import aiss.bitbucketminer.model.Commit;
import aiss.bitbucketminer.model.Issue;
import aiss.bitbucketminer.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${bitbucket.uri}")
    private String bitbucketUri;

 //   @Value("${gitminer.uri}")
 //   private String gitminerUri;

 //   private final String baseUri = "https://api.bitbucket.org";
 //   private final String apiVersion = "/2.0";

    public List<Issue> getIssues(String workspace, String repoSlug, Integer pageLen, Integer maxPages) {
        String issuesUri = bitbucketUri + "/repositories/" + workspace + "/" + repoSlug + "/issues";
        List<Issue> issues = new ArrayList<>();

        for (int page = 1; page <= maxPages; page++) {
            String pageUri = issuesUri + "?pagelen=" + pageLen + "&page=" + page;
            Page<Issue> fetchedPage = restTemplate.exchange(pageUri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<Page<Issue>>(){}).getBody();

            if (fetchedPage != null) {
                List<Issue> fetchedIssues = fetchedPage.getValues();
                issues.addAll(fetchedIssues);
            }
        }
        return issues;
    }
/*
    // TODO: this wont work. this is not the correct path, as issue IDs are only unique within a repository
    public Issue getIssue(String issueId) throws IssueNotFoundException {
        String uri = baseUri + "/issues/" + issueId;
        ResponseEntity<Issue> response = restTemplate.getForEntity(uri, Issue.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IssueNotFoundException();
        }
        Issue issue = response.getBody();
        return issue;
    } */
}
