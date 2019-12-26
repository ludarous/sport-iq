package cz.sportiq.repository.search;
import cz.sportiq.domain.AthleteActivityResultSplit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AthleteActivityResultSplit} entity.
 */
public interface AthleteActivityResultSplitSearchRepository extends ElasticsearchRepository<AthleteActivityResultSplit, Long> {
}
