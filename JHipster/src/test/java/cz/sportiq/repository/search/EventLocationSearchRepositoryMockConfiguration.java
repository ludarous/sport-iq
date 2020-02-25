package cz.sportiq.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link EventLocationSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EventLocationSearchRepositoryMockConfiguration {

    @MockBean
    private EventLocationSearchRepository mockEventLocationSearchRepository;

}