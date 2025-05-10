package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.PageNotFoundException;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CommentService commentService;

    @Value("${bitbucket.uri}")
    private String bitbucketUri;

    public List<Issue> getIssues(String workspace, String repoSlug, Integer pageLen, Integer maxPages) throws PageNotFoundException {
        String issuesUri = bitbucketUri + "/repositories/" + workspace + "/" + repoSlug + "/issues";
        String pageUri = issuesUri + "?pagelen=" + pageLen + "&page=1";
        List<Issue> issues = new ArrayList<>();

        // Fetch data from the API until limits are satisfied or no more pages exist
        for (int page = 1; page <= maxPages; page++) {
            ResponseEntity<Page<Issue>> response = restTemplate.exchange(pageUri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>(){});

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new PageNotFoundException();
            }

            Page<Issue> fetchedPage = response.getBody();
            issues.addAll(fetchedPage.getValues());

            if (fetchedPage.getNext() == null) {
                break;
            }
            pageUri = fetchedPage.getNext();
        }

        for (Issue issue : issues) {
            issue.setComments(commentService.getIssueComments(workspace, repoSlug, issue.getId(), pageLen, maxPages));
        }

        return issues;
    }
}
