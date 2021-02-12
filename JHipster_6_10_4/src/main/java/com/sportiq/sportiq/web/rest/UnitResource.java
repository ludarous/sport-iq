package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.service.UnitService;
import com.sportiq.sportiq.web.rest.errors.BadRequestAlertException;
import com.sportiq.sportiq.service.dto.UnitDTO;

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
 * REST controller for managing {@link com.sportiq.sportiq.domain.Unit}.
 */
@RestController
@RequestMapping("/api")
public class UnitResource {

    private final Logger log = LoggerFactory.getLogger(UnitResource.class);

    private static final String ENTITY_NAME = "unit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnitService unitService;

    public UnitResource(UnitService unitService) {
        this.unitService = unitService;
    }

    /**
     * {@code POST  /units} : Create a new unit.
     *
     * @param unitDTO the unitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unitDTO, or with status {@code 400 (Bad Request)} if the unit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/units")
    public ResponseEntity<UnitDTO> createUnit(@RequestBody UnitDTO unitDTO) throws URISyntaxException {
        log.debug("REST request to save Unit : {}", unitDTO);
        if (unitDTO.getId() != null) {
            throw new BadRequestAlertException("A new unit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnitDTO result = unitService.save(unitDTO);
        return ResponseEntity.created(new URI("/api/units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /units} : Updates an existing unit.
     *
     * @param unitDTO the unitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unitDTO,
     * or with status {@code 400 (Bad Request)} if the unitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/units")
    public ResponseEntity<UnitDTO> updateUnit(@RequestBody UnitDTO unitDTO) throws URISyntaxException {
        log.debug("REST request to update Unit : {}", unitDTO);
        if (unitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnitDTO result = unitService.save(unitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /units} : get all the units.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of units in body.
     */
    @GetMapping("/units")
    public ResponseEntity<List<UnitDTO>> getAllUnits(Pageable pageable) {
        log.debug("REST request to get a page of Units");
        Page<UnitDTO> page = unitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /units/:id} : get the "id" unit.
     *
     * @param id the id of the unitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/units/{id}")
    public ResponseEntity<UnitDTO> getUnit(@PathVariable Long id) {
        log.debug("REST request to get Unit : {}", id);
        Optional<UnitDTO> unitDTO = unitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unitDTO);
    }

    /**
     * {@code DELETE  /units/:id} : delete the "id" unit.
     *
     * @param id the id of the unitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/units/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        log.debug("REST request to delete Unit : {}", id);
        unitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
