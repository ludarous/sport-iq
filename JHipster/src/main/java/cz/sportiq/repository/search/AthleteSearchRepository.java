package cz.sportiq.repository.search;

import cz.sportiq.domain.Athlete;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Athlete entity.
 */
public interface AthleteSearchRepository extends ElasticsearchRepository<Athlete, Long> {
}
