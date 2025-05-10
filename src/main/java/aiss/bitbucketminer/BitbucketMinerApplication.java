package aiss.bitbucketminer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BitbucketMinerApplication {

    @Value("${bitbucket.token}")
    private String token;

    @Value("${bitbucket.uri}")
    private String bitbucketUri;

    @Value("${gitminer.uri}")
    private String gitminerUri;

    public static void main(String[] args) {
        SpringApplication.run(BitbucketMinerApplication.class, args);
    }

    @Bean(name = "BitBucket")
    @Primary
    public RestTemplate restBitBucket(RestTemplateBuilder builder) {
        if (!token.isEmpty()) {
            builder = builder.defaultHeader("Authorization", "Bearer " + token);
        }
        return builder.rootUri(bitbucketUri).build();
    }

    @Bean(name = "GitMiner")
    public RestTemplate restGitMiner(RestTemplateBuilder builder) {
        return builder.rootUri(gitminerUri).build();
    }
}
