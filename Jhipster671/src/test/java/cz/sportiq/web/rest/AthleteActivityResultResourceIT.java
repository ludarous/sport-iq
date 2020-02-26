package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;
import cz.sportiq.config.TestSecurityConfiguration;
import cz.sportiq.domain.AthleteActivityResult;
import cz.sportiq.domain.ActivityResult;
import cz.sportiq.repository.AthleteActivityResultRepository;
import cz.sportiq.service.AthleteActivityResultService;
import cz.sportiq.service.dto.AthleteActivityResultDTO;
import cz.sportiq.service.mapper.AthleteActivityResultMapper;
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
 * Integration tests for the {@link AthleteActivityResultResource} REST controller.
 */
@SpringBootTest(classes = {SportiqApp.class, TestSecurityConfiguration.class})
public class AthleteActivityResultResourceIT {

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final Float DEFAULT_COMPARE_VALUE = 1F;
    private static final Float UPDATED_COMPARE_VALUE = 2F;

    @Autowired
    private AthleteActivityResultRepository athleteActivityResultRepository;

    @Autowired
    private AthleteActivityResultMapper athleteActivityResultMapper;

    @Autowired
    private AthleteActivityResultService athleteActivityResultService;

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

    private MockMvc restAthleteActivityResultMockMvc;

