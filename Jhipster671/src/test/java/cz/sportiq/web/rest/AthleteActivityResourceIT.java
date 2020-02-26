package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;
import cz.sportiq.config.TestSecurityConfiguration;
import cz.sportiq.domain.AthleteActivity;
import cz.sportiq.domain.Activity;
import cz.sportiq.repository.AthleteActivityRepository;
import cz.sportiq.service.AthleteActivityService;
import cz.sportiq.service.dto.AthleteActivityDTO;
import cz.sportiq.service.mapper.AthleteActivityMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static cz.sportiq.web.rest.TestUtil.sameInstant;
import static cz.sportiq.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AthleteActivityResource} REST controller.
 */
@SpringBootTest(classes = {SportiqApp.class, TestSecurityConfiguration.class})
public class AthleteActivityResourceIT {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AthleteActivityRepository athleteActivityRepository;

    @Autowired
    private AthleteActivityMapper athleteActivityMapper;

    @Autowired
    private AthleteActivityService athleteActivityService;

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

    private MockMvc restAthleteActivityMockMvc;

    private AthleteActivity athleteActivity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AthleteActivityResource athleteActivityResource = new AthleteActivityResource(athleteActivityService);
        this.restAthleteActivityMockMvc = MockMvcBuilders.standaloneSetup(athleteActivityResource)
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
    public static AthleteActivity createEntity(EntityManager em) {
        AthleteActivity athleteActivity = new AthleteActivity()
            .note(DEFAULT_NOTE)
            .date(DEFAULT_DATE);
        // Add required entity
        Activity activity;
        if (TestUtil.findAll(em, Activity.class).isEmpty()) {
            activity = ActivityResourceIT.createEntity(em);
            em.persist(activity);
            em.flush();
        } else {
            activity = TestUtil.findAll(em, Activity.class).get(0);
        }
        athleteActivity.setActivity(activity);
        return athleteActivity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AthleteActivity createUpdatedEntity(EntityManager em) {
        AthleteActivity athleteActivity = new AthleteActivity()
            .note(UPDATED_NOTE)
            .date(UPDATED_DATE);
        // Add required entity
        Activity activity;
        if (TestUtil.findAll(em, Activity.class).isEmpty()) {
            activity = ActivityResourceIT.createUpdatedEntity(em);
            em.persist(activity);
            em.flush();
        } else {
            activity = TestUtil.findAll(em, Activity.class).get(0);
        }
        athleteActivity.setActivity(activity);
        return athleteActivity;
    }

    @BeforeEach
    public void initTest() {
        athleteActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createAthleteActivity() throws Exception {
        int databaseSizeBeforeCreate = athleteActivityRepository.findAll().size();

        // Create the AthleteActivity
        AthleteActivityDTO athleteActivityDTO = athleteActivityMapper.toDto(athleteActivity);
        restAthleteActivityMockMvc.perform(post("/api/athlete-activities")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the AthleteActivity in the database
        List<AthleteActivity> athleteActivityList = athleteActivityRepository.findAll();
        assertThat(athleteActivityList).hasSize(databaseSizeBeforeCreate + 1);
        AthleteActivity testAthleteActivity = athleteActivityList.get(athleteActivityList.size() - 1);
        assertThat(testAthleteActivity.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testAthleteActivity.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createAthleteActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = athleteActivityRepository.findAll().size();

        // Create the AthleteActivity with an existing ID
        athleteActivity.setId(1L);
        AthleteActivityDTO athleteActivityDTO = athleteActivityMapper.toDto(athleteActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAthleteActivityMockMvc.perform(post("/api/athlete-activities")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteActivity in the database
        List<AthleteActivity> athleteActivityList = athleteActivityRepository.findAll();
        assertThat(athleteActivityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAthleteActivities() throws Exception {
        // Initialize the database
        athleteActivityRepository.saveAndFlush(athleteActivity);

        // Get all the athleteActivityList
        restAthleteActivityMockMvc.perform(get("/api/athlete-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(athleteActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }
    
    @Test
    @Transactional
    public void getAthleteActivity() throws Exception {
        // Initialize the database
        athleteActivityRepository.saveAndFlush(athleteActivity);

        // Get the athleteActivity
        restAthleteActivityMockMvc.perform(get("/api/athlete-activities/{id}", athleteActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(athleteActivity.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingAthleteActivity() throws Exception {
        // Get the athleteActivity
        restAthleteActivityMockMvc.perform(get("/api/athlete-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAthleteActivity() throws Exception {
        // Initialize the database
        athleteActivityRepository.saveAndFlush(athleteActivity);

        int databaseSizeBeforeUpdate = athleteActivityRepository.findAll().size();

        // Update the athleteActivity
        AthleteActivity updatedAthleteActivity = athleteActivityRepository.findById(athleteActivity.getId()).get();
        // Disconnect from session so that the updates on updatedAthleteActivity are not directly saved in db
        em.detach(updatedAthleteActivity);
        updatedAthleteActivity
            .note(UPDATED_NOTE)
            .date(UPDATED_DATE);
        AthleteActivityDTO athleteActivityDTO = athleteActivityMapper.toDto(updatedAthleteActivity);

        restAthleteActivityMockMvc.perform(put("/api/athlete-activities")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityDTO)))
            .andExpect(status().isOk());

        // Validate the AthleteActivity in the database
        List<AthleteActivity> athleteActivityList = athleteActivityRepository.findAll();
        assertThat(athleteActivityList).hasSize(databaseSizeBeforeUpdate);
        AthleteActivity testAthleteActivity = athleteActivityList.get(athleteActivityList.size() - 1);
        assertThat(testAthleteActivity.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testAthleteActivity.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAthleteActivity() throws Exception {
        int databaseSizeBeforeUpdate = athleteActivityRepository.findAll().size();

        // Create the AthleteActivity
        AthleteActivityDTO athleteActivityDTO = athleteActivityMapper.toDto(athleteActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAthleteActivityMockMvc.perform(put("/api/athlete-activities")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteActivity in the database
        List<AthleteActivity> athleteActivityList = athleteActivityRepository.findAll();
        assertThat(athleteActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAthleteActivity() throws Exception {
        // Initialize the database
        athleteActivityRepository.saveAndFlush(athleteActivity);

        int databaseSizeBeforeDelete = athleteActivityRepository.findAll().size();

        // Delete the athleteActivity
        restAthleteActivityMockMvc.perform(delete("/api/athlete-activities/{id}", athleteActivity.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AthleteActivity> athleteActivityList = athleteActivityRepository.findAll();
        assertThat(athleteActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
