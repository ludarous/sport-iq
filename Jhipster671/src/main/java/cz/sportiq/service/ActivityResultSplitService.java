package cz.sportiq.service;

import cz.sportiq.service.dto.ActivityResultSplitDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cz.sportiq.domain.ActivityResultSplit}.
 */
public interface ActivityResultSplitService {

    /**
     * Save a activityResultSplit.
     *
     * @param activityResultSplitDTO the entity to save.
     * @return the persisted entity.
     */
    ActivityResultSplitDTO save(ActivityResultSplitDTO activityResultSplitDTO);

    /**
     * Get all the activityResultSplits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActivityResultSplitDTO> findAll(Pageable pageable);

    /**
     * Get the "id" activityResultSplit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActivityResultSplitDTO> findOne(Long id);

    /**
     * Delete the "id" activityResultSplit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
