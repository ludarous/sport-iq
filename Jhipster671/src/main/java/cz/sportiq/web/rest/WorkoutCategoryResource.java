package cz.sportiq.web.rest;

import cz.sportiq.service.WorkoutCategoryService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.service.dto.WorkoutCategoryDTO;

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
 * REST controller for managing {@link cz.sportiq.domain.WorkoutCategory}.
 */
@RestController
@RequestMapping("/api")
public class WorkoutCategoryResource {

    private final Logger log = LoggerFactory.getLogger(WorkoutCategoryResource.class);

    private static final String ENTITY_NAME = "workoutCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkoutCategoryService workoutCategoryService;

    public WorkoutCategoryResource(WorkoutCategoryService workoutCategoryService) {
        this.workoutCategoryService = workoutCategoryService;
    }

    /**
     * {@code POST  /workout-categories} : Create a new workoutCategory.
     *
     * @param workoutCategoryDTO the workoutCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workoutCategoryDTO, or with status {@code 400 (Bad Request)} if the workoutCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workout-categories")
    public ResponseEntity<WorkoutCategoryDTO> createWorkoutCategory(@Valid @RequestBody WorkoutCategoryDTO workoutCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save WorkoutCategory : {}", workoutCategoryDTO);
        if (workoutCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new workoutCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkoutCategoryDTO result = workoutCategoryService.save(workoutCategoryDTO);
        return ResponseEntity.created(new URI("/api/workout-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workout-categories} : Updates an existing workoutCategory.
     *
     * @param workoutCategoryDTO the workoutCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workoutCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the workoutCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workoutCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workout-categories")
    public ResponseEntity<WorkoutCategoryDTO> updateWorkoutCategory(@Valid @RequestBody WorkoutCategoryDTO workoutCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update WorkoutCategory : {}", workoutCategoryDTO);
        if (workoutCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkoutCategoryDTO result = workoutCategoryService.save(workoutCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workoutCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /workout-categories} : get all the workoutCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workoutCategories in body.
     */
    @GetMapping("/workout-categories")
    public ResponseEntity<List<WorkoutCategoryDTO>> getAllWorkoutCategories(Pageable pageable) {
        log.debug("REST request to get a page of WorkoutCategories");
        Page<WorkoutCategoryDTO> page = workoutCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /workout-categories/:id} : get the "id" workoutCategory.
     *
     * @param id the id of the workoutCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workoutCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workout-categories/{id}")
    public ResponseEntity<WorkoutCategoryDTO> getWorkoutCategory(@PathVariable Long id) {
        log.debug("REST request to get WorkoutCategory : {}", id);
        Optional<WorkoutCategoryDTO> workoutCategoryDTO = workoutCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workoutCategoryDTO);
    }

    /**
     * {@code DELETE  /workout-categories/:id} : delete the "id" workoutCategory.
     *
     * @param id the id of the workoutCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workout-categories/{id}")
    public ResponseEntity<Void> deleteWorkoutCategory(@PathVariable Long id) {
        log.debug("REST request to delete WorkoutCategory : {}", id);
        workoutCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
