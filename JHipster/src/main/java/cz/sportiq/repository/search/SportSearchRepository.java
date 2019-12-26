package cz.sportiq.repository.search;
import cz.sportiq.domain.Sport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Sport} entity.
 */
public interface SportSearchRepository extends ElasticsearchRepository<Sport, Long> {
}
