package cz.sportiq.repository.search;
import cz.sportiq.domain.ActivityCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ActivityCategory} entity.
 */
public interface ActivityCategorySearchRepository extends ElasticsearchRepository<ActivityCategory, Long> {
}
