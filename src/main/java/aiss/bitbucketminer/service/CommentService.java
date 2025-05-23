package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.PageNotFoundException;
import aiss.bitbucketminer.model.Comment;
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
public class CommentService {

    @Autowired
    RestTemplate restBitBucket;

    public List<Comment> getIssueComments(String workspace, String repoSlug, String issueId, Integer pageLen, Integer maxPages) throws PageNotFoundException {
        String commentsUri = "/repositories/" + workspace + "/" + repoSlug + "/issues/" + issueId + "/comments";
        String pageUri = commentsUri + "?pagelen=" + pageLen + "&page=1";
        List<Comment> comments = new ArrayList<>();

        // Fetch data from the API until limits are satisfied or no more pages exist
        for (int page = 1; page <= maxPages; page++) {
            ResponseEntity<Page<Comment>> response = restBitBucket.exchange(pageUri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>(){});

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new PageNotFoundException();
            }

            Page<Comment> fetchedPage = response.getBody();
            comments.addAll(fetchedPage.getValues());

            if (fetchedPage.getNext() == null) {
                break;
            }
            pageUri = fetchedPage.getNext();
        }

        return comments;
    }
}
