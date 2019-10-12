package cz.sportiq.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of AthleteActivityResultSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AthleteActivityResultSearchRepositoryMockConfiguration {

    @MockBean
    private AthleteActivityResultSearchRepository mockAthleteActivityResultSearchRepository;

}
