package cz.sportiq.web.rest;

import cz.sportiq.service.AthleteActivityResultSplitService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.service.dto.AthleteActivityResultSplitDTO;

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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link cz.sportiq.domain.AthleteActivityResultSplit}.
 */
@RestController
@RequestMapping("/api")
public class AthleteActivityResultSplitResource {

    private final Logger log = LoggerFactory.getLogger(AthleteActivityResultSplitResource.class);

    private static final String ENTITY_NAME = "athleteActivityResultSplit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AthleteActivityResultSplitService athleteActivityResultSplitService;

    public AthleteActivityResultSplitResource(AthleteActivityResultSplitService athleteActivityResultSplitService) {
        this.athleteActivityResultSplitService = athleteActivityResultSplitService;
    }

    /**
     * {@code POST  /athlete-activity-result-splits} : Create a new athleteActivityResultSplit.
     *
     * @param athleteActivityResultSplitDTO the athleteActivityResultSplitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new athleteActivityResultSplitDTO, or with status {@code 400 (Bad Request)} if the athleteActivityResultSplit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/athlete-activity-result-splits")
    public ResponseEntity<AthleteActivityResultSplitDTO> createAthleteActivityResultSplit(@Valid @RequestBody AthleteActivityResultSplitDTO athleteActivityResultSplitDTO) throws URISyntaxException {
        log.debug("REST request to save AthleteActivityResultSplit : {}", athleteActivityResultSplitDTO);
        if (athleteActivityResultSplitDTO.getId() != null) {
            throw new BadRequestAlertException("A new athleteActivityResultSplit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AthleteActivityResultSplitDTO result = athleteActivityResultSplitService.save(athleteActivityResultSplitDTO);
        return ResponseEntity.created(new URI("/api/athlete-activity-result-splits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /athlete-activity-result-splits} : Updates an existing athleteActivityResultSplit.
     *
     * @param athleteActivityResultSplitDTO the athleteActivityResultSplitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated athleteActivityResultSplitDTO,
     * or with status {@code 400 (Bad Request)} if the athleteActivityResultSplitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the athleteActivityResultSplitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/athlete-activity-result-splits")
    public ResponseEntity<AthleteActivityResultSplitDTO> updateAthleteActivityResultSplit(@Valid @RequestBody AthleteActivityResultSplitDTO athleteActivityResultSplitDTO) throws URISyntaxException {
        log.debug("REST request to update AthleteActivityResultSplit : {}", athleteActivityResultSplitDTO);
        if (athleteActivityResultSplitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AthleteActivityResultSplitDTO result = athleteActivityResultSplitService.save(athleteActivityResultSplitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, athleteActivityResultSplitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /athlete-activity-result-splits} : get all the athleteActivityResultSplits.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of athleteActivityResultSplits in body.
     */
    @GetMapping("/athlete-activity-result-splits")
    public ResponseEntity<List<AthleteActivityResultSplitDTO>> getAllAthleteActivityResultSplits(Pageable pageable) {
        log.debug("REST request to get a page of AthleteActivityResultSplits");
        Page<AthleteActivityResultSplitDTO> page = athleteActivityResultSplitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /athlete-activity-result-splits/:id} : get the "id" athleteActivityResultSplit.
     *
     * @param id the id of the athleteActivityResultSplitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the athleteActivityResultSplitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/athlete-activity-result-splits/{id}")
    public ResponseEntity<AthleteActivityResultSplitDTO> getAthleteActivityResultSplit(@PathVariable Long id) {
        log.debug("REST request to get AthleteActivityResultSplit : {}", id);
        Optional<AthleteActivityResultSplitDTO> athleteActivityResultSplitDTO = athleteActivityResultSplitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(athleteActivityResultSplitDTO);
    }

    /**
     * {@code DELETE  /athlete-activity-result-splits/:id} : delete the "id" athleteActivityResultSplit.
     *
     * @param id the id of the athleteActivityResultSplitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/athlete-activity-result-splits/{id}")
    public ResponseEntity<Void> deleteAthleteActivityResultSplit(@PathVariable Long id) {
        log.debug("REST request to delete AthleteActivityResultSplit : {}", id);
        athleteActivityResultSplitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/athlete-activity-result-splits?query=:query} : search for the athleteActivityResultSplit corresponding
     * to the query.
     *
     * @param query the query of the athleteActivityResultSplit search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/athlete-activity-result-splits")
    public ResponseEntity<List<AthleteActivityResultSplitDTO>> searchAthleteActivityResultSplits(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AthleteActivityResultSplits for query {}", query);
        Page<AthleteActivityResultSplitDTO> page = athleteActivityResultSplitService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
