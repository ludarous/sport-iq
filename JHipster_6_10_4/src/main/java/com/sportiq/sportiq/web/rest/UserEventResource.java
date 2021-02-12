package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.service.UserEventService;
import com.sportiq.sportiq.web.rest.errors.BadRequestAlertException;
import com.sportiq.sportiq.service.dto.UserEventDTO;

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
 * REST controller for managing {@link com.sportiq.sportiq.domain.UserEvent}.
 */
@RestController
@RequestMapping("/api")
public class UserEventResource {

    private final Logger log = LoggerFactory.getLogger(UserEventResource.class);

    private static final String ENTITY_NAME = "userEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserEventService userEventService;

    public UserEventResource(UserEventService userEventService) {
        this.userEventService = userEventService;
    }

    /**
     * {@code POST  /user-events} : Create a new userEvent.
     *
     * @param userEventDTO the userEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userEventDTO, or with status {@code 400 (Bad Request)} if the userEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-events")
    public ResponseEntity<UserEventDTO> createUserEvent(@Valid @RequestBody UserEventDTO userEventDTO) throws URISyntaxException {
        log.debug("REST request to save UserEvent : {}", userEventDTO);
        if (userEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new userEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserEventDTO result = userEventService.save(userEventDTO);
        return ResponseEntity.created(new URI("/api/user-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-events} : Updates an existing userEvent.
     *
     * @param userEventDTO the userEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userEventDTO,
     * or with status {@code 400 (Bad Request)} if the userEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-events")
    public ResponseEntity<UserEventDTO> updateUserEvent(@Valid @RequestBody UserEventDTO userEventDTO) throws URISyntaxException {
        log.debug("REST request to update UserEvent : {}", userEventDTO);
        if (userEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserEventDTO result = userEventService.save(userEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-events} : get all the userEvents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userEvents in body.
     */
    @GetMapping("/user-events")
    public ResponseEntity<List<UserEventDTO>> getAllUserEvents(Pageable pageable) {
        log.debug("REST request to get a page of UserEvents");
        Page<UserEventDTO> page = userEventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-events/:id} : get the "id" userEvent.
     *
     * @param id the id of the userEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-events/{id}")
    public ResponseEntity<UserEventDTO> getUserEvent(@PathVariable Long id) {
        log.debug("REST request to get UserEvent : {}", id);
        Optional<UserEventDTO> userEventDTO = userEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userEventDTO);
    }

    /**
     * {@code DELETE  /user-events/:id} : delete the "id" userEvent.
     *
     * @param id the id of the userEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-events/{id}")
    public ResponseEntity<Void> deleteUserEvent(@PathVariable Long id) {
        log.debug("REST request to delete UserEvent : {}", id);
        userEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
