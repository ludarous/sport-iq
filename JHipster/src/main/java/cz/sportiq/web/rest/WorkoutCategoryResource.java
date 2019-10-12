package cz.sportiq.web.rest;

import com.codahale.metrics.annotation.Timed;
import cz.sportiq.service.WorkoutCategoryService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.web.rest.util.HeaderUtil;
import cz.sportiq.web.rest.util.PaginationUtil;
import cz.sportiq.service.dto.WorkoutCategoryDTO;
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
 * REST controller for managing WorkoutCategory.
 */
@RestController
@RequestMapping("/api")
public class WorkoutCategoryResource {

    private final Logger log = LoggerFactory.getLogger(WorkoutCategoryResource.class);

    private static final String ENTITY_NAME = "workoutCategory";

    private final WorkoutCategoryService workoutCategoryService;

    public WorkoutCategoryResource(WorkoutCategoryService workoutCategoryService) {
        this.workoutCategoryService = workoutCategoryService;
    }

    /**
     * POST  /workout-categories : Create a new workoutCategory.
     *
     * @param workoutCategoryDTO the workoutCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workoutCategoryDTO, or with status 400 (Bad Request) if the workoutCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workout-categories")
    @Timed
    public ResponseEntity<WorkoutCategoryDTO> createWorkoutCategory(@Valid @RequestBody WorkoutCategoryDTO workoutCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save WorkoutCategory : {}", workoutCategoryDTO);
        if (workoutCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new workoutCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkoutCategoryDTO result = workoutCategoryService.save(workoutCategoryDTO);
        return ResponseEntity.created(new URI("/api/workout-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workout-categories : Updates an existing workoutCategory.
     *
     * @param workoutCategoryDTO the workoutCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workoutCategoryDTO,
     * or with status 400 (Bad Request) if the workoutCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the workoutCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workout-categories")
    @Timed
    public ResponseEntity<WorkoutCategoryDTO> updateWorkoutCategory(@Valid @RequestBody WorkoutCategoryDTO workoutCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update WorkoutCategory : {}", workoutCategoryDTO);
        if (workoutCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkoutCategoryDTO result = workoutCategoryService.save(workoutCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workoutCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workout-categories : get all the workoutCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of workoutCategories in body
     */
    @GetMapping("/workout-categories")
    @Timed
    public ResponseEntity<List<WorkoutCategoryDTO>> getAllWorkoutCategories(Pageable pageable) {
        log.debug("REST request to get a page of WorkoutCategories");
        Page<WorkoutCategoryDTO> page = workoutCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/workout-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /workout-categories/:id : get the "id" workoutCategory.
     *
     * @param id the id of the workoutCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workoutCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/workout-categories/{id}")
    @Timed
    public ResponseEntity<WorkoutCategoryDTO> getWorkoutCategory(@PathVariable Long id) {
        log.debug("REST request to get WorkoutCategory : {}", id);
        Optional<WorkoutCategoryDTO> workoutCategoryDTO = workoutCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workoutCategoryDTO);
    }

    /**
     * DELETE  /workout-categories/:id : delete the "id" workoutCategory.
     *
     * @param id the id of the workoutCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workout-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkoutCategory(@PathVariable Long id) {
        log.debug("REST request to delete WorkoutCategory : {}", id);
        workoutCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/workout-categories?query=:query : search for the workoutCategory corresponding
     * to the query.
     *
     * @param query the query of the workoutCategory search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/workout-categories")
    @Timed
    public ResponseEntity<List<WorkoutCategoryDTO>> searchWorkoutCategories(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of WorkoutCategories for query {}", query);
        Page<WorkoutCategoryDTO> page = workoutCategoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/workout-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
