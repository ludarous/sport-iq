package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.service.ActivityResultService;
import com.sportiq.sportiq.web.rest.errors.BadRequestAlertException;
import com.sportiq.sportiq.service.dto.ActivityResultDTO;

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
 * REST controller for managing {@link com.sportiq.sportiq.domain.ActivityResult}.
 */
@RestController
@RequestMapping("/api")
public class ActivityResultResource {

    private final Logger log = LoggerFactory.getLogger(ActivityResultResource.class);

    private static final String ENTITY_NAME = "activityResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityResultService activityResultService;

    public ActivityResultResource(ActivityResultService activityResultService) {
        this.activityResultService = activityResultService;
    }

    /**
     * {@code POST  /activity-results} : Create a new activityResult.
     *
     * @param activityResultDTO the activityResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityResultDTO, or with status {@code 400 (Bad Request)} if the activityResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activity-results")
    public ResponseEntity<ActivityResultDTO> createActivityResult(@RequestBody ActivityResultDTO activityResultDTO) throws URISyntaxException {
        log.debug("REST request to save ActivityResult : {}", activityResultDTO);
        if (activityResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new activityResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityResultDTO result = activityResultService.save(activityResultDTO);
        return ResponseEntity.created(new URI("/api/activity-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activity-results} : Updates an existing activityResult.
     *
     * @param activityResultDTO the activityResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityResultDTO,
     * or with status {@code 400 (Bad Request)} if the activityResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activity-results")
    public ResponseEntity<ActivityResultDTO> updateActivityResult(@RequestBody ActivityResultDTO activityResultDTO) throws URISyntaxException {
        log.debug("REST request to update ActivityResult : {}", activityResultDTO);
        if (activityResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActivityResultDTO result = activityResultService.save(activityResultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activityResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /activity-results} : get all the activityResults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activityResults in body.
     */
    @GetMapping("/activity-results")
    public ResponseEntity<List<ActivityResultDTO>> getAllActivityResults(Pageable pageable) {
        log.debug("REST request to get a page of ActivityResults");
        Page<ActivityResultDTO> page = activityResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /activity-results/:id} : get the "id" activityResult.
     *
     * @param id the id of the activityResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activityResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activity-results/{id}")
    public ResponseEntity<ActivityResultDTO> getActivityResult(@PathVariable Long id) {
        log.debug("REST request to get ActivityResult : {}", id);
        Optional<ActivityResultDTO> activityResultDTO = activityResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activityResultDTO);
    }

    /**
     * {@code DELETE  /activity-results/:id} : delete the "id" activityResult.
     *
     * @param id the id of the activityResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activity-results/{id}")
    public ResponseEntity<Void> deleteActivityResult(@PathVariable Long id) {
        log.debug("REST request to delete ActivityResult : {}", id);
        activityResultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
