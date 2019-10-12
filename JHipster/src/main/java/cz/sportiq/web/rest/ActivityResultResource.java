package cz.sportiq.web.rest;

import com.codahale.metrics.annotation.Timed;
import cz.sportiq.service.ActivityResultService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.web.rest.util.HeaderUtil;
import cz.sportiq.web.rest.util.PaginationUtil;
import cz.sportiq.service.dto.ActivityResultDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ActivityResult.
 */
@RestController
@RequestMapping("/api")
public class ActivityResultResource {

    private final Logger log = LoggerFactory.getLogger(ActivityResultResource.class);

    private static final String ENTITY_NAME = "activityResult";

    private final ActivityResultService activityResultService;

    public ActivityResultResource(ActivityResultService activityResultService) {
        this.activityResultService = activityResultService;
    }

    /**
     * POST  /activity-results : Create a new activityResult.
     *
     * @param activityResultDTO the activityResultDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activityResultDTO, or with status 400 (Bad Request) if the activityResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activity-results")
    @Timed
    public ResponseEntity<ActivityResultDTO> createActivityResult(@RequestBody ActivityResultDTO activityResultDTO) throws URISyntaxException {
        log.debug("REST request to save ActivityResult : {}", activityResultDTO);
        if (activityResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new activityResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityResultDTO result = activityResultService.save(activityResultDTO);
        return ResponseEntity.created(new URI("/api/activity-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activity-results : Updates an existing activityResult.
     *
     * @param activityResultDTO the activityResultDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activityResultDTO,
     * or with status 400 (Bad Request) if the activityResultDTO is not valid,
     * or with status 500 (Internal Server Error) if the activityResultDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activity-results")
    @Timed
    public ResponseEntity<ActivityResultDTO> updateActivityResult(@RequestBody ActivityResultDTO activityResultDTO) throws URISyntaxException {
        log.debug("REST request to update ActivityResult : {}", activityResultDTO);
        if (activityResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActivityResultDTO result = activityResultService.save(activityResultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activityResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activity-results : get all the activityResults.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of activityResults in body
     */
    @GetMapping("/activity-results")
    @Timed
    public ResponseEntity<List<ActivityResultDTO>> getAllActivityResults(Pageable pageable) {
        log.debug("REST request to get a page of ActivityResults");
        Page<ActivityResultDTO> page = activityResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/activity-results");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /activity-results/:id : get the "id" activityResult.
     *
     * @param id the id of the activityResultDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activityResultDTO, or with status 404 (Not Found)
     */
    @GetMapping("/activity-results/{id}")
    @Timed
    public ResponseEntity<ActivityResultDTO> getActivityResult(@PathVariable Long id) {
        log.debug("REST request to get ActivityResult : {}", id);
        Optional<ActivityResultDTO> activityResultDTO = activityResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activityResultDTO);
    }

    /**
     * DELETE  /activity-results/:id : delete the "id" activityResult.
     *
     * @param id the id of the activityResultDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activity-results/{id}")
    @Timed
    public ResponseEntity<Void> deleteActivityResult(@PathVariable Long id) {
        log.debug("REST request to delete ActivityResult : {}", id);
        activityResultService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/activity-results?query=:query : search for the activityResult corresponding
     * to the query.
     *
     * @param query the query of the activityResult search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/activity-results")
    @Timed
    public ResponseEntity<List<ActivityResultDTO>> searchActivityResults(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ActivityResults for query {}", query);
        Page<ActivityResultDTO> page = activityResultService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/activity-results");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
