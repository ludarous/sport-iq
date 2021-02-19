package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.domain.LegalRepresentative;
import com.sportiq.sportiq.repository.LegalRepresentativeRepository;
import com.sportiq.sportiq.web.rest.errors.BadRequestAlertException;

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
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class LegalRepresentativeResource {

    private final Logger log = LoggerFactory.getLogger(LegalRepresentativeResource.class);

    private static final String ENTITY_NAME = "legalRepresentative";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LegalRepresentativeRepository legalRepresentativeRepository;

    public LegalRepresentativeResource(LegalRepresentativeRepository legalRepresentativeRepository) {
        this.legalRepresentativeRepository = legalRepresentativeRepository;
    }

    /**
     * {@code POST  /legal-representatives} : Create a new legalRepresentative.
     *
     * @param legalRepresentative the legalRepresentative to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new legalRepresentative, or with status {@code 400 (Bad Request)} if the legalRepresentative has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/legal-representatives")
    public ResponseEntity<LegalRepresentative> createLegalRepresentative(@RequestBody LegalRepresentative legalRepresentative) throws URISyntaxException {
        log.debug("REST request to save LegalRepresentative : {}", legalRepresentative);
        if (legalRepresentative.getId() != null) {
            throw new BadRequestAlertException("A new legalRepresentative cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LegalRepresentative result = legalRepresentativeRepository.save(legalRepresentative);
        return ResponseEntity.created(new URI("/api/legal-representatives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /legal-representatives} : Updates an existing legalRepresentative.
     *
     * @param legalRepresentative the legalRepresentative to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated legalRepresentative,
     * or with status {@code 400 (Bad Request)} if the legalRepresentative is not valid,
     * or with status {@code 500 (Internal Server Error)} if the legalRepresentative couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/legal-representatives")
    public ResponseEntity<LegalRepresentative> updateLegalRepresentative(@RequestBody LegalRepresentative legalRepresentative) throws URISyntaxException {
        log.debug("REST request to update LegalRepresentative : {}", legalRepresentative);
        if (legalRepresentative.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LegalRepresentative result = legalRepresentativeRepository.save(legalRepresentative);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, legalRepresentative.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /legal-representatives} : get all the legalRepresentatives.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of legalRepresentatives in body.
     */
    @GetMapping("/legal-representatives")
    public ResponseEntity<List<LegalRepresentative>> getAllLegalRepresentatives(Pageable pageable) {
        log.debug("REST request to get a page of LegalRepresentatives");
        Page<LegalRepresentative> page = legalRepresentativeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /legal-representatives/:id} : get the "id" legalRepresentative.
     *
     * @param id the id of the legalRepresentative to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the legalRepresentative, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/legal-representatives/{id}")
    public ResponseEntity<LegalRepresentative> getLegalRepresentative(@PathVariable Long id) {
        log.debug("REST request to get LegalRepresentative : {}", id);
        Optional<LegalRepresentative> legalRepresentative = legalRepresentativeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(legalRepresentative);
    }

    /**
     * {@code DELETE  /legal-representatives/:id} : delete the "id" legalRepresentative.
     *
     * @param id the id of the legalRepresentative to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/legal-representatives/{id}")
    public ResponseEntity<Void> deleteLegalRepresentative(@PathVariable Long id) {
        log.debug("REST request to delete LegalRepresentative : {}", id);
        legalRepresentativeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
