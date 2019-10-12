package cz.sportiq.repository.search;

import cz.sportiq.domain.Workout;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Workout entity.
 */
public interface WorkoutSearchRepository extends ElasticsearchRepository<Workout, Long> {
}
