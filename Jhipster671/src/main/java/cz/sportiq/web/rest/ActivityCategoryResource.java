package cz.sportiq.web.rest;

import cz.sportiq.service.ActivityCategoryService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.service.dto.ActivityCategoryDTO;

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
 * REST controller for managing {@link cz.sportiq.domain.ActivityCategory}.
 */
@RestController
@RequestMapping("/api")
public class ActivityCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ActivityCategoryResource.class);

    private static final String ENTITY_NAME = "activityCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityCategoryService activityCategoryService;

    public ActivityCategoryResource(ActivityCategoryService activityCategoryService) {
        this.activityCategoryService = activityCategoryService;
    }

    /**
     * {@code POST  /activity-categories} : Create a new activityCategory.
     *
     * @param activityCategoryDTO the activityCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityCategoryDTO, or with status {@code 400 (Bad Request)} if the activityCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activity-categories")
    public ResponseEntity<ActivityCategoryDTO> createActivityCategory(@Valid @RequestBody ActivityCategoryDTO activityCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ActivityCategory : {}", activityCategoryDTO);
        if (activityCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new activityCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityCategoryDTO result = activityCategoryService.save(activityCategoryDTO);
        return ResponseEntity.created(new URI("/api/activity-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activity-categories} : Updates an existing activityCategory.
     *
     * @param activityCategoryDTO the activityCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the activityCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activity-categories")
    public ResponseEntity<ActivityCategoryDTO> updateActivityCategory(@Valid @RequestBody ActivityCategoryDTO activityCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update ActivityCategory : {}", activityCategoryDTO);
        if (activityCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActivityCategoryDTO result = activityCategoryService.save(activityCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activityCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /activity-categories} : get all the activityCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activityCategories in body.
     */
    @GetMapping("/activity-categories")
    public ResponseEntity<List<ActivityCategoryDTO>> getAllActivityCategories(Pageable pageable) {
        log.debug("REST request to get a page of ActivityCategories");
        Page<ActivityCategoryDTO> page = activityCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /activity-categories/:id} : get the "id" activityCategory.
     *
     * @param id the id of the activityCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activityCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activity-categories/{id}")
    public ResponseEntity<ActivityCategoryDTO> getActivityCategory(@PathVariable Long id) {
        log.debug("REST request to get ActivityCategory : {}", id);
        Optional<ActivityCategoryDTO> activityCategoryDTO = activityCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activityCategoryDTO);
    }

    /**
     * {@code DELETE  /activity-categories/:id} : delete the "id" activityCategory.
     *
     * @param id the id of the activityCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activity-categories/{id}")
    public ResponseEntity<Void> deleteActivityCategory(@PathVariable Long id) {
        log.debug("REST request to delete ActivityCategory : {}", id);
        activityCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
