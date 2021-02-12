package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.service.OrganisationMembershipService;
import com.sportiq.sportiq.web.rest.errors.BadRequestAlertException;
import com.sportiq.sportiq.service.dto.OrganisationMembershipDTO;

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
 * REST controller for managing {@link com.sportiq.sportiq.domain.OrganisationMembership}.
 */
@RestController
@RequestMapping("/api")
public class OrganisationMembershipResource {

    private final Logger log = LoggerFactory.getLogger(OrganisationMembershipResource.class);

    private static final String ENTITY_NAME = "organisationMembership";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganisationMembershipService organisationMembershipService;

    public OrganisationMembershipResource(OrganisationMembershipService organisationMembershipService) {
        this.organisationMembershipService = organisationMembershipService;
    }

    /**
     * {@code POST  /organisation-memberships} : Create a new organisationMembership.
     *
     * @param organisationMembershipDTO the organisationMembershipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organisationMembershipDTO, or with status {@code 400 (Bad Request)} if the organisationMembership has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organisation-memberships")
    public ResponseEntity<OrganisationMembershipDTO> createOrganisationMembership(@RequestBody OrganisationMembershipDTO organisationMembershipDTO) throws URISyntaxException {
        log.debug("REST request to save OrganisationMembership : {}", organisationMembershipDTO);
        if (organisationMembershipDTO.getId() != null) {
            throw new BadRequestAlertException("A new organisationMembership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganisationMembershipDTO result = organisationMembershipService.save(organisationMembershipDTO);
        return ResponseEntity.created(new URI("/api/organisation-memberships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organisation-memberships} : Updates an existing organisationMembership.
     *
     * @param organisationMembershipDTO the organisationMembershipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organisationMembershipDTO,
     * or with status {@code 400 (Bad Request)} if the organisationMembershipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organisationMembershipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organisation-memberships")
    public ResponseEntity<OrganisationMembershipDTO> updateOrganisationMembership(@RequestBody OrganisationMembershipDTO organisationMembershipDTO) throws URISyntaxException {
        log.debug("REST request to update OrganisationMembership : {}", organisationMembershipDTO);
        if (organisationMembershipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrganisationMembershipDTO result = organisationMembershipService.save(organisationMembershipDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organisationMembershipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /organisation-memberships} : get all the organisationMemberships.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organisationMemberships in body.
     */
    @GetMapping("/organisation-memberships")
    public ResponseEntity<List<OrganisationMembershipDTO>> getAllOrganisationMemberships(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of OrganisationMemberships");
        Page<OrganisationMembershipDTO> page;
        if (eagerload) {
            page = organisationMembershipService.findAllWithEagerRelationships(pageable);
        } else {
            page = organisationMembershipService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organisation-memberships/:id} : get the "id" organisationMembership.
     *
     * @param id the id of the organisationMembershipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organisationMembershipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organisation-memberships/{id}")
    public ResponseEntity<OrganisationMembershipDTO> getOrganisationMembership(@PathVariable Long id) {
        log.debug("REST request to get OrganisationMembership : {}", id);
        Optional<OrganisationMembershipDTO> organisationMembershipDTO = organisationMembershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organisationMembershipDTO);
    }

    /**
     * {@code DELETE  /organisation-memberships/:id} : delete the "id" organisationMembership.
     *
     * @param id the id of the organisationMembershipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organisation-memberships/{id}")
    public ResponseEntity<Void> deleteOrganisationMembership(@PathVariable Long id) {
        log.debug("REST request to delete OrganisationMembership : {}", id);
        organisationMembershipService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
