package cz.sportiq.web.rest;

import cz.sportiq.service.AthleteEventService;
import cz.sportiq.service.dto.custom.AthleteEventSummaryDTO;
import cz.sportiq.service.impl.custom.SummaryService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.service.dto.AthleteEventDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link cz.sportiq.domain.AthleteEvent}.
 */
@RestController
@RequestMapping("/api")
public class AthleteEventResource {

    private final Logger log = LoggerFactory.getLogger(AthleteEventResource.class);

    private static final String ENTITY_NAME = "athleteEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SummaryService summaryService;
    private final AthleteEventService athleteEventService;

    public AthleteEventResource(AthleteEventService athleteEventService, SummaryService summaryService) {
        this.athleteEventService = athleteEventService;
        this.summaryService = summaryService;
    }

    /**
     * {@code POST  /athlete-events} : Create a new athleteEvent.
     *
     * @param athleteEventDTO the athleteEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new athleteEventDTO, or with status {@code 400 (Bad Request)} if the athleteEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/athlete-events")
    public ResponseEntity<AthleteEventDTO> createAthleteEvent(@Valid @RequestBody AthleteEventDTO athleteEventDTO) throws URISyntaxException {
        log.debug("REST request to save AthleteEvent : {}", athleteEventDTO);
        if (athleteEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new athleteEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AthleteEventDTO result = athleteEventService.save(athleteEventDTO);
        return ResponseEntity.created(new URI("/api/athlete-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /athlete-events} : Updates an existing athleteEvent.
     *
     * @param athleteEventDTO the athleteEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated athleteEventDTO,
     * or with status {@code 400 (Bad Request)} if the athleteEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the athleteEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/athlete-events")
    public ResponseEntity<AthleteEventDTO> updateAthleteEvent(@Valid @RequestBody AthleteEventDTO athleteEventDTO) throws URISyntaxException {
        log.debug("REST request to update AthleteEvent : {}", athleteEventDTO);
        if (athleteEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AthleteEventDTO result = athleteEventService.save(athleteEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, athleteEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /athlete-events} : get all the athleteEvents.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of athleteEvents in body.
     */
    @GetMapping("/athlete-events")
    public ResponseEntity<List<AthleteEventDTO>> getAllAthleteEvents(Pageable pageable) {
        log.debug("REST request to get a page of AthleteEvents");
        Page<AthleteEventDTO> page = athleteEventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /athlete-events/:id} : get the "id" athleteEvent.
     *
     * @param id the id of the athleteEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the athleteEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/athlete-events/{id}")
    public ResponseEntity<AthleteEventDTO> getAthleteEvent(@PathVariable Long id) {
        log.debug("REST request to get AthleteEvent : {}", id);
        Optional<AthleteEventDTO> athleteEventDTO = athleteEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(athleteEventDTO);
    }

    /**
     * {@code DELETE  /athlete-events/:id} : delete the "id" athleteEvent.
     *
     * @param id the id of the athleteEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/athlete-events/{id}")
    public ResponseEntity<Void> deleteAthleteEvent(@PathVariable Long id) {
        log.debug("REST request to delete AthleteEvent : {}", id);
        athleteEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/athlete-events?query=:query} : search for the athleteEvent corresponding
     * to the query.
     *
     * @param query the query of the athleteEvent search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/athlete-events")
    public ResponseEntity<List<AthleteEventDTO>> searchAthleteEvents(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AthleteEvents for query {}", query);
        Page<AthleteEventDTO> page = athleteEventService.search(query, pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    /*--------------------------------- CUSTOM ENDPOINTS -----------------------------------------------*/

    /**
     * GET  /athlete-events : get the athleteEvent by eventId and athleteId
     *
     * @param eventId event Id
     * @param athleteId athlete Id
     * @return the ResponseEntity with status 200 (OK) and the athleteEvent in body
     */
    @GetMapping("/athlete-events/by-event-id-and-athlete-id")
    @Timed
    public ResponseEntity<AthleteEventDTO> getAthleteEventByEventIdAndAthleteId(@RequestParam Long eventId, @RequestParam Long athleteId) {
        log.debug("REST request to get a page of AthleteEvents");
        AthleteEventDTO athleteEventDTO = athleteEventService.findByEventIdAndAthleteId(eventId, athleteId);
        if(athleteEventDTO != null) {
            return new ResponseEntity<>(athleteEventDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET  /athlete-events/by-event-id : get the athleteEvent by eventId and athleteId
     *
     * @param eventId event Id
     * @return the ResponseEntity with status 200 (OK) and the athleteEvent in body
     */
    @GetMapping("/athlete-events/by-event-id")
    @Timed
    public ResponseEntity<List<AthleteEventDTO>> getAthleteEventByEventId(@RequestParam Long eventId) {
        log.debug("REST request to get a page of AthleteEvents");
        List<AthleteEventDTO> athleteEventDTOs = athleteEventService.findAllByEventId(eventId);
        if(athleteEventDTOs != null) {
            return new ResponseEntity<>(athleteEventDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET  /athlete-events/by-athlete-id : get the athleteEvent by athleteId
     *
     * @param athleteId athlete Id
     * @return the ResponseEntity with status 200 (OK) and the athleteEvent in body
     */
    @GetMapping("/athlete-events/by-athlete-id")
    @Timed
    public ResponseEntity<List<AthleteEventDTO>> getAthleteEventByAthleteId(@RequestParam Long athleteId) {
        log.debug("REST request to get a page of AthleteEvents");
        List<AthleteEventDTO> athleteEventDTOs = athleteEventService.findAllByAthleteId(athleteId);
        if(athleteEventDTOs != null) {
            return new ResponseEntity<>(athleteEventDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET  /athlete-events/summary : get the athleteEvent by athleteId
     *
     * @param athleteId athlete Id
     * @return the ResponseEntity with status 200 (OK) and the athleteEvent in body
     */
    @GetMapping("/athlete-events/summary")
    @Timed
    public ResponseEntity<AthleteEventSummaryDTO> getAthleteEventByAthleteId(@RequestParam Long eventId, @RequestParam Long athleteId) {
        log.debug("REST request to get a page of AthleteEvents");
        AthleteEventSummaryDTO athleteEventSummaryDTO = summaryService.getAthleteEventSummary(eventId, athleteId);
        if(athleteEventSummaryDTO != null) {
            return new ResponseEntity<>(athleteEventSummaryDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
