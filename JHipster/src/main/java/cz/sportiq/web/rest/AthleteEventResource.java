package cz.sportiq.web.rest;

import com.codahale.metrics.annotation.Timed;
import cz.sportiq.service.AthleteEventService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.web.rest.util.HeaderUtil;
import cz.sportiq.web.rest.util.PaginationUtil;
import cz.sportiq.service.dto.AthleteEventDTO;
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
 * REST controller for managing AthleteEvent.
 */
@RestController
@RequestMapping("/api")
public class AthleteEventResource {

    private final Logger log = LoggerFactory.getLogger(AthleteEventResource.class);

    private static final String ENTITY_NAME = "athleteEvent";

    private final AthleteEventService athleteEventService;

    public AthleteEventResource(AthleteEventService athleteEventService) {
        this.athleteEventService = athleteEventService;
    }

    /**
     * POST  /athlete-events : Create a new athleteEvent.
     *
     * @param athleteEventDTO the athleteEventDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new athleteEventDTO, or with status 400 (Bad Request) if the athleteEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/athlete-events")
    @Timed
    public ResponseEntity<AthleteEventDTO> createAthleteEvent(@Valid @RequestBody AthleteEventDTO athleteEventDTO) throws URISyntaxException {
        log.debug("REST request to save AthleteEvent : {}", athleteEventDTO);
        if (athleteEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new athleteEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AthleteEventDTO result = athleteEventService.save(athleteEventDTO);
        return ResponseEntity.created(new URI("/api/athlete-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /athlete-events : Updates an existing athleteEvent.
     *
     * @param athleteEventDTO the athleteEventDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated athleteEventDTO,
     * or with status 400 (Bad Request) if the athleteEventDTO is not valid,
     * or with status 500 (Internal Server Error) if the athleteEventDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/athlete-events")
    @Timed
    public ResponseEntity<AthleteEventDTO> updateAthleteEvent(@Valid @RequestBody AthleteEventDTO athleteEventDTO) throws URISyntaxException {
        log.debug("REST request to update AthleteEvent : {}", athleteEventDTO);
        if (athleteEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AthleteEventDTO result = athleteEventService.save(athleteEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, athleteEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /athlete-events : get all the athleteEvents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of athleteEvents in body
     */
    @GetMapping("/athlete-events")
    @Timed
    public ResponseEntity<List<AthleteEventDTO>> getAllAthleteEvents(Pageable pageable) {
        log.debug("REST request to get a page of AthleteEvents");
        Page<AthleteEventDTO> page = athleteEventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/athlete-events");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /athlete-events/:id : get the "id" athleteEvent.
     *
     * @param id the id of the athleteEventDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the athleteEventDTO, or with status 404 (Not Found)
     */
    @GetMapping("/athlete-events/{id}")
    @Timed
    public ResponseEntity<AthleteEventDTO> getAthleteEvent(@PathVariable Long id) {
        log.debug("REST request to get AthleteEvent : {}", id);
        Optional<AthleteEventDTO> athleteEventDTO = athleteEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(athleteEventDTO);
    }

    /**
     * DELETE  /athlete-events/:id : delete the "id" athleteEvent.
     *
     * @param id the id of the athleteEventDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/athlete-events/{id}")
    @Timed
    public ResponseEntity<Void> deleteAthleteEvent(@PathVariable Long id) {
        log.debug("REST request to delete AthleteEvent : {}", id);
        athleteEventService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/athlete-events?query=:query : search for the athleteEvent corresponding
     * to the query.
     *
     * @param query the query of the athleteEvent search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/athlete-events")
    @Timed
    public ResponseEntity<List<AthleteEventDTO>> searchAthleteEvents(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AthleteEvents for query {}", query);
        Page<AthleteEventDTO> page = athleteEventService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/athlete-events");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
