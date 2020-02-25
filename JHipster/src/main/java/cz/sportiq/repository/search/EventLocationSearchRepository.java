package cz.sportiq.repository.search;
import cz.sportiq.domain.EventLocation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EventLocation} entity.
 */
public interface EventLocationSearchRepository extends ElasticsearchRepository<EventLocation, Long> {
}
