package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.IssueNotFoundException;
import aiss.bitbucketminer.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IssueService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${bitbucket.uri}")
    private String bitbucketUri;

    @Value("${gitminer.uri}")
    private String gitminerUri;

    private final String baseUri = "https://api.bitbucket.org";
    private final String apiVersion = "/2.0";

    // TODO: this wont work. this is not the correct path, as issue IDs are only unique within a repository
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
