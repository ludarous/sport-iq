package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.domain.Organisation;
import com.sportiq.sportiq.repository.OrganisationRepository;
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
 * REST controller for managing {@link com.sportiq.sportiq.domain.Organisation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrganisationResource {

    private final Logger log = LoggerFactory.getLogger(OrganisationResource.class);

    private static final String ENTITY_NAME = "organisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganisationRepository organisationRepository;

    public OrganisationResource(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    /**
     * {@code POST  /organisations} : Create a new organisation.
     *
     * @param organisation the organisation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organisation, or with status {@code 400 (Bad Request)} if the organisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organisations")
    public ResponseEntity<Organisation> createOrganisation(@RequestBody Organisation organisation) throws URISyntaxException {
        log.debug("REST request to save Organisation : {}", organisation);
        if (organisation.getId() != null) {
            throw new BadRequestAlertException("A new organisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Organisation result = organisationRepository.save(organisation);
        return ResponseEntity.created(new URI("/api/organisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organisations} : Updates an existing organisation.
     *
     * @param organisation the organisation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organisation,
     * or with status {@code 400 (Bad Request)} if the organisation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organisation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organisations")
    public ResponseEntity<Organisation> updateOrganisation(@RequestBody Organisation organisation) throws URISyntaxException {
        log.debug("REST request to update Organisation : {}", organisation);
        if (organisation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Organisation result = organisationRepository.save(organisation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organisation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /organisations} : get all the organisations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organisations in body.
     */
    @GetMapping("/organisations")
    public ResponseEntity<List<Organisation>> getAllOrganisations(Pageable pageable) {
        log.debug("REST request to get a page of Organisations");
        Page<Organisation> page = organisationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organisations/:id} : get the "id" organisation.
     *
     * @param id the id of the organisation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organisation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organisations/{id}")
    public ResponseEntity<Organisation> getOrganisation(@PathVariable Long id) {
        log.debug("REST request to get Organisation : {}", id);
        Optional<Organisation> organisation = organisationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(organisation);
    }

    /**
     * {@code DELETE  /organisations/:id} : delete the "id" organisation.
     *
     * @param id the id of the organisation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organisations/{id}")
    public ResponseEntity<Void> deleteOrganisation(@PathVariable Long id) {
        log.debug("REST request to delete Organisation : {}", id);
        organisationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
