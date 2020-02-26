package cz.sportiq.web.rest;

import cz.sportiq.service.ActivityResultSplitService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.service.dto.ActivityResultSplitDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link cz.sportiq.domain.ActivityResultSplit}.
 */
@RestController
@RequestMapping("/api")
public class ActivityResultSplitResource {

    private final Logger log = LoggerFactory.getLogger(ActivityResultSplitResource.class);

    private static final String ENTITY_NAME = "activityResultSplit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityResultSplitService activityResultSplitService;

    public ActivityResultSplitResource(ActivityResultSplitService activityResultSplitService) {
        this.activityResultSplitService = activityResultSplitService;
    }

    /**
     * {@code POST  /activity-result-splits} : Create a new activityResultSplit.
     *
     * @param activityResultSplitDTO the activityResultSplitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityResultSplitDTO, or with status {@code 400 (Bad Request)} if the activityResultSplit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activity-result-splits")
    public ResponseEntity<ActivityResultSplitDTO> createActivityResultSplit(@RequestBody ActivityResultSplitDTO activityResultSplitDTO) throws URISyntaxException {
        log.debug("REST request to save ActivityResultSplit : {}", activityResultSplitDTO);
        if (activityResultSplitDTO.getId() != null) {
            throw new BadRequestAlertException("A new activityResultSplit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityResultSplitDTO result = activityResultSplitService.save(activityResultSplitDTO);
        return ResponseEntity.created(new URI("/api/activity-result-splits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activity-result-splits} : Updates an existing activityResultSplit.
     *
     * @param activityResultSplitDTO the activityResultSplitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityResultSplitDTO,
     * or with status {@code 400 (Bad Request)} if the activityResultSplitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityResultSplitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activity-result-splits")
    public ResponseEntity<ActivityResultSplitDTO> updateActivityResultSplit(@RequestBody ActivityResultSplitDTO activityResultSplitDTO) throws URISyntaxException {
        log.debug("REST request to update ActivityResultSplit : {}", activityResultSplitDTO);
        if (activityResultSplitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActivityResultSplitDTO result = activityResultSplitService.save(activityResultSplitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activityResultSplitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /activity-result-splits} : get all the activityResultSplits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activityResultSplits in body.
     */
    @GetMapping("/activity-result-splits")
    public ResponseEntity<List<ActivityResultSplitDTO>> getAllActivityResultSplits(Pageable pageable) {
        log.debug("REST request to get a page of ActivityResultSplits");
        Page<ActivityResultSplitDTO> page = activityResultSplitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /activity-result-splits/:id} : get the "id" activityResultSplit.
     *
     * @param id the id of the activityResultSplitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activityResultSplitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activity-result-splits/{id}")
    public ResponseEntity<ActivityResultSplitDTO> getActivityResultSplit(@PathVariable Long id) {
        log.debug("REST request to get ActivityResultSplit : {}", id);
        Optional<ActivityResultSplitDTO> activityResultSplitDTO = activityResultSplitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activityResultSplitDTO);
    }

    /**
     * {@code DELETE  /activity-result-splits/:id} : delete the "id" activityResultSplit.
     *
     * @param id the id of the activityResultSplitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activity-result-splits/{id}")
    public ResponseEntity<Void> deleteActivityResultSplit(@PathVariable Long id) {
        log.debug("REST request to delete ActivityResultSplit : {}", id);
        activityResultSplitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
