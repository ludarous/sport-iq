package cz.sportiq.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ActivityCategorySearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ActivityCategorySearchRepositoryMockConfiguration {

    @MockBean
    private ActivityCategorySearchRepository mockActivityCategorySearchRepository;

}
