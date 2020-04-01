package cz.sportiq.web.rest;

import cz.sportiq.service.AthleteService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.service.dto.AthleteDTO;

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
 * REST controller for managing {@link cz.sportiq.domain.Athlete}.
 */
@RestController
@RequestMapping("/api")
public class AthleteResource {

    private final Logger log = LoggerFactory.getLogger(AthleteResource.class);

    private static final String ENTITY_NAME = "athlete";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AthleteService athleteService;

    public AthleteResource(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    /**
     * {@code POST  /athletes} : Create a new athlete.
     *
     * @param athleteDTO the athleteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new athleteDTO, or with status {@code 400 (Bad Request)} if the athlete has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/athletes")
    public ResponseEntity<AthleteDTO> createAthlete(@Valid @RequestBody AthleteDTO athleteDTO) throws URISyntaxException {
        log.debug("REST request to save Athlete : {}", athleteDTO);
        if (athleteDTO.getId() != null) {
            throw new BadRequestAlertException("A new athlete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AthleteDTO result = athleteService.save(athleteDTO);
        return ResponseEntity.created(new URI("/api/athletes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /athletes} : Updates an existing athlete.
     *
     * @param athleteDTO the athleteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated athleteDTO,
     * or with status {@code 400 (Bad Request)} if the athleteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the athleteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/athletes")
    public ResponseEntity<AthleteDTO> updateAthlete(@Valid @RequestBody AthleteDTO athleteDTO) throws URISyntaxException {
        log.debug("REST request to update Athlete : {}", athleteDTO);
        if (athleteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AthleteDTO result = athleteService.save(athleteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, athleteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /athletes} : get all the athletes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of athletes in body.
     */
    @GetMapping("/athletes")
    public ResponseEntity<List<AthleteDTO>> getAllAthletes(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Athletes");
        Page<AthleteDTO> page;
        if (eagerload) {
            page = athleteService.findAllWithEagerRelationships(pageable);
        } else {
            page = athleteService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /athletes/:id} : get the "id" athlete.
     *
     * @param id the id of the athleteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the athleteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/athletes/{id}")
    public ResponseEntity<AthleteDTO> getAthlete(@PathVariable Long id) {
        log.debug("REST request to get Athlete : {}", id);
        Optional<AthleteDTO> athleteDTO = athleteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(athleteDTO);
    }

    /**
     * {@code DELETE  /athletes/:id} : delete the "id" athlete.
     *
     * @param id the id of the athleteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/athletes/{id}")
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        log.debug("REST request to delete Athlete : {}", id);
        athleteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
