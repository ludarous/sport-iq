package cz.sportiq.web.rest;

import com.codahale.metrics.annotation.Timed;
import cz.sportiq.service.AthleteActivityService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.web.rest.util.HeaderUtil;
import cz.sportiq.web.rest.util.PaginationUtil;
import cz.sportiq.service.dto.AthleteActivityDTO;
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
 * REST controller for managing AthleteActivity.
 */
@RestController
@RequestMapping("/api")
public class AthleteActivityResource {

    private final Logger log = LoggerFactory.getLogger(AthleteActivityResource.class);

    private static final String ENTITY_NAME = "athleteActivity";

    private final AthleteActivityService athleteActivityService;

    public AthleteActivityResource(AthleteActivityService athleteActivityService) {
        this.athleteActivityService = athleteActivityService;
    }

    /**
     * POST  /athlete-activities : Create a new athleteActivity.
     *
     * @param athleteActivityDTO the athleteActivityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new athleteActivityDTO, or with status 400 (Bad Request) if the athleteActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/athlete-activities")
    @Timed
    public ResponseEntity<AthleteActivityDTO> createAthleteActivity(@Valid @RequestBody AthleteActivityDTO athleteActivityDTO) throws URISyntaxException {
        log.debug("REST request to save AthleteActivity : {}", athleteActivityDTO);
        if (athleteActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new athleteActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AthleteActivityDTO result = athleteActivityService.saveComplete(athleteActivityDTO);
        return ResponseEntity.created(new URI("/api/athlete-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /athlete-activities : Updates an existing athleteActivity.
     *
     * @param athleteActivityDTO the athleteActivityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated athleteActivityDTO,
     * or with status 400 (Bad Request) if the athleteActivityDTO is not valid,
     * or with status 500 (Internal Server Error) if the athleteActivityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/athlete-activities")
    @Timed
    public ResponseEntity<AthleteActivityDTO> updateAthleteActivity(@Valid @RequestBody AthleteActivityDTO athleteActivityDTO) throws URISyntaxException {
        log.debug("REST request to update AthleteActivity : {}", athleteActivityDTO);
        if (athleteActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AthleteActivityDTO result = athleteActivityService.save(athleteActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, athleteActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /athlete-activities : get all the athleteActivities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of athleteActivities in body
     */
    @GetMapping("/athlete-activities")
    @Timed
    public ResponseEntity<List<AthleteActivityDTO>> getAllAthleteActivities(Pageable pageable) {
        log.debug("REST request to get a page of AthleteActivities");
        Page<AthleteActivityDTO> page = athleteActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/athlete-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /athlete-activities/:id : get the "id" athleteActivity.
     *
     * @param id the id of the athleteActivityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the athleteActivityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/athlete-activities/{id}")
    @Timed
    public ResponseEntity<AthleteActivityDTO> getAthleteActivity(@PathVariable Long id) {
        log.debug("REST request to get AthleteActivity : {}", id);
        Optional<AthleteActivityDTO> athleteActivityDTO = athleteActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(athleteActivityDTO);
    }

    /**
     * DELETE  /athlete-activities/:id : delete the "id" athleteActivity.
     *
     * @param id the id of the athleteActivityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/athlete-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteAthleteActivity(@PathVariable Long id) {
        log.debug("REST request to delete AthleteActivity : {}", id);
        athleteActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/athlete-activities?query=:query : search for the athleteActivity corresponding
     * to the query.
     *
     * @param query the query of the athleteActivity search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/athlete-activities")
    @Timed
    public ResponseEntity<List<AthleteActivityDTO>> searchAthleteActivities(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AthleteActivities for query {}", query);
        Page<AthleteActivityDTO> page = athleteActivityService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/athlete-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /*--------------------------------- CUSTOM ENDPOINTS -----------------------------------------------*/

    /**
     * GET  /athlete-activities/by-activity-id-and-athlete-id : get the athleteActivity by athleteWorkoutId and activityId
     *
     * @param activityId activity Id
     * @param athleteWorkoutId athlete event Id
     * @return the ResponseEntity with status 200 (OK) and the athleteWorkout in body
     */
    @GetMapping("/athlete-activities/by-activity-id-and-athleteWorkout-id")
    @Timed
    public ResponseEntity<AthleteActivityDTO> getAthleteActivityByActivityIdAndAthleteWorkoutId(@RequestParam Long activityId, @RequestParam Long athleteWorkoutId) {
        log.debug("REST request to get a page of AthleteActivities");
        AthleteActivityDTO athleteActivityDTO = athleteActivityService.findByActivityIdAndAthleteWorkoutId(activityId, athleteWorkoutId);
        if(athleteActivityDTO != null) {
            return new ResponseEntity<>(athleteActivityDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