    private AthleteActivityResult athleteActivityResult;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AthleteActivityResultResource athleteActivityResultResource = new AthleteActivityResultResource(athleteActivityResultService);
        this.restAthleteActivityResultMockMvc = MockMvcBuilders.standaloneSetup(athleteActivityResultResource)
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
    public static AthleteActivityResult createEntity(EntityManager em) {
        AthleteActivityResult athleteActivityResult = new AthleteActivityResult()
            .value(DEFAULT_VALUE)
            .compareValue(DEFAULT_COMPARE_VALUE);
        // Add required entity
        ActivityResult activityResult;
        if (TestUtil.findAll(em, ActivityResult.class).isEmpty()) {
            activityResult = ActivityResultResourceIT.createEntity(em);
            em.persist(activityResult);
            em.flush();
        } else {
            activityResult = TestUtil.findAll(em, ActivityResult.class).get(0);
        }
        athleteActivityResult.setActivityResult(activityResult);
        return athleteActivityResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AthleteActivityResult createUpdatedEntity(EntityManager em) {
        AthleteActivityResult athleteActivityResult = new AthleteActivityResult()
            .value(UPDATED_VALUE)
            .compareValue(UPDATED_COMPARE_VALUE);
        // Add required entity
        ActivityResult activityResult;
        if (TestUtil.findAll(em, ActivityResult.class).isEmpty()) {
            activityResult = ActivityResultResourceIT.createUpdatedEntity(em);
            em.persist(activityResult);
            em.flush();
        } else {
            activityResult = TestUtil.findAll(em, ActivityResult.class).get(0);
        }
        athleteActivityResult.setActivityResult(activityResult);
        return athleteActivityResult;
    }

    @BeforeEach
    public void initTest() {
        athleteActivityResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createAthleteActivityResult() throws Exception {
        int databaseSizeBeforeCreate = athleteActivityResultRepository.findAll().size();

        // Create the AthleteActivityResult
        AthleteActivityResultDTO athleteActivityResultDTO = athleteActivityResultMapper.toDto(athleteActivityResult);
        restAthleteActivityResultMockMvc.perform(post("/api/athlete-activity-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityResultDTO)))
            .andExpect(status().isCreated());

        // Validate the AthleteActivityResult in the database
        List<AthleteActivityResult> athleteActivityResultList = athleteActivityResultRepository.findAll();
        assertThat(athleteActivityResultList).hasSize(databaseSizeBeforeCreate + 1);
        AthleteActivityResult testAthleteActivityResult = athleteActivityResultList.get(athleteActivityResultList.size() - 1);
        assertThat(testAthleteActivityResult.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAthleteActivityResult.getCompareValue()).isEqualTo(DEFAULT_COMPARE_VALUE);
    }

    @Test
    @Transactional
    public void createAthleteActivityResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = athleteActivityResultRepository.findAll().size();

        // Create the AthleteActivityResult with an existing ID
        athleteActivityResult.setId(1L);
        AthleteActivityResultDTO athleteActivityResultDTO = athleteActivityResultMapper.toDto(athleteActivityResult);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAthleteActivityResultMockMvc.perform(post("/api/athlete-activity-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteActivityResult in the database
        List<AthleteActivityResult> athleteActivityResultList = athleteActivityResultRepository.findAll();
        assertThat(athleteActivityResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAthleteActivityResults() throws Exception {
        // Initialize the database
        athleteActivityResultRepository.saveAndFlush(athleteActivityResult);

        // Get all the athleteActivityResultList
        restAthleteActivityResultMockMvc.perform(get("/api/athlete-activity-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(athleteActivityResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].compareValue").value(hasItem(DEFAULT_COMPARE_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAthleteActivityResult() throws Exception {
        // Initialize the database
        athleteActivityResultRepository.saveAndFlush(athleteActivityResult);

        // Get the athleteActivityResult
        restAthleteActivityResultMockMvc.perform(get("/api/athlete-activity-results/{id}", athleteActivityResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(athleteActivityResult.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.compareValue").value(DEFAULT_COMPARE_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAthleteActivityResult() throws Exception {
        // Get the athleteActivityResult
        restAthleteActivityResultMockMvc.perform(get("/api/athlete-activity-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAthleteActivityResult() throws Exception {
        // Initialize the database
        athleteActivityResultRepository.saveAndFlush(athleteActivityResult);

        int databaseSizeBeforeUpdate = athleteActivityResultRepository.findAll().size();

        // Update the athleteActivityResult
        AthleteActivityResult updatedAthleteActivityResult = athleteActivityResultRepository.findById(athleteActivityResult.getId()).get();
        // Disconnect from session so that the updates on updatedAthleteActivityResult are not directly saved in db
        em.detach(updatedAthleteActivityResult);
        updatedAthleteActivityResult
            .value(UPDATED_VALUE)
            .compareValue(UPDATED_COMPARE_VALUE);
        AthleteActivityResultDTO athleteActivityResultDTO = athleteActivityResultMapper.toDto(updatedAthleteActivityResult);

        restAthleteActivityResultMockMvc.perform(put("/api/athlete-activity-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityResultDTO)))
            .andExpect(status().isOk());

        // Validate the AthleteActivityResult in the database
        List<AthleteActivityResult> athleteActivityResultList = athleteActivityResultRepository.findAll();
        assertThat(athleteActivityResultList).hasSize(databaseSizeBeforeUpdate);
        AthleteActivityResult testAthleteActivityResult = athleteActivityResultList.get(athleteActivityResultList.size() - 1);
        assertThat(testAthleteActivityResult.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAthleteActivityResult.getCompareValue()).isEqualTo(UPDATED_COMPARE_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingAthleteActivityResult() throws Exception {
        int databaseSizeBeforeUpdate = athleteActivityResultRepository.findAll().size();

        // Create the AthleteActivityResult
        AthleteActivityResultDTO athleteActivityResultDTO = athleteActivityResultMapper.toDto(athleteActivityResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAthleteActivityResultMockMvc.perform(put("/api/athlete-activity-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteActivityResult in the database
        List<AthleteActivityResult> athleteActivityResultList = athleteActivityResultRepository.findAll();
        assertThat(athleteActivityResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAthleteActivityResult() throws Exception {
        // Initialize the database
        athleteActivityResultRepository.saveAndFlush(athleteActivityResult);

        int databaseSizeBeforeDelete = athleteActivityResultRepository.findAll().size();

        // Delete the athleteActivityResult
        restAthleteActivityResultMockMvc.perform(delete("/api/athlete-activity-results/{id}", athleteActivityResult.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AthleteActivityResult> athleteActivityResultList = athleteActivityResultRepository.findAll();
        assertThat(athleteActivityResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
