package cz.sportiq.web.rest;

import com.codahale.metrics.annotation.Timed;
import cz.sportiq.service.AthleteService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.web.rest.util.HeaderUtil;
import cz.sportiq.web.rest.util.PaginationUtil;
import cz.sportiq.service.dto.AthleteDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Athlete.
 */
@RestController
@RequestMapping("/api")
public class AthleteResource {

    private final Logger log = LoggerFactory.getLogger(AthleteResource.class);

    private static final String ENTITY_NAME = "athlete";

    private final AthleteService athleteService;

    public AthleteResource(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    /**
     * POST  /athletes : Create a new athlete.
     *
     * @param athleteDTO the athleteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new athleteDTO, or with status 400 (Bad Request) if the athlete has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/athletes")
    @Timed
    public ResponseEntity<AthleteDTO> createAthlete(@Valid @RequestBody AthleteDTO athleteDTO) throws URISyntaxException {
        log.debug("REST request to save Athlete : {}", athleteDTO);
        if (athleteDTO.getId() != null) {
            throw new BadRequestAlertException("A new athlete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AthleteDTO result = athleteService.save(athleteDTO);
        return ResponseEntity.created(new URI("/api/athletes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /athletes : Updates an existing athlete.
     *
     * @param athleteDTO the athleteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated athleteDTO,
     * or with status 400 (Bad Request) if the athleteDTO is not valid,
     * or with status 500 (Internal Server Error) if the athleteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/athletes")
    @Timed
    public ResponseEntity<AthleteDTO> updateAthlete(@Valid @RequestBody AthleteDTO athleteDTO) throws URISyntaxException {
        log.debug("REST request to update Athlete : {}", athleteDTO);
        if (athleteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AthleteDTO result = athleteService.save(athleteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, athleteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /athletes : get all the athletes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of athletes in body
     */
    @GetMapping("/athletes")
    @Timed
    public ResponseEntity<List<AthleteDTO>> getAllAthletes(Pageable pageable) {
        log.debug("REST request to get a page of Athletes");
        Page<AthleteDTO> page = athleteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/athletes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /athletes/:id : get the "id" athlete.
     *
     * @param id the id of the athleteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the athleteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/athletes/{id}")
    @Timed
    public ResponseEntity<AthleteDTO> getAthlete(@PathVariable Long id) {
        log.debug("REST request to get Athlete : {}", id);
        Optional<AthleteDTO> athleteDTO = athleteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(athleteDTO);
    }

    /**
     * DELETE  /athletes/:id : delete the "id" athlete.
     *
     * @param id the id of the athleteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/athletes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        log.debug("REST request to delete Athlete : {}", id);
        athleteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/athletes?query=:query : search for the athlete corresponding
     * to the query.
     *
     * @param query the query of the athlete search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/athletes")
    @Timed
    public ResponseEntity<List<AthleteDTO>> searchAthletes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Athletes for query {}", query);
        Page<AthleteDTO> page = athleteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/athletes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
