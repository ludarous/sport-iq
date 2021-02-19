package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.domain.Sport;
import com.sportiq.sportiq.repository.SportRepository;
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
@Transactional
public class SportResource {

    private final Logger log = LoggerFactory.getLogger(SportResource.class);

    private static final String ENTITY_NAME = "sport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SportRepository sportRepository;

    public SportResource(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    /**
     * {@code POST  /sports} : Create a new sport.
     *
     * @param sport the sport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sport, or with status {@code 400 (Bad Request)} if the sport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sports")
    public ResponseEntity<Sport> createSport(@Valid @RequestBody Sport sport) throws URISyntaxException {
        log.debug("REST request to save Sport : {}", sport);
        if (sport.getId() != null) {
            throw new BadRequestAlertException("A new sport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sport result = sportRepository.save(sport);
        return ResponseEntity.created(new URI("/api/sports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sports} : Updates an existing sport.
     *
     * @param sport the sport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sport,
     * or with status {@code 400 (Bad Request)} if the sport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sports")
    public ResponseEntity<Sport> updateSport(@Valid @RequestBody Sport sport) throws URISyntaxException {
        log.debug("REST request to update Sport : {}", sport);
        if (sport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sport result = sportRepository.save(sport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sports} : get all the sports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sports in body.
     */
    @GetMapping("/sports")
    public ResponseEntity<List<Sport>> getAllSports(Pageable pageable) {
        log.debug("REST request to get a page of Sports");
        Page<Sport> page = sportRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sports/:id} : get the "id" sport.
     *
     * @param id the id of the sport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sports/{id}")
    public ResponseEntity<Sport> getSport(@PathVariable Long id) {
        log.debug("REST request to get Sport : {}", id);
        Optional<Sport> sport = sportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sport);
    }

    /**
     * {@code DELETE  /sports/:id} : delete the "id" sport.
     *
     * @param id the id of the sport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sports/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable Long id) {
        log.debug("REST request to delete Sport : {}", id);
        sportRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
