package cz.sportiq.service;

import cz.sportiq.service.dto.WorkoutDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cz.sportiq.domain.Workout}.
 */
public interface WorkoutService {

    /**
     * Save a workout.
     *
     * @param workoutDTO the entity to save.
     * @return the persisted entity.
     */
    WorkoutDTO save(WorkoutDTO workoutDTO);

    /**
     * Get all the workouts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkoutDTO> findAll(Pageable pageable);

    /**
     * Get all the workouts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<WorkoutDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" workout.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkoutDTO> findOne(Long id);

    /**
     * Delete the "id" workout.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the workout corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkoutDTO> search(String query, Pageable pageable);
}
