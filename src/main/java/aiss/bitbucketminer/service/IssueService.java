package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.IssueNotFoundException;
import aiss.bitbucketminer.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IssueService {

    @Autowired
    RestTemplate restTemplate;

    private final String baseUri = "https://api.bitbucket.org";
    private final String apiVersion = "/2.0";

    public Issue getIssue(String issueId) throws IssueNotFoundException {
        String uri = baseUri + "/issues/" + issueId;
        ResponseEntity<Issue> response = restTemplate.getForEntity(uri, Issue.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IssueNotFoundException();
        }
        Issue issue = response.getBody();
        issue.setWebUrl(baseUri + issue.getTitle());
        return issue;
    }
}
