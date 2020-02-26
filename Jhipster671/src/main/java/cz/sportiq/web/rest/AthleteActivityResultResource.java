package cz.sportiq.web.rest;

import cz.sportiq.service.AthleteActivityResultService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.service.dto.AthleteActivityResultDTO;

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
 * REST controller for managing {@link cz.sportiq.domain.AthleteActivityResult}.
 */
@RestController
@RequestMapping("/api")
public class AthleteActivityResultResource {

    private final Logger log = LoggerFactory.getLogger(AthleteActivityResultResource.class);

    private static final String ENTITY_NAME = "athleteActivityResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AthleteActivityResultService athleteActivityResultService;

    public AthleteActivityResultResource(AthleteActivityResultService athleteActivityResultService) {
        this.athleteActivityResultService = athleteActivityResultService;
    }

    /**
     * {@code POST  /athlete-activity-results} : Create a new athleteActivityResult.
     *
     * @param athleteActivityResultDTO the athleteActivityResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new athleteActivityResultDTO, or with status {@code 400 (Bad Request)} if the athleteActivityResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/athlete-activity-results")
    public ResponseEntity<AthleteActivityResultDTO> createAthleteActivityResult(@Valid @RequestBody AthleteActivityResultDTO athleteActivityResultDTO) throws URISyntaxException {
        log.debug("REST request to save AthleteActivityResult : {}", athleteActivityResultDTO);
        if (athleteActivityResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new athleteActivityResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AthleteActivityResultDTO result = athleteActivityResultService.save(athleteActivityResultDTO);
        return ResponseEntity.created(new URI("/api/athlete-activity-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /athlete-activity-results} : Updates an existing athleteActivityResult.
     *
     * @param athleteActivityResultDTO the athleteActivityResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated athleteActivityResultDTO,
     * or with status {@code 400 (Bad Request)} if the athleteActivityResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the athleteActivityResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/athlete-activity-results")
    public ResponseEntity<AthleteActivityResultDTO> updateAthleteActivityResult(@Valid @RequestBody AthleteActivityResultDTO athleteActivityResultDTO) throws URISyntaxException {
        log.debug("REST request to update AthleteActivityResult : {}", athleteActivityResultDTO);
        if (athleteActivityResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AthleteActivityResultDTO result = athleteActivityResultService.save(athleteActivityResultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, athleteActivityResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /athlete-activity-results} : get all the athleteActivityResults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of athleteActivityResults in body.
     */
    @GetMapping("/athlete-activity-results")
    public ResponseEntity<List<AthleteActivityResultDTO>> getAllAthleteActivityResults(Pageable pageable) {
        log.debug("REST request to get a page of AthleteActivityResults");
        Page<AthleteActivityResultDTO> page = athleteActivityResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /athlete-activity-results/:id} : get the "id" athleteActivityResult.
     *
     * @param id the id of the athleteActivityResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the athleteActivityResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/athlete-activity-results/{id}")
    public ResponseEntity<AthleteActivityResultDTO> getAthleteActivityResult(@PathVariable Long id) {
        log.debug("REST request to get AthleteActivityResult : {}", id);
        Optional<AthleteActivityResultDTO> athleteActivityResultDTO = athleteActivityResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(athleteActivityResultDTO);
    }

    /**
     * {@code DELETE  /athlete-activity-results/:id} : delete the "id" athleteActivityResult.
     *
     * @param id the id of the athleteActivityResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/athlete-activity-results/{id}")
    public ResponseEntity<Void> deleteAthleteActivityResult(@PathVariable Long id) {
        log.debug("REST request to delete AthleteActivityResult : {}", id);
        athleteActivityResultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
