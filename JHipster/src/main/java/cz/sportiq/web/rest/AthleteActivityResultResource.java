package cz.sportiq.web.rest;

import com.codahale.metrics.annotation.Timed;
import cz.sportiq.service.AthleteActivityResultService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.web.rest.util.HeaderUtil;
import cz.sportiq.web.rest.util.PaginationUtil;
import cz.sportiq.service.dto.AthleteActivityResultDTO;
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
 * REST controller for managing AthleteActivityResult.
 */
@RestController
@RequestMapping("/api")
public class AthleteActivityResultResource {

    private final Logger log = LoggerFactory.getLogger(AthleteActivityResultResource.class);

    private static final String ENTITY_NAME = "athleteActivityResult";

    private final AthleteActivityResultService athleteActivityResultService;

    public AthleteActivityResultResource(AthleteActivityResultService athleteActivityResultService) {
        this.athleteActivityResultService = athleteActivityResultService;
    }

    /**
     * POST  /athlete-activity-results : Create a new athleteActivityResult.
     *
     * @param athleteActivityResultDTO the athleteActivityResultDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new athleteActivityResultDTO, or with status 400 (Bad Request) if the athleteActivityResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/athlete-activity-results")
    @Timed
    public ResponseEntity<AthleteActivityResultDTO> createAthleteActivityResult(@Valid @RequestBody AthleteActivityResultDTO athleteActivityResultDTO) throws URISyntaxException {
        log.debug("REST request to save AthleteActivityResult : {}", athleteActivityResultDTO);
        if (athleteActivityResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new athleteActivityResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AthleteActivityResultDTO result = athleteActivityResultService.save(athleteActivityResultDTO);
        return ResponseEntity.created(new URI("/api/athlete-activity-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /athlete-activity-results : Updates an existing athleteActivityResult.
     *
     * @param athleteActivityResultDTO the athleteActivityResultDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated athleteActivityResultDTO,
     * or with status 400 (Bad Request) if the athleteActivityResultDTO is not valid,
     * or with status 500 (Internal Server Error) if the athleteActivityResultDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/athlete-activity-results")
    @Timed
    public ResponseEntity<AthleteActivityResultDTO> updateAthleteActivityResult(@Valid @RequestBody AthleteActivityResultDTO athleteActivityResultDTO) throws URISyntaxException {
        log.debug("REST request to update AthleteActivityResult : {}", athleteActivityResultDTO);
        if (athleteActivityResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AthleteActivityResultDTO result = athleteActivityResultService.save(athleteActivityResultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, athleteActivityResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /athlete-activity-results : get all the athleteActivityResults.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of athleteActivityResults in body
     */
    @GetMapping("/athlete-activity-results")
    @Timed
    public ResponseEntity<List<AthleteActivityResultDTO>> getAllAthleteActivityResults(Pageable pageable) {
        log.debug("REST request to get a page of AthleteActivityResults");
        Page<AthleteActivityResultDTO> page = athleteActivityResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/athlete-activity-results");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /athlete-activity-results/:id : get the "id" athleteActivityResult.
     *
     * @param id the id of the athleteActivityResultDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the athleteActivityResultDTO, or with status 404 (Not Found)
     */
    @GetMapping("/athlete-activity-results/{id}")
    @Timed
    public ResponseEntity<AthleteActivityResultDTO> getAthleteActivityResult(@PathVariable Long id) {
        log.debug("REST request to get AthleteActivityResult : {}", id);
        Optional<AthleteActivityResultDTO> athleteActivityResultDTO = athleteActivityResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(athleteActivityResultDTO);
    }

    /**
     * DELETE  /athlete-activity-results/:id : delete the "id" athleteActivityResult.
     *
     * @param id the id of the athleteActivityResultDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/athlete-activity-results/{id}")
    @Timed
    public ResponseEntity<Void> deleteAthleteActivityResult(@PathVariable Long id) {
        log.debug("REST request to delete AthleteActivityResult : {}", id);
        athleteActivityResultService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/athlete-activity-results?query=:query : search for the athleteActivityResult corresponding
     * to the query.
     *
     * @param query the query of the athleteActivityResult search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/athlete-activity-results")
    @Timed
    public ResponseEntity<List<AthleteActivityResultDTO>> searchAthleteActivityResults(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AthleteActivityResults for query {}", query);
        Page<AthleteActivityResultDTO> page = athleteActivityResultService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/athlete-activity-results");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
