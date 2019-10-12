package cz.sportiq.repository.search;

import cz.sportiq.domain.AthleteActivityResult;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AthleteActivityResult entity.
 */
public interface AthleteActivityResultSearchRepository extends ElasticsearchRepository<AthleteActivityResult, Long> {
}
