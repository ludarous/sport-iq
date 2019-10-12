package cz.sportiq.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of AthleteSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AthleteSearchRepositoryMockConfiguration {

    @MockBean
    private AthleteSearchRepository mockAthleteSearchRepository;

}
