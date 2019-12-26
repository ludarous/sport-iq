package cz.sportiq.repository.search;
import cz.sportiq.domain.AthleteWorkout;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AthleteWorkout} entity.
 */
public interface AthleteWorkoutSearchRepository extends ElasticsearchRepository<AthleteWorkout, Long> {
}
