package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.domain.UserActivityResult;
import com.sportiq.sportiq.repository.UserActivityResultRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sportiq.sportiq.domain.UserActivityResult}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserActivityResultResource {

    private final Logger log = LoggerFactory.getLogger(UserActivityResultResource.class);

    private static final String ENTITY_NAME = "userActivityResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserActivityResultRepository userActivityResultRepository;

    public UserActivityResultResource(UserActivityResultRepository userActivityResultRepository) {
        this.userActivityResultRepository = userActivityResultRepository;
    }

    /**
     * {@code POST  /user-activity-results} : Create a new userActivityResult.
     *
     * @param userActivityResult the userActivityResult to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userActivityResult, or with status {@code 400 (Bad Request)} if the userActivityResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-activity-results")
    public ResponseEntity<UserActivityResult> createUserActivityResult(@Valid @RequestBody UserActivityResult userActivityResult) throws URISyntaxException {
        log.debug("REST request to save UserActivityResult : {}", userActivityResult);
        if (userActivityResult.getId() != null) {
            throw new BadRequestAlertException("A new userActivityResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserActivityResult result = userActivityResultRepository.save(userActivityResult);
        return ResponseEntity.created(new URI("/api/user-activity-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-activity-results} : Updates an existing userActivityResult.
     *
     * @param userActivityResult the userActivityResult to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userActivityResult,
     * or with status {@code 400 (Bad Request)} if the userActivityResult is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userActivityResult couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-activity-results")
    public ResponseEntity<UserActivityResult> updateUserActivityResult(@Valid @RequestBody UserActivityResult userActivityResult) throws URISyntaxException {
        log.debug("REST request to update UserActivityResult : {}", userActivityResult);
        if (userActivityResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserActivityResult result = userActivityResultRepository.save(userActivityResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userActivityResult.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-activity-results} : get all the userActivityResults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userActivityResults in body.
     */
    @GetMapping("/user-activity-results")
    public ResponseEntity<List<UserActivityResult>> getAllUserActivityResults(Pageable pageable) {
        log.debug("REST request to get a page of UserActivityResults");
        Page<UserActivityResult> page = userActivityResultRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-activity-results/:id} : get the "id" userActivityResult.
     *
     * @param id the id of the userActivityResult to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userActivityResult, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-activity-results/{id}")
    public ResponseEntity<UserActivityResult> getUserActivityResult(@PathVariable Long id) {
        log.debug("REST request to get UserActivityResult : {}", id);
        Optional<UserActivityResult> userActivityResult = userActivityResultRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userActivityResult);
    }

    /**
     * {@code DELETE  /user-activity-results/:id} : delete the "id" userActivityResult.
     *
     * @param id the id of the userActivityResult to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-activity-results/{id}")
    public ResponseEntity<Void> deleteUserActivityResult(@PathVariable Long id) {
        log.debug("REST request to delete UserActivityResult : {}", id);
        userActivityResultRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
