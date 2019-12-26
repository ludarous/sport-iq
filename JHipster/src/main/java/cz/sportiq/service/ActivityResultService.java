package cz.sportiq.service;

import cz.sportiq.service.dto.ActivityResultDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cz.sportiq.domain.ActivityResult}.
 */
public interface ActivityResultService {

    /**
     * Save a activityResult.
     *
     * @param activityResultDTO the entity to save.
     * @return the persisted entity.
     */
    ActivityResultDTO save(ActivityResultDTO activityResultDTO);

    /**
     * Get all the activityResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActivityResultDTO> findAll(Pageable pageable);


    /**
     * Get the "id" activityResult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActivityResultDTO> findOne(Long id);

    /**
     * Delete the "id" activityResult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the activityResult corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActivityResultDTO> search(String query, Pageable pageable);
}
