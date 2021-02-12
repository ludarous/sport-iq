package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.service.AgreementService;
import com.sportiq.sportiq.web.rest.errors.BadRequestAlertException;
import com.sportiq.sportiq.service.dto.AgreementDTO;

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
 * REST controller for managing {@link com.sportiq.sportiq.domain.Agreement}.
 */
@RestController
@RequestMapping("/api")
public class AgreementResource {

    private final Logger log = LoggerFactory.getLogger(AgreementResource.class);

    private static final String ENTITY_NAME = "agreement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgreementService agreementService;

    public AgreementResource(AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    /**
     * {@code POST  /agreements} : Create a new agreement.
     *
     * @param agreementDTO the agreementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agreementDTO, or with status {@code 400 (Bad Request)} if the agreement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agreements")
    public ResponseEntity<AgreementDTO> createAgreement(@RequestBody AgreementDTO agreementDTO) throws URISyntaxException {
        log.debug("REST request to save Agreement : {}", agreementDTO);
        if (agreementDTO.getId() != null) {
            throw new BadRequestAlertException("A new agreement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgreementDTO result = agreementService.save(agreementDTO);
        return ResponseEntity.created(new URI("/api/agreements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agreements} : Updates an existing agreement.
     *
     * @param agreementDTO the agreementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agreementDTO,
     * or with status {@code 400 (Bad Request)} if the agreementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agreementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agreements")
    public ResponseEntity<AgreementDTO> updateAgreement(@RequestBody AgreementDTO agreementDTO) throws URISyntaxException {
        log.debug("REST request to update Agreement : {}", agreementDTO);
        if (agreementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgreementDTO result = agreementService.save(agreementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, agreementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agreements} : get all the agreements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agreements in body.
     */
    @GetMapping("/agreements")
    public ResponseEntity<List<AgreementDTO>> getAllAgreements(Pageable pageable) {
        log.debug("REST request to get a page of Agreements");
        Page<AgreementDTO> page = agreementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /agreements/:id} : get the "id" agreement.
     *
     * @param id the id of the agreementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agreementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agreements/{id}")
    public ResponseEntity<AgreementDTO> getAgreement(@PathVariable Long id) {
        log.debug("REST request to get Agreement : {}", id);
        Optional<AgreementDTO> agreementDTO = agreementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agreementDTO);
    }

    /**
     * {@code DELETE  /agreements/:id} : delete the "id" agreement.
     *
     * @param id the id of the agreementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agreements/{id}")
    public ResponseEntity<Void> deleteAgreement(@PathVariable Long id) {
        log.debug("REST request to delete Agreement : {}", id);
        agreementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
