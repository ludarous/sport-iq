package cz.sportiq.web.rest;

import cz.sportiq.service.AthleteActivityService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.service.dto.AthleteActivityDTO;

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
 * REST controller for managing {@link cz.sportiq.domain.AthleteActivity}.
 */
@RestController
@RequestMapping("/api")
public class AthleteActivityResource {

    private final Logger log = LoggerFactory.getLogger(AthleteActivityResource.class);

    private static final String ENTITY_NAME = "athleteActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AthleteActivityService athleteActivityService;

    public AthleteActivityResource(AthleteActivityService athleteActivityService) {
        this.athleteActivityService = athleteActivityService;
    }

    /**
     * {@code POST  /athlete-activities} : Create a new athleteActivity.
     *
     * @param athleteActivityDTO the athleteActivityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new athleteActivityDTO, or with status {@code 400 (Bad Request)} if the athleteActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/athlete-activities")
    public ResponseEntity<AthleteActivityDTO> createAthleteActivity(@Valid @RequestBody AthleteActivityDTO athleteActivityDTO) throws URISyntaxException {
        log.debug("REST request to save AthleteActivity : {}", athleteActivityDTO);
        if (athleteActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new athleteActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AthleteActivityDTO result = athleteActivityService.save(athleteActivityDTO);
        return ResponseEntity.created(new URI("/api/athlete-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /athlete-activities} : Updates an existing athleteActivity.
     *
     * @param athleteActivityDTO the athleteActivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated athleteActivityDTO,
     * or with status {@code 400 (Bad Request)} if the athleteActivityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the athleteActivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/athlete-activities")
    public ResponseEntity<AthleteActivityDTO> updateAthleteActivity(@Valid @RequestBody AthleteActivityDTO athleteActivityDTO) throws URISyntaxException {
        log.debug("REST request to update AthleteActivity : {}", athleteActivityDTO);
        if (athleteActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AthleteActivityDTO result = athleteActivityService.save(athleteActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, athleteActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /athlete-activities} : get all the athleteActivities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of athleteActivities in body.
     */
    @GetMapping("/athlete-activities")
    public ResponseEntity<List<AthleteActivityDTO>> getAllAthleteActivities(Pageable pageable) {
        log.debug("REST request to get a page of AthleteActivities");
        Page<AthleteActivityDTO> page = athleteActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /athlete-activities/:id} : get the "id" athleteActivity.
     *
     * @param id the id of the athleteActivityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the athleteActivityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/athlete-activities/{id}")
    public ResponseEntity<AthleteActivityDTO> getAthleteActivity(@PathVariable Long id) {
        log.debug("REST request to get AthleteActivity : {}", id);
        Optional<AthleteActivityDTO> athleteActivityDTO = athleteActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(athleteActivityDTO);
    }

    /**
     * {@code DELETE  /athlete-activities/:id} : delete the "id" athleteActivity.
     *
     * @param id the id of the athleteActivityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/athlete-activities/{id}")
    public ResponseEntity<Void> deleteAthleteActivity(@PathVariable Long id) {
        log.debug("REST request to delete AthleteActivity : {}", id);
        athleteActivityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
