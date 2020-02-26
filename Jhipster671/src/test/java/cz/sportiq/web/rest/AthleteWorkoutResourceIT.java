package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;
import cz.sportiq.config.TestSecurityConfiguration;
import cz.sportiq.domain.AthleteWorkout;
import cz.sportiq.domain.Workout;
import cz.sportiq.repository.AthleteWorkoutRepository;
import cz.sportiq.service.AthleteWorkoutService;
import cz.sportiq.service.dto.AthleteWorkoutDTO;
import cz.sportiq.service.mapper.AthleteWorkoutMapper;
import cz.sportiq.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static cz.sportiq.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AthleteWorkoutResource} REST controller.
 */
@SpringBootTest(classes = {SportiqApp.class, TestSecurityConfiguration.class})
public class AthleteWorkoutResourceIT {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private AthleteWorkoutRepository athleteWorkoutRepository;

    @Autowired
    private AthleteWorkoutMapper athleteWorkoutMapper;

    @Autowired
    private AthleteWorkoutService athleteWorkoutService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAthleteWorkoutMockMvc;

    private AthleteWorkout athleteWorkout;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AthleteWorkoutResource athleteWorkoutResource = new AthleteWorkoutResource(athleteWorkoutService);
        this.restAthleteWorkoutMockMvc = MockMvcBuilders.standaloneSetup(athleteWorkoutResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AthleteWorkout createEntity(EntityManager em) {
        AthleteWorkout athleteWorkout = new AthleteWorkout()
            .note(DEFAULT_NOTE);
        // Add required entity
        Workout workout;
        if (TestUtil.findAll(em, Workout.class).isEmpty()) {
            workout = WorkoutResourceIT.createEntity(em);
            em.persist(workout);
            em.flush();
        } else {
            workout = TestUtil.findAll(em, Workout.class).get(0);
        }
        athleteWorkout.setWorkout(workout);
        return athleteWorkout;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AthleteWorkout createUpdatedEntity(EntityManager em) {
        AthleteWorkout athleteWorkout = new AthleteWorkout()
            .note(UPDATED_NOTE);
        // Add required entity
        Workout workout;
        if (TestUtil.findAll(em, Workout.class).isEmpty()) {
            workout = WorkoutResourceIT.createUpdatedEntity(em);
            em.persist(workout);
            em.flush();
        } else {
            workout = TestUtil.findAll(em, Workout.class).get(0);
        }
        athleteWorkout.setWorkout(workout);
        return athleteWorkout;
    }

    @BeforeEach
    public void initTest() {
        athleteWorkout = createEntity(em);
    }

    @Test
    @Transactional
    public void createAthleteWorkout() throws Exception {
        int databaseSizeBeforeCreate = athleteWorkoutRepository.findAll().size();

        // Create the AthleteWorkout
        AthleteWorkoutDTO athleteWorkoutDTO = athleteWorkoutMapper.toDto(athleteWorkout);
        restAthleteWorkoutMockMvc.perform(post("/api/athlete-workouts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteWorkoutDTO)))
            .andExpect(status().isCreated());

        // Validate the AthleteWorkout in the database
        List<AthleteWorkout> athleteWorkoutList = athleteWorkoutRepository.findAll();
        assertThat(athleteWorkoutList).hasSize(databaseSizeBeforeCreate + 1);
        AthleteWorkout testAthleteWorkout = athleteWorkoutList.get(athleteWorkoutList.size() - 1);
        assertThat(testAthleteWorkout.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createAthleteWorkoutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = athleteWorkoutRepository.findAll().size();

        // Create the AthleteWorkout with an existing ID
        athleteWorkout.setId(1L);
        AthleteWorkoutDTO athleteWorkoutDTO = athleteWorkoutMapper.toDto(athleteWorkout);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAthleteWorkoutMockMvc.perform(post("/api/athlete-workouts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteWorkoutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteWorkout in the database
        List<AthleteWorkout> athleteWorkoutList = athleteWorkoutRepository.findAll();
        assertThat(athleteWorkoutList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAthleteWorkouts() throws Exception {
        // Initialize the database
        athleteWorkoutRepository.saveAndFlush(athleteWorkout);

        // Get all the athleteWorkoutList
        restAthleteWorkoutMockMvc.perform(get("/api/athlete-workouts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(athleteWorkout.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getAthleteWorkout() throws Exception {
        // Initialize the database
        athleteWorkoutRepository.saveAndFlush(athleteWorkout);

        // Get the athleteWorkout
        restAthleteWorkoutMockMvc.perform(get("/api/athlete-workouts/{id}", athleteWorkout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(athleteWorkout.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    public void getNonExistingAthleteWorkout() throws Exception {
        // Get the athleteWorkout
        restAthleteWorkoutMockMvc.perform(get("/api/athlete-workouts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAthleteWorkout() throws Exception {
        // Initialize the database
        athleteWorkoutRepository.saveAndFlush(athleteWorkout);

        int databaseSizeBeforeUpdate = athleteWorkoutRepository.findAll().size();

        // Update the athleteWorkout
        AthleteWorkout updatedAthleteWorkout = athleteWorkoutRepository.findById(athleteWorkout.getId()).get();
        // Disconnect from session so that the updates on updatedAthleteWorkout are not directly saved in db
        em.detach(updatedAthleteWorkout);
        updatedAthleteWorkout
            .note(UPDATED_NOTE);
        AthleteWorkoutDTO athleteWorkoutDTO = athleteWorkoutMapper.toDto(updatedAthleteWorkout);

        restAthleteWorkoutMockMvc.perform(put("/api/athlete-workouts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteWorkoutDTO)))
            .andExpect(status().isOk());

        // Validate the AthleteWorkout in the database
        List<AthleteWorkout> athleteWorkoutList = athleteWorkoutRepository.findAll();
        assertThat(athleteWorkoutList).hasSize(databaseSizeBeforeUpdate);
        AthleteWorkout testAthleteWorkout = athleteWorkoutList.get(athleteWorkoutList.size() - 1);
        assertThat(testAthleteWorkout.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingAthleteWorkout() throws Exception {
        int databaseSizeBeforeUpdate = athleteWorkoutRepository.findAll().size();

        // Create the AthleteWorkout
        AthleteWorkoutDTO athleteWorkoutDTO = athleteWorkoutMapper.toDto(athleteWorkout);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAthleteWorkoutMockMvc.perform(put("/api/athlete-workouts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteWorkoutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteWorkout in the database
        List<AthleteWorkout> athleteWorkoutList = athleteWorkoutRepository.findAll();
        assertThat(athleteWorkoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAthleteWorkout() throws Exception {
        // Initialize the database
        athleteWorkoutRepository.saveAndFlush(athleteWorkout);

        int databaseSizeBeforeDelete = athleteWorkoutRepository.findAll().size();

        // Delete the athleteWorkout
        restAthleteWorkoutMockMvc.perform(delete("/api/athlete-workouts/{id}", athleteWorkout.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AthleteWorkout> athleteWorkoutList = athleteWorkoutRepository.findAll();
        assertThat(athleteWorkoutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
