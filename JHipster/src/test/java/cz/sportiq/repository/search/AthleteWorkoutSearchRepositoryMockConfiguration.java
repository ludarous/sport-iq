package cz.sportiq.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AthleteWorkoutSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AthleteWorkoutSearchRepositoryMockConfiguration {

    @MockBean
    private AthleteWorkoutSearchRepository mockAthleteWorkoutSearchRepository;

}
