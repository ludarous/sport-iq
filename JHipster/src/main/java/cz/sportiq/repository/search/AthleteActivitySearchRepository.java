package cz.sportiq.repository.search;

import cz.sportiq.domain.AthleteActivity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AthleteActivity entity.
 */
public interface AthleteActivitySearchRepository extends ElasticsearchRepository<AthleteActivity, Long> {
}
