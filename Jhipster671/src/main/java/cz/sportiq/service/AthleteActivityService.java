package cz.sportiq.service;

import cz.sportiq.service.dto.AthleteActivityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}
