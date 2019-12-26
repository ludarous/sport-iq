package cz.sportiq.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link WorkoutCategorySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class WorkoutCategorySearchRepositoryMockConfiguration {

    @MockBean
    private WorkoutCategorySearchRepository mockWorkoutCategorySearchRepository;

}
