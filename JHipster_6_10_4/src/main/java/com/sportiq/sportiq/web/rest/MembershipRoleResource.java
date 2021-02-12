package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.service.MembershipRoleService;
import com.sportiq.sportiq.web.rest.errors.BadRequestAlertException;
import com.sportiq.sportiq.service.dto.MembershipRoleDTO;

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
 * REST controller for managing {@link com.sportiq.sportiq.domain.MembershipRole}.
 */
@RestController
@RequestMapping("/api")
public class MembershipRoleResource {

    private final Logger log = LoggerFactory.getLogger(MembershipRoleResource.class);

    private static final String ENTITY_NAME = "membershipRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MembershipRoleService membershipRoleService;

    public MembershipRoleResource(MembershipRoleService membershipRoleService) {
        this.membershipRoleService = membershipRoleService;
    }

    /**
     * {@code POST  /membership-roles} : Create a new membershipRole.
     *
     * @param membershipRoleDTO the membershipRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new membershipRoleDTO, or with status {@code 400 (Bad Request)} if the membershipRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/membership-roles")
    public ResponseEntity<MembershipRoleDTO> createMembershipRole(@RequestBody MembershipRoleDTO membershipRoleDTO) throws URISyntaxException {
        log.debug("REST request to save MembershipRole : {}", membershipRoleDTO);
        if (membershipRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new membershipRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembershipRoleDTO result = membershipRoleService.save(membershipRoleDTO);
        return ResponseEntity.created(new URI("/api/membership-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /membership-roles} : Updates an existing membershipRole.
     *
     * @param membershipRoleDTO the membershipRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membershipRoleDTO,
     * or with status {@code 400 (Bad Request)} if the membershipRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the membershipRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/membership-roles")
    public ResponseEntity<MembershipRoleDTO> updateMembershipRole(@RequestBody MembershipRoleDTO membershipRoleDTO) throws URISyntaxException {
        log.debug("REST request to update MembershipRole : {}", membershipRoleDTO);
        if (membershipRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MembershipRoleDTO result = membershipRoleService.save(membershipRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, membershipRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /membership-roles} : get all the membershipRoles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of membershipRoles in body.
     */
    @GetMapping("/membership-roles")
    public ResponseEntity<List<MembershipRoleDTO>> getAllMembershipRoles(Pageable pageable) {
        log.debug("REST request to get a page of MembershipRoles");
        Page<MembershipRoleDTO> page = membershipRoleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /membership-roles/:id} : get the "id" membershipRole.
     *
     * @param id the id of the membershipRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the membershipRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/membership-roles/{id}")
    public ResponseEntity<MembershipRoleDTO> getMembershipRole(@PathVariable Long id) {
        log.debug("REST request to get MembershipRole : {}", id);
        Optional<MembershipRoleDTO> membershipRoleDTO = membershipRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membershipRoleDTO);
    }

    /**
     * {@code DELETE  /membership-roles/:id} : delete the "id" membershipRole.
     *
     * @param id the id of the membershipRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/membership-roles/{id}")
    public ResponseEntity<Void> deleteMembershipRole(@PathVariable Long id) {
        log.debug("REST request to delete MembershipRole : {}", id);
        membershipRoleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
