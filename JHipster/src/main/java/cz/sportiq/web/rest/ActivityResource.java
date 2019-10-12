package cz.sportiq.web.rest;

import com.codahale.metrics.annotation.Timed;
import cz.sportiq.service.ActivityService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.web.rest.util.HeaderUtil;
import cz.sportiq.web.rest.util.PaginationUtil;
import cz.sportiq.service.dto.ActivityDTO;
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
 * REST controller for managing Activity.
 */
@RestController
@RequestMapping("/api")
public class ActivityResource {

    private final Logger log = LoggerFactory.getLogger(ActivityResource.class);

    private static final String ENTITY_NAME = "activity";

    private final ActivityService activityService;

    public ActivityResource(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * POST  /activities : Create a new activity.
     *
     * @param activityDTO the activityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activityDTO, or with status 400 (Bad Request) if the activity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activities")
    @Timed
    public ResponseEntity<ActivityDTO> createActivity(@Valid @RequestBody ActivityDTO activityDTO) throws URISyntaxException {
        log.debug("REST request to save Activity : {}", activityDTO);
        if (activityDTO.getId() != null) {
            throw new BadRequestAlertException("A new activity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityDTO result = activityService.save(activityDTO);
        return ResponseEntity.created(new URI("/api/activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activities : Updates an existing activity.
     *
     * @param activityDTO the activityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activityDTO,
     * or with status 400 (Bad Request) if the activityDTO is not valid,
     * or with status 500 (Internal Server Error) if the activityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activities")
    @Timed
    public ResponseEntity<ActivityDTO> updateActivity(@Valid @RequestBody ActivityDTO activityDTO) throws URISyntaxException {
        log.debug("REST request to update Activity : {}", activityDTO);
        if (activityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActivityDTO result = activityService.save(activityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activities : get all the activities.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of activities in body
     */
    @GetMapping("/activities")
    @Timed
    public ResponseEntity<List<ActivityDTO>> getAllActivities(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Activities");
        Page<ActivityDTO> page;
        if (eagerload) {
            page = activityService.findAllWithEagerRelationships(pageable);
        } else {
            page = activityService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/activities?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /activities/:id : get the "id" activity.
     *
     * @param id the id of the activityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/activities/{id}")
    @Timed
    public ResponseEntity<ActivityDTO> getActivity(@PathVariable Long id) {
        log.debug("REST request to get Activity : {}", id);
        Optional<ActivityDTO> activityDTO = activityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activityDTO);
    }

    /**
     * DELETE  /activities/:id : delete the "id" activity.
     *
     * @param id the id of the activityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        log.debug("REST request to delete Activity : {}", id);
        activityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/activities?query=:query : search for the activity corresponding
     * to the query.
     *
     * @param query the query of the activity search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/activities")
    @Timed
    public ResponseEntity<List<ActivityDTO>> searchActivities(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Activities for query {}", query);
        Page<ActivityDTO> page = activityService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
