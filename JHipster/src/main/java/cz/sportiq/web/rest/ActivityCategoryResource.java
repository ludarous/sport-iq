package cz.sportiq.web.rest;

import com.codahale.metrics.annotation.Timed;
import cz.sportiq.service.ActivityCategoryService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.web.rest.util.HeaderUtil;
import cz.sportiq.web.rest.util.PaginationUtil;
import cz.sportiq.service.dto.ActivityCategoryDTO;
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
 * REST controller for managing ActivityCategory.
 */
@RestController
@RequestMapping("/api")
public class ActivityCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ActivityCategoryResource.class);

    private static final String ENTITY_NAME = "activityCategory";

    private final ActivityCategoryService activityCategoryService;

    public ActivityCategoryResource(ActivityCategoryService activityCategoryService) {
        this.activityCategoryService = activityCategoryService;
    }

    /**
     * POST  /activity-categories : Create a new activityCategory.
     *
     * @param activityCategoryDTO the activityCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activityCategoryDTO, or with status 400 (Bad Request) if the activityCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activity-categories")
    @Timed
    public ResponseEntity<ActivityCategoryDTO> createActivityCategory(@Valid @RequestBody ActivityCategoryDTO activityCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ActivityCategory : {}", activityCategoryDTO);
        if (activityCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new activityCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityCategoryDTO result = activityCategoryService.save(activityCategoryDTO);
        return ResponseEntity.created(new URI("/api/activity-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activity-categories : Updates an existing activityCategory.
     *
     * @param activityCategoryDTO the activityCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activityCategoryDTO,
     * or with status 400 (Bad Request) if the activityCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the activityCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activity-categories")
    @Timed
    public ResponseEntity<ActivityCategoryDTO> updateActivityCategory(@Valid @RequestBody ActivityCategoryDTO activityCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update ActivityCategory : {}", activityCategoryDTO);
        if (activityCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActivityCategoryDTO result = activityCategoryService.save(activityCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activityCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activity-categories : get all the activityCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of activityCategories in body
     */
    @GetMapping("/activity-categories")
    @Timed
    public ResponseEntity<List<ActivityCategoryDTO>> getAllActivityCategories(Pageable pageable) {
        log.debug("REST request to get a page of ActivityCategories");
        Page<ActivityCategoryDTO> page = activityCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/activity-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /activity-categories/:id : get the "id" activityCategory.
     *
     * @param id the id of the activityCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activityCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/activity-categories/{id}")
    @Timed
    public ResponseEntity<ActivityCategoryDTO> getActivityCategory(@PathVariable Long id) {
        log.debug("REST request to get ActivityCategory : {}", id);
        Optional<ActivityCategoryDTO> activityCategoryDTO = activityCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activityCategoryDTO);
    }

    /**
     * DELETE  /activity-categories/:id : delete the "id" activityCategory.
     *
     * @param id the id of the activityCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activity-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteActivityCategory(@PathVariable Long id) {
        log.debug("REST request to delete ActivityCategory : {}", id);
        activityCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/activity-categories?query=:query : search for the activityCategory corresponding
     * to the query.
     *
     * @param query the query of the activityCategory search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/activity-categories")
    @Timed
    public ResponseEntity<List<ActivityCategoryDTO>> searchActivityCategories(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ActivityCategories for query {}", query);
        Page<ActivityCategoryDTO> page = activityCategoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/activity-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
