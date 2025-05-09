package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.Commit;
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
public class CommitService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${bitbucket.uri}")
    private String bitbucketUri;

    public List<Commit> getCommits(String workspace, String repoSlug, Integer pageLen, Integer maxPages) {
        String commitsUri = bitbucketUri + "/repositories/" + workspace + "/" + repoSlug + "/commits";
        List<Commit> commits = new ArrayList<>();

        // Fetch data from the API until limits are satisfied
        for (int page = 1; page <= maxPages; page++) {
            String pageUri = commitsUri + "?pagelen=" + pageLen + "&page=" + page;
            Page<Commit> fetchedPage = restTemplate.exchange(pageUri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<Page<Commit>>(){}).getBody();

            if (fetchedPage != null) {
                List<Commit> fetchedCommits = fetchedPage.getValues();
                commits.addAll(fetchedCommits);
            }
        }

        return commits;
    }
}
