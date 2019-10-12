package cz.sportiq.web.rest;

import com.codahale.metrics.annotation.Timed;
import cz.sportiq.service.AthleteActivityResultSplitService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.web.rest.util.HeaderUtil;
import cz.sportiq.web.rest.util.PaginationUtil;
import cz.sportiq.service.dto.AthleteActivityResultSplitDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AthleteActivityResultSplit.
 */
@RestController
@RequestMapping("/api")
public class AthleteActivityResultSplitResource {

    private final Logger log = LoggerFactory.getLogger(AthleteActivityResultSplitResource.class);

    private static final String ENTITY_NAME = "athleteActivityResultSplit";

    private final AthleteActivityResultSplitService athleteActivityResultSplitService;

    public AthleteActivityResultSplitResource(AthleteActivityResultSplitService athleteActivityResultSplitService) {
        this.athleteActivityResultSplitService = athleteActivityResultSplitService;
    }

    /**
     * POST  /athlete-activity-result-splits : Create a new athleteActivityResultSplit.
     *
     * @param athleteActivityResultSplitDTO the athleteActivityResultSplitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new athleteActivityResultSplitDTO, or with status 400 (Bad Request) if the athleteActivityResultSplit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/athlete-activity-result-splits")
    @Timed
    public ResponseEntity<AthleteActivityResultSplitDTO> createAthleteActivityResultSplit(@RequestBody AthleteActivityResultSplitDTO athleteActivityResultSplitDTO) throws URISyntaxException {
        log.debug("REST request to save AthleteActivityResultSplit : {}", athleteActivityResultSplitDTO);
        if (athleteActivityResultSplitDTO.getId() != null) {
            throw new BadRequestAlertException("A new athleteActivityResultSplit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AthleteActivityResultSplitDTO result = athleteActivityResultSplitService.save(athleteActivityResultSplitDTO);
        return ResponseEntity.created(new URI("/api/athlete-activity-result-splits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /athlete-activity-result-splits : Updates an existing athleteActivityResultSplit.
     *
     * @param athleteActivityResultSplitDTO the athleteActivityResultSplitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated athleteActivityResultSplitDTO,
     * or with status 400 (Bad Request) if the athleteActivityResultSplitDTO is not valid,
     * or with status 500 (Internal Server Error) if the athleteActivityResultSplitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/athlete-activity-result-splits")
    @Timed
    public ResponseEntity<AthleteActivityResultSplitDTO> updateAthleteActivityResultSplit(@RequestBody AthleteActivityResultSplitDTO athleteActivityResultSplitDTO) throws URISyntaxException {
        log.debug("REST request to update AthleteActivityResultSplit : {}", athleteActivityResultSplitDTO);
        if (athleteActivityResultSplitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AthleteActivityResultSplitDTO result = athleteActivityResultSplitService.save(athleteActivityResultSplitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, athleteActivityResultSplitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /athlete-activity-result-splits : get all the athleteActivityResultSplits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of athleteActivityResultSplits in body
     */
    @GetMapping("/athlete-activity-result-splits")
    @Timed
    public ResponseEntity<List<AthleteActivityResultSplitDTO>> getAllAthleteActivityResultSplits(Pageable pageable) {
        log.debug("REST request to get a page of AthleteActivityResultSplits");
        Page<AthleteActivityResultSplitDTO> page = athleteActivityResultSplitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/athlete-activity-result-splits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /athlete-activity-result-splits/:id : get the "id" athleteActivityResultSplit.
     *
     * @param id the id of the athleteActivityResultSplitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the athleteActivityResultSplitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/athlete-activity-result-splits/{id}")
    @Timed
    public ResponseEntity<AthleteActivityResultSplitDTO> getAthleteActivityResultSplit(@PathVariable Long id) {
        log.debug("REST request to get AthleteActivityResultSplit : {}", id);
        Optional<AthleteActivityResultSplitDTO> athleteActivityResultSplitDTO = athleteActivityResultSplitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(athleteActivityResultSplitDTO);
    }

    /**
     * DELETE  /athlete-activity-result-splits/:id : delete the "id" athleteActivityResultSplit.
     *
     * @param id the id of the athleteActivityResultSplitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/athlete-activity-result-splits/{id}")
    @Timed
    public ResponseEntity<Void> deleteAthleteActivityResultSplit(@PathVariable Long id) {
        log.debug("REST request to delete AthleteActivityResultSplit : {}", id);
        athleteActivityResultSplitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/athlete-activity-result-splits?query=:query : search for the athleteActivityResultSplit corresponding
     * to the query.
     *
     * @param query the query of the athleteActivityResultSplit search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/athlete-activity-result-splits")
    @Timed
    public ResponseEntity<List<AthleteActivityResultSplitDTO>> searchAthleteActivityResultSplits(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AthleteActivityResultSplits for query {}", query);
        Page<AthleteActivityResultSplitDTO> page = athleteActivityResultSplitService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/athlete-activity-result-splits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
