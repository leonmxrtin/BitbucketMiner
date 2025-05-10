package aiss.bitbucketminer.service;

import aiss.bitbucketminer.exception.PageNotFoundException;
import aiss.bitbucketminer.model.Commit;
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
public class CommitService {

    @Autowired
    RestTemplate restBitBucket;

    @Value("${bitbucket.uri}")
    private String bitbucketUri;

    public List<Commit> getCommits(String workspace, String repoSlug, Integer pageLen, Integer maxPages) throws PageNotFoundException {
        String commitsUri = "/repositories/" + workspace + "/" + repoSlug + "/commits";
        String pageUri = commitsUri + "?pagelen=" + pageLen + "&page=1";
        List<Commit> commits = new ArrayList<>();

        // Fetch data from the API until limits are satisfied or no more pages exist
        for (int page = 1; page <= maxPages; page++) {
            ResponseEntity<Page<Commit>> response = restBitBucket.exchange(pageUri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>(){});

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new PageNotFoundException();
            }

            Page<Commit> fetchedPage = response.getBody();
            commits.addAll(fetchedPage.getValues());

            if (fetchedPage.getNext() == null) {
                break;
            }
            pageUri = fetchedPage.getNext();
        }

        return commits;
    }
}
