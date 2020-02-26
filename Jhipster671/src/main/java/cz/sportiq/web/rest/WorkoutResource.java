package cz.sportiq.web.rest;

import cz.sportiq.service.WorkoutService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.service.dto.WorkoutDTO;

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

/**
 * REST controller for managing {@link cz.sportiq.domain.Workout}.
 */
@RestController
@RequestMapping("/api")
public class WorkoutResource {

    private final Logger log = LoggerFactory.getLogger(WorkoutResource.class);

    private static final String ENTITY_NAME = "workout";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkoutService workoutService;

    public WorkoutResource(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    /**
     * {@code POST  /workouts} : Create a new workout.
     *
     * @param workoutDTO the workoutDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workoutDTO, or with status {@code 400 (Bad Request)} if the workout has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workouts")
    public ResponseEntity<WorkoutDTO> createWorkout(@Valid @RequestBody WorkoutDTO workoutDTO) throws URISyntaxException {
        log.debug("REST request to save Workout : {}", workoutDTO);
        if (workoutDTO.getId() != null) {
            throw new BadRequestAlertException("A new workout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkoutDTO result = workoutService.save(workoutDTO);
        return ResponseEntity.created(new URI("/api/workouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workouts} : Updates an existing workout.
     *
     * @param workoutDTO the workoutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workoutDTO,
     * or with status {@code 400 (Bad Request)} if the workoutDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workoutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workouts")
    public ResponseEntity<WorkoutDTO> updateWorkout(@Valid @RequestBody WorkoutDTO workoutDTO) throws URISyntaxException {
        log.debug("REST request to update Workout : {}", workoutDTO);
        if (workoutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkoutDTO result = workoutService.save(workoutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workoutDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /workouts} : get all the workouts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workouts in body.
     */
    @GetMapping("/workouts")
    public ResponseEntity<List<WorkoutDTO>> getAllWorkouts(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Workouts");
        Page<WorkoutDTO> page;
        if (eagerload) {
            page = workoutService.findAllWithEagerRelationships(pageable);
        } else {
            page = workoutService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /workouts/:id} : get the "id" workout.
     *
     * @param id the id of the workoutDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workoutDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workouts/{id}")
    public ResponseEntity<WorkoutDTO> getWorkout(@PathVariable Long id) {
        log.debug("REST request to get Workout : {}", id);
        Optional<WorkoutDTO> workoutDTO = workoutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workoutDTO);
    }

    /**
     * {@code DELETE  /workouts/:id} : delete the "id" workout.
     *
     * @param id the id of the workoutDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workouts/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        log.debug("REST request to delete Workout : {}", id);
        workoutService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
