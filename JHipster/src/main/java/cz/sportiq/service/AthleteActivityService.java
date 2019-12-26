package cz.sportiq.service;

import cz.sportiq.domain.Activity;
import cz.sportiq.domain.AthleteActivity;
import cz.sportiq.domain.AthleteWorkout;
import cz.sportiq.service.dto.AthleteActivityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Interface for managing {@link cz.sportiq.domain.AthleteActivity}.
 */
public interface AthleteActivityService {

    /**
     * Save a athleteActivity.
     *
     * @param athleteActivityDTO the entity to save.
     * @return the persisted entity.
     */
    AthleteActivityDTO save(AthleteActivityDTO athleteActivityDTO);

    /**
     * Get all the athleteActivities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AthleteActivityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" athleteActivity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AthleteActivityDTO> findOne(Long id);

    /**
     * Delete the "id" athleteActivity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the athleteActivity corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AthleteActivityDTO> search(String query, Pageable pageable);

    AthleteActivityDTO findByActivityIdAndAthleteWorkoutId(Long activityId, Long athleteWorkoutId);

    AthleteActivityDTO saveComplete(AthleteActivityDTO athleteActivityDTO);

    @Transactional(readOnly = true)
    AthleteActivity findOrCreateAthleteActivity(Activity activity, AthleteWorkout athleteWorkout);
}
