package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.service.GroupMembershipService;
import com.sportiq.sportiq.web.rest.errors.BadRequestAlertException;
import com.sportiq.sportiq.service.dto.GroupMembershipDTO;

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
 * REST controller for managing {@link com.sportiq.sportiq.domain.GroupMembership}.
 */
@RestController
@RequestMapping("/api")
public class GroupMembershipResource {

    private final Logger log = LoggerFactory.getLogger(GroupMembershipResource.class);

    private static final String ENTITY_NAME = "groupMembership";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupMembershipService groupMembershipService;

    public GroupMembershipResource(GroupMembershipService groupMembershipService) {
        this.groupMembershipService = groupMembershipService;
    }

    /**
     * {@code POST  /group-memberships} : Create a new groupMembership.
     *
     * @param groupMembershipDTO the groupMembershipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupMembershipDTO, or with status {@code 400 (Bad Request)} if the groupMembership has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-memberships")
    public ResponseEntity<GroupMembershipDTO> createGroupMembership(@RequestBody GroupMembershipDTO groupMembershipDTO) throws URISyntaxException {
        log.debug("REST request to save GroupMembership : {}", groupMembershipDTO);
        if (groupMembershipDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupMembership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupMembershipDTO result = groupMembershipService.save(groupMembershipDTO);
        return ResponseEntity.created(new URI("/api/group-memberships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-memberships} : Updates an existing groupMembership.
     *
     * @param groupMembershipDTO the groupMembershipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupMembershipDTO,
     * or with status {@code 400 (Bad Request)} if the groupMembershipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupMembershipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-memberships")
    public ResponseEntity<GroupMembershipDTO> updateGroupMembership(@RequestBody GroupMembershipDTO groupMembershipDTO) throws URISyntaxException {
        log.debug("REST request to update GroupMembership : {}", groupMembershipDTO);
        if (groupMembershipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupMembershipDTO result = groupMembershipService.save(groupMembershipDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupMembershipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-memberships} : get all the groupMemberships.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupMemberships in body.
     */
    @GetMapping("/group-memberships")
    public ResponseEntity<List<GroupMembershipDTO>> getAllGroupMemberships(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of GroupMemberships");
        Page<GroupMembershipDTO> page;
        if (eagerload) {
            page = groupMembershipService.findAllWithEagerRelationships(pageable);
        } else {
            page = groupMembershipService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /group-memberships/:id} : get the "id" groupMembership.
     *
     * @param id the id of the groupMembershipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupMembershipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-memberships/{id}")
    public ResponseEntity<GroupMembershipDTO> getGroupMembership(@PathVariable Long id) {
        log.debug("REST request to get GroupMembership : {}", id);
        Optional<GroupMembershipDTO> groupMembershipDTO = groupMembershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupMembershipDTO);
    }

    /**
     * {@code DELETE  /group-memberships/:id} : delete the "id" groupMembership.
     *
     * @param id the id of the groupMembershipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-memberships/{id}")
    public ResponseEntity<Void> deleteGroupMembership(@PathVariable Long id) {
        log.debug("REST request to delete GroupMembership : {}", id);
        groupMembershipService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
