package cz.sportiq.repository.search;
import cz.sportiq.domain.AthleteEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AthleteEvent} entity.
 */
public interface AthleteEventSearchRepository extends ElasticsearchRepository<AthleteEvent, Long> {
}
