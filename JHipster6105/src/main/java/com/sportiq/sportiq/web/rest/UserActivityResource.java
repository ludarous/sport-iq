package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.domain.UserActivity;
import com.sportiq.sportiq.repository.UserActivityRepository;
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
 * REST controller for managing {@link com.sportiq.sportiq.domain.UserActivity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserActivityResource {

    private final Logger log = LoggerFactory.getLogger(UserActivityResource.class);

    private static final String ENTITY_NAME = "userActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserActivityRepository userActivityRepository;

    public UserActivityResource(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }

    /**
     * {@code POST  /user-activities} : Create a new userActivity.
     *
     * @param userActivity the userActivity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userActivity, or with status {@code 400 (Bad Request)} if the userActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-activities")
    public ResponseEntity<UserActivity> createUserActivity(@Valid @RequestBody UserActivity userActivity) throws URISyntaxException {
        log.debug("REST request to save UserActivity : {}", userActivity);
        if (userActivity.getId() != null) {
            throw new BadRequestAlertException("A new userActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserActivity result = userActivityRepository.save(userActivity);
        return ResponseEntity.created(new URI("/api/user-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-activities} : Updates an existing userActivity.
     *
     * @param userActivity the userActivity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userActivity,
     * or with status {@code 400 (Bad Request)} if the userActivity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userActivity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-activities")
    public ResponseEntity<UserActivity> updateUserActivity(@Valid @RequestBody UserActivity userActivity) throws URISyntaxException {
        log.debug("REST request to update UserActivity : {}", userActivity);
        if (userActivity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserActivity result = userActivityRepository.save(userActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userActivity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-activities} : get all the userActivities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userActivities in body.
     */
    @GetMapping("/user-activities")
    public ResponseEntity<List<UserActivity>> getAllUserActivities(Pageable pageable) {
        log.debug("REST request to get a page of UserActivities");
        Page<UserActivity> page = userActivityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-activities/:id} : get the "id" userActivity.
     *
     * @param id the id of the userActivity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userActivity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-activities/{id}")
    public ResponseEntity<UserActivity> getUserActivity(@PathVariable Long id) {
        log.debug("REST request to get UserActivity : {}", id);
        Optional<UserActivity> userActivity = userActivityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userActivity);
    }

    /**
     * {@code DELETE  /user-activities/:id} : delete the "id" userActivity.
     *
     * @param id the id of the userActivity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-activities/{id}")
    public ResponseEntity<Void> deleteUserActivity(@PathVariable Long id) {
        log.debug("REST request to delete UserActivity : {}", id);
        userActivityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
