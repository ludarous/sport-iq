package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.domain.MembershipRole;
import com.sportiq.sportiq.repository.MembershipRoleRepository;
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
 * REST controller for managing {@link com.sportiq.sportiq.domain.MembershipRole}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MembershipRoleResource {

    private final Logger log = LoggerFactory.getLogger(MembershipRoleResource.class);

    private static final String ENTITY_NAME = "membershipRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MembershipRoleRepository membershipRoleRepository;

    public MembershipRoleResource(MembershipRoleRepository membershipRoleRepository) {
        this.membershipRoleRepository = membershipRoleRepository;
    }

    /**
     * {@code POST  /membership-roles} : Create a new membershipRole.
     *
     * @param membershipRole the membershipRole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new membershipRole, or with status {@code 400 (Bad Request)} if the membershipRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/membership-roles")
    public ResponseEntity<MembershipRole> createMembershipRole(@RequestBody MembershipRole membershipRole) throws URISyntaxException {
        log.debug("REST request to save MembershipRole : {}", membershipRole);
        if (membershipRole.getId() != null) {
            throw new BadRequestAlertException("A new membershipRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembershipRole result = membershipRoleRepository.save(membershipRole);
        return ResponseEntity.created(new URI("/api/membership-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /membership-roles} : Updates an existing membershipRole.
     *
     * @param membershipRole the membershipRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membershipRole,
     * or with status {@code 400 (Bad Request)} if the membershipRole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the membershipRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/membership-roles")
    public ResponseEntity<MembershipRole> updateMembershipRole(@RequestBody MembershipRole membershipRole) throws URISyntaxException {
        log.debug("REST request to update MembershipRole : {}", membershipRole);
        if (membershipRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MembershipRole result = membershipRoleRepository.save(membershipRole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, membershipRole.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /membership-roles} : get all the membershipRoles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of membershipRoles in body.
     */
    @GetMapping("/membership-roles")
    public ResponseEntity<List<MembershipRole>> getAllMembershipRoles(Pageable pageable) {
        log.debug("REST request to get a page of MembershipRoles");
        Page<MembershipRole> page = membershipRoleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /membership-roles/:id} : get the "id" membershipRole.
     *
     * @param id the id of the membershipRole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the membershipRole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/membership-roles/{id}")
    public ResponseEntity<MembershipRole> getMembershipRole(@PathVariable Long id) {
        log.debug("REST request to get MembershipRole : {}", id);
        Optional<MembershipRole> membershipRole = membershipRoleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(membershipRole);
    }

    /**
     * {@code DELETE  /membership-roles/:id} : delete the "id" membershipRole.
     *
     * @param id the id of the membershipRole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/membership-roles/{id}")
    public ResponseEntity<Void> deleteMembershipRole(@PathVariable Long id) {
        log.debug("REST request to delete MembershipRole : {}", id);
        membershipRoleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
