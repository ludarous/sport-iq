package cz.sportiq.repository.search;
import cz.sportiq.domain.ActivityResult;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ActivityResult} entity.
 */
public interface ActivityResultSearchRepository extends ElasticsearchRepository<ActivityResult, Long> {
}
