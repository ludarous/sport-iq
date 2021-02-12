package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.service.BodyCharacteristicsService;
import com.sportiq.sportiq.web.rest.errors.BadRequestAlertException;
import com.sportiq.sportiq.service.dto.BodyCharacteristicsDTO;

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
 * REST controller for managing {@link com.sportiq.sportiq.domain.BodyCharacteristics}.
 */
@RestController
@RequestMapping("/api")
public class BodyCharacteristicsResource {

    private final Logger log = LoggerFactory.getLogger(BodyCharacteristicsResource.class);

    private static final String ENTITY_NAME = "bodyCharacteristics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BodyCharacteristicsService bodyCharacteristicsService;

    public BodyCharacteristicsResource(BodyCharacteristicsService bodyCharacteristicsService) {
        this.bodyCharacteristicsService = bodyCharacteristicsService;
    }

    /**
     * {@code POST  /body-characteristics} : Create a new bodyCharacteristics.
     *
     * @param bodyCharacteristicsDTO the bodyCharacteristicsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bodyCharacteristicsDTO, or with status {@code 400 (Bad Request)} if the bodyCharacteristics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/body-characteristics")
    public ResponseEntity<BodyCharacteristicsDTO> createBodyCharacteristics(@RequestBody BodyCharacteristicsDTO bodyCharacteristicsDTO) throws URISyntaxException {
        log.debug("REST request to save BodyCharacteristics : {}", bodyCharacteristicsDTO);
        if (bodyCharacteristicsDTO.getId() != null) {
            throw new BadRequestAlertException("A new bodyCharacteristics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BodyCharacteristicsDTO result = bodyCharacteristicsService.save(bodyCharacteristicsDTO);
        return ResponseEntity.created(new URI("/api/body-characteristics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /body-characteristics} : Updates an existing bodyCharacteristics.
     *
     * @param bodyCharacteristicsDTO the bodyCharacteristicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bodyCharacteristicsDTO,
     * or with status {@code 400 (Bad Request)} if the bodyCharacteristicsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bodyCharacteristicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/body-characteristics")
    public ResponseEntity<BodyCharacteristicsDTO> updateBodyCharacteristics(@RequestBody BodyCharacteristicsDTO bodyCharacteristicsDTO) throws URISyntaxException {
        log.debug("REST request to update BodyCharacteristics : {}", bodyCharacteristicsDTO);
        if (bodyCharacteristicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BodyCharacteristicsDTO result = bodyCharacteristicsService.save(bodyCharacteristicsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bodyCharacteristicsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /body-characteristics} : get all the bodyCharacteristics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bodyCharacteristics in body.
     */
    @GetMapping("/body-characteristics")
    public ResponseEntity<List<BodyCharacteristicsDTO>> getAllBodyCharacteristics(Pageable pageable) {
        log.debug("REST request to get a page of BodyCharacteristics");
        Page<BodyCharacteristicsDTO> page = bodyCharacteristicsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /body-characteristics/:id} : get the "id" bodyCharacteristics.
     *
     * @param id the id of the bodyCharacteristicsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bodyCharacteristicsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/body-characteristics/{id}")
    public ResponseEntity<BodyCharacteristicsDTO> getBodyCharacteristics(@PathVariable Long id) {
        log.debug("REST request to get BodyCharacteristics : {}", id);
        Optional<BodyCharacteristicsDTO> bodyCharacteristicsDTO = bodyCharacteristicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bodyCharacteristicsDTO);
    }

    /**
     * {@code DELETE  /body-characteristics/:id} : delete the "id" bodyCharacteristics.
     *
     * @param id the id of the bodyCharacteristicsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/body-characteristics/{id}")
    public ResponseEntity<Void> deleteBodyCharacteristics(@PathVariable Long id) {
        log.debug("REST request to delete BodyCharacteristics : {}", id);
        bodyCharacteristicsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
