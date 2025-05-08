package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.Commit;
import aiss.bitbucketminer.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommitService {

    @Autowired
    RestTemplate restTemplate;

    public List<Commit> getCommits(String repoUri, Integer pageLen, Integer maxPages) {
        List<Commit> commits = new ArrayList<>();

        // Fetch data from the API until limits are satisfied
        for (int page = 1; page <= maxPages; page++) {
            String commitUri = repoUri + "/commits" + "?pagelen=" + pageLen + "&page=" + page;
            Page<Commit> fetchedPage = restTemplate.exchange(commitUri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<Page<Commit>>(){}).getBody();

            if (fetchedPage != null) {
                List<Commit> fetchedCommits = fetchedPage.getValues();
                commits.addAll(fetchedCommits);
            }
        }

        return commits;
    }
}
