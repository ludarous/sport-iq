package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.domain.UserActivityResultSplit;
import com.sportiq.sportiq.repository.UserActivityResultSplitRepository;
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
 * REST controller for managing {@link com.sportiq.sportiq.domain.UserActivityResultSplit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserActivityResultSplitResource {

    private final Logger log = LoggerFactory.getLogger(UserActivityResultSplitResource.class);

    private static final String ENTITY_NAME = "userActivityResultSplit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserActivityResultSplitRepository userActivityResultSplitRepository;

    public UserActivityResultSplitResource(UserActivityResultSplitRepository userActivityResultSplitRepository) {
        this.userActivityResultSplitRepository = userActivityResultSplitRepository;
    }

    /**
     * {@code POST  /user-activity-result-splits} : Create a new userActivityResultSplit.
     *
     * @param userActivityResultSplit the userActivityResultSplit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userActivityResultSplit, or with status {@code 400 (Bad Request)} if the userActivityResultSplit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-activity-result-splits")
    public ResponseEntity<UserActivityResultSplit> createUserActivityResultSplit(@Valid @RequestBody UserActivityResultSplit userActivityResultSplit) throws URISyntaxException {
        log.debug("REST request to save UserActivityResultSplit : {}", userActivityResultSplit);
        if (userActivityResultSplit.getId() != null) {
            throw new BadRequestAlertException("A new userActivityResultSplit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserActivityResultSplit result = userActivityResultSplitRepository.save(userActivityResultSplit);
        return ResponseEntity.created(new URI("/api/user-activity-result-splits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-activity-result-splits} : Updates an existing userActivityResultSplit.
     *
     * @param userActivityResultSplit the userActivityResultSplit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userActivityResultSplit,
     * or with status {@code 400 (Bad Request)} if the userActivityResultSplit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userActivityResultSplit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-activity-result-splits")
    public ResponseEntity<UserActivityResultSplit> updateUserActivityResultSplit(@Valid @RequestBody UserActivityResultSplit userActivityResultSplit) throws URISyntaxException {
        log.debug("REST request to update UserActivityResultSplit : {}", userActivityResultSplit);
        if (userActivityResultSplit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserActivityResultSplit result = userActivityResultSplitRepository.save(userActivityResultSplit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userActivityResultSplit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-activity-result-splits} : get all the userActivityResultSplits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userActivityResultSplits in body.
     */
    @GetMapping("/user-activity-result-splits")
    public ResponseEntity<List<UserActivityResultSplit>> getAllUserActivityResultSplits(Pageable pageable) {
        log.debug("REST request to get a page of UserActivityResultSplits");
        Page<UserActivityResultSplit> page = userActivityResultSplitRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-activity-result-splits/:id} : get the "id" userActivityResultSplit.
     *
     * @param id the id of the userActivityResultSplit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userActivityResultSplit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-activity-result-splits/{id}")
    public ResponseEntity<UserActivityResultSplit> getUserActivityResultSplit(@PathVariable Long id) {
        log.debug("REST request to get UserActivityResultSplit : {}", id);
        Optional<UserActivityResultSplit> userActivityResultSplit = userActivityResultSplitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userActivityResultSplit);
    }

    /**
     * {@code DELETE  /user-activity-result-splits/:id} : delete the "id" userActivityResultSplit.
     *
     * @param id the id of the userActivityResultSplit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-activity-result-splits/{id}")
    public ResponseEntity<Void> deleteUserActivityResultSplit(@PathVariable Long id) {
        log.debug("REST request to delete UserActivityResultSplit : {}", id);
        userActivityResultSplitRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
