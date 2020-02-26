package cz.sportiq.service;

import cz.sportiq.service.dto.AthleteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cz.sportiq.domain.Athlete}.
 */
public interface AthleteService {

    /**
     * Save a athlete.
     *
     * @param athleteDTO the entity to save.
     * @return the persisted entity.
     */
    AthleteDTO save(AthleteDTO athleteDTO);

    /**
     * Get all the athletes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AthleteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" athlete.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AthleteDTO> findOne(Long id);

    /**
     * Delete the "id" athlete.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
