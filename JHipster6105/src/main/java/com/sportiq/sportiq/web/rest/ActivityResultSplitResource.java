package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.domain.ActivityResultSplit;
import com.sportiq.sportiq.repository.ActivityResultSplitRepository;
import com.sportiq.sportiq.web.rest.errors.BadRequestAlertException;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sportiq.sportiq.domain.ActivityResultSplit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ActivityResultSplitResource {

    private final Logger log = LoggerFactory.getLogger(ActivityResultSplitResource.class);

    private static final String ENTITY_NAME = "activityResultSplit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityResultSplitRepository activityResultSplitRepository;

    public ActivityResultSplitResource(ActivityResultSplitRepository activityResultSplitRepository) {
        this.activityResultSplitRepository = activityResultSplitRepository;
    }

    /**
     * {@code POST  /activity-result-splits} : Create a new activityResultSplit.
     *
     * @param activityResultSplit the activityResultSplit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityResultSplit, or with status {@code 400 (Bad Request)} if the activityResultSplit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activity-result-splits")
    public ResponseEntity<ActivityResultSplit> createActivityResultSplit(@RequestBody ActivityResultSplit activityResultSplit) throws URISyntaxException {
        log.debug("REST request to save ActivityResultSplit : {}", activityResultSplit);
        if (activityResultSplit.getId() != null) {
            throw new BadRequestAlertException("A new activityResultSplit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityResultSplit result = activityResultSplitRepository.save(activityResultSplit);
        return ResponseEntity.created(new URI("/api/activity-result-splits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activity-result-splits} : Updates an existing activityResultSplit.
     *
     * @param activityResultSplit the activityResultSplit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityResultSplit,
     * or with status {@code 400 (Bad Request)} if the activityResultSplit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityResultSplit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activity-result-splits")
    public ResponseEntity<ActivityResultSplit> updateActivityResultSplit(@RequestBody ActivityResultSplit activityResultSplit) throws URISyntaxException {
        log.debug("REST request to update ActivityResultSplit : {}", activityResultSplit);
        if (activityResultSplit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActivityResultSplit result = activityResultSplitRepository.save(activityResultSplit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activityResultSplit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /activity-result-splits} : get all the activityResultSplits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activityResultSplits in body.
     */
    @GetMapping("/activity-result-splits")
    public ResponseEntity<List<ActivityResultSplit>> getAllActivityResultSplits(Pageable pageable) {
        log.debug("REST request to get a page of ActivityResultSplits");
        Page<ActivityResultSplit> page = activityResultSplitRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /activity-result-splits/:id} : get the "id" activityResultSplit.
     *
     * @param id the id of the activityResultSplit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activityResultSplit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activity-result-splits/{id}")
    public ResponseEntity<ActivityResultSplit> getActivityResultSplit(@PathVariable Long id) {
        log.debug("REST request to get ActivityResultSplit : {}", id);
        Optional<ActivityResultSplit> activityResultSplit = activityResultSplitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(activityResultSplit);
    }

    /**
     * {@code DELETE  /activity-result-splits/:id} : delete the "id" activityResultSplit.
     *
     * @param id the id of the activityResultSplit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activity-result-splits/{id}")
    public ResponseEntity<Void> deleteActivityResultSplit(@PathVariable Long id) {
        log.debug("REST request to delete ActivityResultSplit : {}", id);
        activityResultSplitRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
