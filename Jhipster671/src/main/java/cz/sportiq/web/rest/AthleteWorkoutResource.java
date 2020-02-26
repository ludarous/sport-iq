package cz.sportiq.web.rest;

import cz.sportiq.service.AthleteWorkoutService;
import cz.sportiq.web.rest.errors.BadRequestAlertException;
import cz.sportiq.service.dto.AthleteWorkoutDTO;

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
 * REST controller for managing {@link cz.sportiq.domain.AthleteWorkout}.
 */
@RestController
@RequestMapping("/api")
public class AthleteWorkoutResource {

    private final Logger log = LoggerFactory.getLogger(AthleteWorkoutResource.class);

    private static final String ENTITY_NAME = "athleteWorkout";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AthleteWorkoutService athleteWorkoutService;

    public AthleteWorkoutResource(AthleteWorkoutService athleteWorkoutService) {
        this.athleteWorkoutService = athleteWorkoutService;
    }

    /**
     * {@code POST  /athlete-workouts} : Create a new athleteWorkout.
     *
     * @param athleteWorkoutDTO the athleteWorkoutDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new athleteWorkoutDTO, or with status {@code 400 (Bad Request)} if the athleteWorkout has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/athlete-workouts")
    public ResponseEntity<AthleteWorkoutDTO> createAthleteWorkout(@Valid @RequestBody AthleteWorkoutDTO athleteWorkoutDTO) throws URISyntaxException {
        log.debug("REST request to save AthleteWorkout : {}", athleteWorkoutDTO);
        if (athleteWorkoutDTO.getId() != null) {
            throw new BadRequestAlertException("A new athleteWorkout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AthleteWorkoutDTO result = athleteWorkoutService.save(athleteWorkoutDTO);
        return ResponseEntity.created(new URI("/api/athlete-workouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /athlete-workouts} : Updates an existing athleteWorkout.
     *
     * @param athleteWorkoutDTO the athleteWorkoutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated athleteWorkoutDTO,
     * or with status {@code 400 (Bad Request)} if the athleteWorkoutDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the athleteWorkoutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/athlete-workouts")
    public ResponseEntity<AthleteWorkoutDTO> updateAthleteWorkout(@Valid @RequestBody AthleteWorkoutDTO athleteWorkoutDTO) throws URISyntaxException {
        log.debug("REST request to update AthleteWorkout : {}", athleteWorkoutDTO);
        if (athleteWorkoutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AthleteWorkoutDTO result = athleteWorkoutService.save(athleteWorkoutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, athleteWorkoutDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /athlete-workouts} : get all the athleteWorkouts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of athleteWorkouts in body.
     */
    @GetMapping("/athlete-workouts")
    public ResponseEntity<List<AthleteWorkoutDTO>> getAllAthleteWorkouts(Pageable pageable) {
        log.debug("REST request to get a page of AthleteWorkouts");
        Page<AthleteWorkoutDTO> page = athleteWorkoutService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /athlete-workouts/:id} : get the "id" athleteWorkout.
     *
     * @param id the id of the athleteWorkoutDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the athleteWorkoutDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/athlete-workouts/{id}")
    public ResponseEntity<AthleteWorkoutDTO> getAthleteWorkout(@PathVariable Long id) {
        log.debug("REST request to get AthleteWorkout : {}", id);
        Optional<AthleteWorkoutDTO> athleteWorkoutDTO = athleteWorkoutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(athleteWorkoutDTO);
    }

    /**
     * {@code DELETE  /athlete-workouts/:id} : delete the "id" athleteWorkout.
     *
     * @param id the id of the athleteWorkoutDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/athlete-workouts/{id}")
    public ResponseEntity<Void> deleteAthleteWorkout(@PathVariable Long id) {
        log.debug("REST request to delete AthleteWorkout : {}", id);
        athleteWorkoutService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
