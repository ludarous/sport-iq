package cz.sportiq.service;

import cz.sportiq.service.dto.AthleteActivityResultSplitDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AthleteActivityResultSplit.
 */
public interface AthleteActivityResultSplitService {

    /**
     * Save a athleteActivityResultSplit.
     *
     * @param athleteActivityResultSplitDTO the entity to save
     * @return the persisted entity
     */
    AthleteActivityResultSplitDTO save(AthleteActivityResultSplitDTO athleteActivityResultSplitDTO);

    /**
     * Get all the athleteActivityResultSplits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AthleteActivityResultSplitDTO> findAll(Pageable pageable);


    /**
     * Get the "id" athleteActivityResultSplit.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AthleteActivityResultSplitDTO> findOne(Long id);

    /**
     * Delete the "id" athleteActivityResultSplit.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the athleteActivityResultSplit corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AthleteActivityResultSplitDTO> search(String query, Pageable pageable);
}