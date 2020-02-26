package cz.sportiq.service;

import cz.sportiq.domain.ActivityResult;
import cz.sportiq.domain.AthleteActivity;
import cz.sportiq.domain.AthleteActivityResult;
import cz.sportiq.service.dto.AthleteActivityResultDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Interface for managing {@link cz.sportiq.domain.AthleteActivityResult}.
 */
public interface AthleteActivityResultService {

    /**
     * Save a athleteActivityResult.
     *
     * @param athleteActivityResultDTO the entity to save.
     * @return the persisted entity.
     */
    AthleteActivityResultDTO save(AthleteActivityResultDTO athleteActivityResultDTO);

    /**
     * Get all the athleteActivityResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AthleteActivityResultDTO> findAll(Pageable pageable);


    /**
     * Get the "id" athleteActivityResult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AthleteActivityResultDTO> findOne(Long id);

    /**
     * Delete the "id" athleteActivityResult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    @Transactional(readOnly = true)
    AthleteActivityResult findOrCreateAthleteActivityResult(ActivityResult activityResult, AthleteActivity athleteActivity);
}
