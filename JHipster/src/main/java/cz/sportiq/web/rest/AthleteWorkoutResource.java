package cz.sportiq.web.rest;

import com.codahale.metrics.annotation.Timed;
import cz.sportiq.service.AthleteWorkoutService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.web.rest.util.HeaderUtil;
import cz.sportiq.web.rest.util.PaginationUtil;
import cz.sportiq.service.dto.AthleteWorkoutDTO;
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
 * REST controller for managing AthleteWorkout.
 */
@RestController
@RequestMapping("/api")
public class AthleteWorkoutResource {

    private final Logger log = LoggerFactory.getLogger(AthleteWorkoutResource.class);

    private static final String ENTITY_NAME = "athleteWorkout";

    private final AthleteWorkoutService athleteWorkoutService;

    public AthleteWorkoutResource(AthleteWorkoutService athleteWorkoutService) {
        this.athleteWorkoutService = athleteWorkoutService;
    }

    /**
     * POST  /athlete-workouts : Create a new athleteWorkout.
     *
     * @param athleteWorkoutDTO the athleteWorkoutDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new athleteWorkoutDTO, or with status 400 (Bad Request) if the athleteWorkout has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/athlete-workouts")
    @Timed
    public ResponseEntity<AthleteWorkoutDTO> createAthleteWorkout(@Valid @RequestBody AthleteWorkoutDTO athleteWorkoutDTO) throws URISyntaxException {
        log.debug("REST request to save AthleteWorkout : {}", athleteWorkoutDTO);
        if (athleteWorkoutDTO.getId() != null) {
            throw new BadRequestAlertException("A new athleteWorkout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AthleteWorkoutDTO result = athleteWorkoutService.save(athleteWorkoutDTO);
        return ResponseEntity.created(new URI("/api/athlete-workouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /athlete-workouts : Updates an existing athleteWorkout.
     *
     * @param athleteWorkoutDTO the athleteWorkoutDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated athleteWorkoutDTO,
     * or with status 400 (Bad Request) if the athleteWorkoutDTO is not valid,
     * or with status 500 (Internal Server Error) if the athleteWorkoutDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/athlete-workouts")
    @Timed
    public ResponseEntity<AthleteWorkoutDTO> updateAthleteWorkout(@Valid @RequestBody AthleteWorkoutDTO athleteWorkoutDTO) throws URISyntaxException {
        log.debug("REST request to update AthleteWorkout : {}", athleteWorkoutDTO);
        if (athleteWorkoutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AthleteWorkoutDTO result = athleteWorkoutService.save(athleteWorkoutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, athleteWorkoutDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /athlete-workouts : get all the athleteWorkouts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of athleteWorkouts in body
     */
    @GetMapping("/athlete-workouts")
    @Timed
    public ResponseEntity<List<AthleteWorkoutDTO>> getAllAthleteWorkouts(Pageable pageable) {
        log.debug("REST request to get a page of AthleteWorkouts");
        Page<AthleteWorkoutDTO> page = athleteWorkoutService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/athlete-workouts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /athlete-workouts/:id : get the "id" athleteWorkout.
     *
     * @param id the id of the athleteWorkoutDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the athleteWorkoutDTO, or with status 404 (Not Found)
     */
    @GetMapping("/athlete-workouts/{id}")
    @Timed
    public ResponseEntity<AthleteWorkoutDTO> getAthleteWorkout(@PathVariable Long id) {
        log.debug("REST request to get AthleteWorkout : {}", id);
        Optional<AthleteWorkoutDTO> athleteWorkoutDTO = athleteWorkoutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(athleteWorkoutDTO);
    }

    /**
     * DELETE  /athlete-workouts/:id : delete the "id" athleteWorkout.
     *
     * @param id the id of the athleteWorkoutDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/athlete-workouts/{id}")
    @Timed
    public ResponseEntity<Void> deleteAthleteWorkout(@PathVariable Long id) {
        log.debug("REST request to delete AthleteWorkout : {}", id);
        athleteWorkoutService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/athlete-workouts?query=:query : search for the athleteWorkout corresponding
     * to the query.
     *
     * @param query the query of the athleteWorkout search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/athlete-workouts")
    @Timed
    public ResponseEntity<List<AthleteWorkoutDTO>> searchAthleteWorkouts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AthleteWorkouts for query {}", query);
        Page<AthleteWorkoutDTO> page = athleteWorkoutService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/athlete-workouts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /*--------------------------------- CUSTOM ENDPOINTS -----------------------------------------------*/

    /**
     * GET  /athlete-workouts/by-workout-id-and-athlete-id : get the athleteWorkout by athleteEventId and workoutId
     *
     * @param workoutId workout Id
     * @param athleteEventId athlete event Id
     * @return the ResponseEntity with status 200 (OK) and the athleteEvent in body
     */
    @GetMapping("/athlete-workouts/by-workout-id-and-athleteEvent-id")
    @Timed
    public ResponseEntity<AthleteWorkoutDTO> getAthleteWorkoutByWorkoutIdAndAthleteEventId(@RequestParam Long workoutId, @RequestParam Long athleteEventId) {
        log.debug("REST request to get a page of AthleteWorkouts");
        AthleteWorkoutDTO athleteWorkoutDTO = athleteWorkoutService.findByWorkoutIdAndAthleteEventId(workoutId, athleteEventId);
        if(athleteWorkoutDTO != null) {
            return new ResponseEntity<>(athleteWorkoutDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
