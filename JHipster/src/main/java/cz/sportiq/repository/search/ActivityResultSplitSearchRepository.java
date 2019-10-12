package cz.sportiq.repository.search;

import cz.sportiq.domain.ActivityResultSplit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ActivityResultSplit entity.
 */
public interface ActivityResultSplitSearchRepository extends ElasticsearchRepository<ActivityResultSplit, Long> {
}
