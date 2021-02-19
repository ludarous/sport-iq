package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.domain.UserEvent;
import com.sportiq.sportiq.repository.UserEventRepository;
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
 * REST controller for managing {@link com.sportiq.sportiq.domain.UserEvent}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserEventResource {

    private final Logger log = LoggerFactory.getLogger(UserEventResource.class);

    private static final String ENTITY_NAME = "userEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserEventRepository userEventRepository;

    public UserEventResource(UserEventRepository userEventRepository) {
        this.userEventRepository = userEventRepository;
    }

    /**
     * {@code POST  /user-events} : Create a new userEvent.
     *
     * @param userEvent the userEvent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userEvent, or with status {@code 400 (Bad Request)} if the userEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-events")
    public ResponseEntity<UserEvent> createUserEvent(@Valid @RequestBody UserEvent userEvent) throws URISyntaxException {
        log.debug("REST request to save UserEvent : {}", userEvent);
        if (userEvent.getId() != null) {
            throw new BadRequestAlertException("A new userEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserEvent result = userEventRepository.save(userEvent);
        return ResponseEntity.created(new URI("/api/user-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-events} : Updates an existing userEvent.
     *
     * @param userEvent the userEvent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userEvent,
     * or with status {@code 400 (Bad Request)} if the userEvent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userEvent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-events")
    public ResponseEntity<UserEvent> updateUserEvent(@Valid @RequestBody UserEvent userEvent) throws URISyntaxException {
        log.debug("REST request to update UserEvent : {}", userEvent);
        if (userEvent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserEvent result = userEventRepository.save(userEvent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userEvent.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-events} : get all the userEvents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userEvents in body.
     */
    @GetMapping("/user-events")
    public ResponseEntity<List<UserEvent>> getAllUserEvents(Pageable pageable) {
        log.debug("REST request to get a page of UserEvents");
        Page<UserEvent> page = userEventRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-events/:id} : get the "id" userEvent.
     *
     * @param id the id of the userEvent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userEvent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-events/{id}")
    public ResponseEntity<UserEvent> getUserEvent(@PathVariable Long id) {
        log.debug("REST request to get UserEvent : {}", id);
        Optional<UserEvent> userEvent = userEventRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userEvent);
    }

    /**
     * {@code DELETE  /user-events/:id} : delete the "id" userEvent.
     *
     * @param id the id of the userEvent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-events/{id}")
    public ResponseEntity<Void> deleteUserEvent(@PathVariable Long id) {
        log.debug("REST request to delete UserEvent : {}", id);
        userEventRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
