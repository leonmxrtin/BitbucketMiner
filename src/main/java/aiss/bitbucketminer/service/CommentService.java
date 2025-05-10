package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.Comment;
import aiss.bitbucketminer.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${bitbucket.uri}")
    private String bitbucketUri;

    public List<Comment> getIssueComments(String workspace, String repoSlug, String issueId, Integer pageLen, Integer maxPages) {
        String commentsUri = bitbucketUri + "/repositories/" + workspace + "/" + repoSlug + "/issues/" + issueId + "/comments";
        List<Comment> comments = new ArrayList<>();

        // Fetch data from the API until limits are satisfied
        for (int page = 1; page <= maxPages; page++) {
            String pageUri = commentsUri + "?pagelen=" + pageLen + "&page=" + page;
            Page<Comment> fetchedPage = restTemplate.exchange(pageUri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<Page<Comment>>(){}).getBody();

            if (fetchedPage != null) {
                List<Comment> fetchedComments = fetchedPage.getValues();
                comments.addAll(fetchedComments);
            }
        }

        return comments;
    }
}
