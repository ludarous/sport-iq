package cz.sportiq.service;

import cz.sportiq.service.dto.AthleteWorkoutDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AthleteWorkout.
 */
public interface AthleteWorkoutService {

    /**
     * Save a athleteWorkout.
     *
     * @param athleteWorkoutDTO the entity to save
     * @return the persisted entity
     */
    AthleteWorkoutDTO save(AthleteWorkoutDTO athleteWorkoutDTO);

    /**
     * Get all the athleteWorkouts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AthleteWorkoutDTO> findAll(Pageable pageable);


    /**
     * Get the "id" athleteWorkout.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AthleteWorkoutDTO> findOne(Long id);

    /**
     * Delete the "id" athleteWorkout.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the athleteWorkout corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AthleteWorkoutDTO> search(String query, Pageable pageable);

    Optional<AthleteWorkoutDTO> findByWorkoutIdAndAthleteEventId(Long workoutId, Long athleteEventId);
}
