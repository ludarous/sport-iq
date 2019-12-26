package cz.sportiq.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AthleteActivitySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AthleteActivitySearchRepositoryMockConfiguration {

    @MockBean
    private AthleteActivitySearchRepository mockAthleteActivitySearchRepository;

}
