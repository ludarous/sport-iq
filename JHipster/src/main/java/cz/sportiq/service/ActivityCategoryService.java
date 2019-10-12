package cz.sportiq.service;

import cz.sportiq.service.dto.ActivityCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ActivityCategory.
 */
public interface ActivityCategoryService {

    /**
     * Save a activityCategory.
     *
     * @param activityCategoryDTO the entity to save
     * @return the persisted entity
     */
    ActivityCategoryDTO save(ActivityCategoryDTO activityCategoryDTO);

    /**
     * Get all the activityCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ActivityCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" activityCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ActivityCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" activityCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the activityCategory corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ActivityCategoryDTO> search(String query, Pageable pageable);
}
