package com.sportiq.sportiq.service;

import com.sportiq.sportiq.service.dto.LegalRepresentativeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sportiq.sportiq.domain.LegalRepresentative}.
 */
public interface LegalRepresentativeService {

    /**
     * Save a legalRepresentative.
     *
     * @param legalRepresentativeDTO the entity to save.
     * @return the persisted entity.
     */
    LegalRepresentativeDTO save(LegalRepresentativeDTO legalRepresentativeDTO);

    /**
     * Get all the legalRepresentatives.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LegalRepresentativeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" legalRepresentative.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LegalRepresentativeDTO> findOne(Long id);

    /**
     * Delete the "id" legalRepresentative.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
