package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.service.LegalRepresentativeService;
import com.sportiq.sportiq.web.rest.errors.BadRequestAlertException;
import com.sportiq.sportiq.service.dto.LegalRepresentativeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sportiq.sportiq.domain.LegalRepresentative}.
 */
@RestController
@RequestMapping("/api")
public class LegalRepresentativeResource {

    private final Logger log = LoggerFactory.getLogger(LegalRepresentativeResource.class);

    private static final String ENTITY_NAME = "legalRepresentative";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LegalRepresentativeService legalRepresentativeService;

    public LegalRepresentativeResource(LegalRepresentativeService legalRepresentativeService) {
        this.legalRepresentativeService = legalRepresentativeService;
    }

    /**
     * {@code POST  /legal-representatives} : Create a new legalRepresentative.
     *
     * @param legalRepresentativeDTO the legalRepresentativeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new legalRepresentativeDTO, or with status {@code 400 (Bad Request)} if the legalRepresentative has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/legal-representatives")
    public ResponseEntity<LegalRepresentativeDTO> createLegalRepresentative(@RequestBody LegalRepresentativeDTO legalRepresentativeDTO) throws URISyntaxException {
        log.debug("REST request to save LegalRepresentative : {}", legalRepresentativeDTO);
        if (legalRepresentativeDTO.getId() != null) {
            throw new BadRequestAlertException("A new legalRepresentative cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LegalRepresentativeDTO result = legalRepresentativeService.save(legalRepresentativeDTO);
        return ResponseEntity.created(new URI("/api/legal-representatives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /legal-representatives} : Updates an existing legalRepresentative.
     *
     * @param legalRepresentativeDTO the legalRepresentativeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated legalRepresentativeDTO,
     * or with status {@code 400 (Bad Request)} if the legalRepresentativeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the legalRepresentativeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/legal-representatives")
    public ResponseEntity<LegalRepresentativeDTO> updateLegalRepresentative(@RequestBody LegalRepresentativeDTO legalRepresentativeDTO) throws URISyntaxException {
        log.debug("REST request to update LegalRepresentative : {}", legalRepresentativeDTO);
        if (legalRepresentativeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LegalRepresentativeDTO result = legalRepresentativeService.save(legalRepresentativeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, legalRepresentativeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /legal-representatives} : get all the legalRepresentatives.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of legalRepresentatives in body.
     */
    @GetMapping("/legal-representatives")
    public ResponseEntity<List<LegalRepresentativeDTO>> getAllLegalRepresentatives(Pageable pageable) {
        log.debug("REST request to get a page of LegalRepresentatives");
        Page<LegalRepresentativeDTO> page = legalRepresentativeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /legal-representatives/:id} : get the "id" legalRepresentative.
     *
     * @param id the id of the legalRepresentativeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the legalRepresentativeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/legal-representatives/{id}")
    public ResponseEntity<LegalRepresentativeDTO> getLegalRepresentative(@PathVariable Long id) {
        log.debug("REST request to get LegalRepresentative : {}", id);
        Optional<LegalRepresentativeDTO> legalRepresentativeDTO = legalRepresentativeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(legalRepresentativeDTO);
    }

    /**
     * {@code DELETE  /legal-representatives/:id} : delete the "id" legalRepresentative.
     *
     * @param id the id of the legalRepresentativeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/legal-representatives/{id}")
    public ResponseEntity<Void> deleteLegalRepresentative(@PathVariable Long id) {
        log.debug("REST request to delete LegalRepresentative : {}", id);
        legalRepresentativeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
