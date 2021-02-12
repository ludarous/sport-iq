package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.service.SportService;
import com.sportiq.sportiq.web.rest.errors.BadRequestAlertException;
import com.sportiq.sportiq.service.dto.SportDTO;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sportiq.sportiq.domain.Sport}.
 */
@RestController
@RequestMapping("/api")
public class SportResource {

    private final Logger log = LoggerFactory.getLogger(SportResource.class);

    private static final String ENTITY_NAME = "sport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SportService sportService;

    public SportResource(SportService sportService) {
        this.sportService = sportService;
    }

    /**
     * {@code POST  /sports} : Create a new sport.
     *
     * @param sportDTO the sportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sportDTO, or with status {@code 400 (Bad Request)} if the sport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sports")
    public ResponseEntity<SportDTO> createSport(@Valid @RequestBody SportDTO sportDTO) throws URISyntaxException {
        log.debug("REST request to save Sport : {}", sportDTO);
        if (sportDTO.getId() != null) {
            throw new BadRequestAlertException("A new sport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SportDTO result = sportService.save(sportDTO);
        return ResponseEntity.created(new URI("/api/sports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sports} : Updates an existing sport.
     *
     * @param sportDTO the sportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sportDTO,
     * or with status {@code 400 (Bad Request)} if the sportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sports")
    public ResponseEntity<SportDTO> updateSport(@Valid @RequestBody SportDTO sportDTO) throws URISyntaxException {
        log.debug("REST request to update Sport : {}", sportDTO);
        if (sportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SportDTO result = sportService.save(sportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sports} : get all the sports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sports in body.
     */
    @GetMapping("/sports")
    public ResponseEntity<List<SportDTO>> getAllSports(Pageable pageable) {
        log.debug("REST request to get a page of Sports");
        Page<SportDTO> page = sportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sports/:id} : get the "id" sport.
     *
     * @param id the id of the sportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sports/{id}")
    public ResponseEntity<SportDTO> getSport(@PathVariable Long id) {
        log.debug("REST request to get Sport : {}", id);
        Optional<SportDTO> sportDTO = sportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sportDTO);
    }

    /**
     * {@code DELETE  /sports/:id} : delete the "id" sport.
     *
     * @param id the id of the sportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sports/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable Long id) {
        log.debug("REST request to delete Sport : {}", id);
        sportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
