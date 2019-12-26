package cz.sportiq.service;

import cz.sportiq.service.dto.SportDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cz.sportiq.domain.Sport}.
 */
public interface SportService {

    /**
     * Save a sport.
     *
     * @param sportDTO the entity to save.
     * @return the persisted entity.
     */
    SportDTO save(SportDTO sportDTO);

    /**
     * Get all the sports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SportDTO> findAll(Pageable pageable);


    /**
     * Get the "id" sport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SportDTO> findOne(Long id);

    /**
     * Delete the "id" sport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the sport corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SportDTO> search(String query, Pageable pageable);
}
