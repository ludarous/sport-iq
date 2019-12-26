package cz.sportiq.repository.search;
import cz.sportiq.domain.ActivityResultSplit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ActivityResultSplit} entity.
 */
public interface ActivityResultSplitSearchRepository extends ElasticsearchRepository<ActivityResultSplit, Long> {
}
