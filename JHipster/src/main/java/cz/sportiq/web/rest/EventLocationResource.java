package cz.sportiq.web.rest;

import cz.sportiq.service.EventLocationService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.service.dto.EventLocationDTO;

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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link cz.sportiq.domain.EventLocation}.
 */
@RestController
@RequestMapping("/api")
public class EventLocationResource {

    private final Logger log = LoggerFactory.getLogger(EventLocationResource.class);

    private static final String ENTITY_NAME = "eventLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventLocationService eventLocationService;

    public EventLocationResource(EventLocationService eventLocationService) {
        this.eventLocationService = eventLocationService;
    }

    /**
     * {@code POST  /event-locations} : Create a new eventLocation.
     *
     * @param eventLocationDTO the eventLocationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventLocationDTO, or with status {@code 400 (Bad Request)} if the eventLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-locations")
    public ResponseEntity<EventLocationDTO> createEventLocation(@Valid @RequestBody EventLocationDTO eventLocationDTO) throws URISyntaxException {
        log.debug("REST request to save EventLocation : {}", eventLocationDTO);
        if (eventLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventLocationDTO result = eventLocationService.save(eventLocationDTO);
        return ResponseEntity.created(new URI("/api/event-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-locations} : Updates an existing eventLocation.
     *
     * @param eventLocationDTO the eventLocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventLocationDTO,
     * or with status {@code 400 (Bad Request)} if the eventLocationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventLocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-locations")
    public ResponseEntity<EventLocationDTO> updateEventLocation(@Valid @RequestBody EventLocationDTO eventLocationDTO) throws URISyntaxException {
        log.debug("REST request to update EventLocation : {}", eventLocationDTO);
        if (eventLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventLocationDTO result = eventLocationService.save(eventLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /event-locations} : get all the eventLocations.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventLocations in body.
     */
    @GetMapping("/event-locations")
    public ResponseEntity<List<EventLocationDTO>> getAllEventLocations(Pageable pageable) {
        log.debug("REST request to get a page of EventLocations");
        Page<EventLocationDTO> page = eventLocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-locations/:id} : get the "id" eventLocation.
     *
     * @param id the id of the eventLocationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventLocationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-locations/{id}")
    public ResponseEntity<EventLocationDTO> getEventLocation(@PathVariable Long id) {
        log.debug("REST request to get EventLocation : {}", id);
        Optional<EventLocationDTO> eventLocationDTO = eventLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventLocationDTO);
    }

    /**
     * {@code DELETE  /event-locations/:id} : delete the "id" eventLocation.
     *
     * @param id the id of the eventLocationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-locations/{id}")
    public ResponseEntity<Void> deleteEventLocation(@PathVariable Long id) {
        log.debug("REST request to delete EventLocation : {}", id);
        eventLocationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/event-locations?query=:query} : search for the eventLocation corresponding
     * to the query.
     *
     * @param query the query of the eventLocation search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/event-locations")
    public ResponseEntity<List<EventLocationDTO>> searchEventLocations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EventLocations for query {}", query);
        Page<EventLocationDTO> page = eventLocationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
