package cz.sportiq.web.rest;

import com.codahale.metrics.annotation.Timed;
import cz.sportiq.service.WorkoutService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.web.rest.util.HeaderUtil;
import cz.sportiq.web.rest.util.PaginationUtil;
import cz.sportiq.service.dto.WorkoutDTO;
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
 * REST controller for managing Workout.
 */
@RestController
@RequestMapping("/api")
public class WorkoutResource {

    private final Logger log = LoggerFactory.getLogger(WorkoutResource.class);

    private static final String ENTITY_NAME = "workout";

    private final WorkoutService workoutService;

    public WorkoutResource(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    /**
     * POST  /workouts : Create a new workout.
     *
     * @param workoutDTO the workoutDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workoutDTO, or with status 400 (Bad Request) if the workout has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workouts")
    @Timed
    public ResponseEntity<WorkoutDTO> createWorkout(@Valid @RequestBody WorkoutDTO workoutDTO) throws URISyntaxException {
        log.debug("REST request to save Workout : {}", workoutDTO);
        if (workoutDTO.getId() != null) {
            throw new BadRequestAlertException("A new workout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkoutDTO result = workoutService.save(workoutDTO);
        return ResponseEntity.created(new URI("/api/workouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workouts : Updates an existing workout.
     *
     * @param workoutDTO the workoutDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workoutDTO,
     * or with status 400 (Bad Request) if the workoutDTO is not valid,
     * or with status 500 (Internal Server Error) if the workoutDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workouts")
    @Timed
    public ResponseEntity<WorkoutDTO> updateWorkout(@Valid @RequestBody WorkoutDTO workoutDTO) throws URISyntaxException {
        log.debug("REST request to update Workout : {}", workoutDTO);
        if (workoutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkoutDTO result = workoutService.save(workoutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workoutDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workouts : get all the workouts.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of workouts in body
     */
    @GetMapping("/workouts")
    @Timed
    public ResponseEntity<List<WorkoutDTO>> getAllWorkouts(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Workouts");
        Page<WorkoutDTO> page;
        if (eagerload) {
            page = workoutService.findAllWithEagerRelationships(pageable);
        } else {
            page = workoutService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/workouts?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /workouts/:id : get the "id" workout.
     *
     * @param id the id of the workoutDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workoutDTO, or with status 404 (Not Found)
     */
    @GetMapping("/workouts/{id}")
    @Timed
    public ResponseEntity<WorkoutDTO> getWorkout(@PathVariable Long id) {
        log.debug("REST request to get Workout : {}", id);
        Optional<WorkoutDTO> workoutDTO = workoutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workoutDTO);
    }

    /**
     * DELETE  /workouts/:id : delete the "id" workout.
     *
     * @param id the id of the workoutDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workouts/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        log.debug("REST request to delete Workout : {}", id);
        workoutService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/workouts?query=:query : search for the workout corresponding
     * to the query.
     *
     * @param query the query of the workout search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/workouts")
    @Timed
    public ResponseEntity<List<WorkoutDTO>> searchWorkouts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Workouts for query {}", query);
        Page<WorkoutDTO> page = workoutService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/workouts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
