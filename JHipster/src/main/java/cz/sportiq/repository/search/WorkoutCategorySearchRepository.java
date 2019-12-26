package cz.sportiq.repository.search;
import cz.sportiq.domain.WorkoutCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link WorkoutCategory} entity.
 */
public interface WorkoutCategorySearchRepository extends ElasticsearchRepository<WorkoutCategory, Long> {
}
