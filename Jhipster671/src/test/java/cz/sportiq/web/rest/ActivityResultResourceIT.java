package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;
import cz.sportiq.config.TestSecurityConfiguration;
import cz.sportiq.domain.ActivityResult;
import cz.sportiq.repository.ActivityResultRepository;
import cz.sportiq.service.ActivityResultService;
import cz.sportiq.service.dto.ActivityResultDTO;
import cz.sportiq.service.mapper.ActivityResultMapper;
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

import cz.sportiq.domain.enumeration.ResultType;
/**
 * Integration tests for the {@link ActivityResultResource} REST controller.
 */
@SpringBootTest(classes = {SportiqApp.class, TestSecurityConfiguration.class})
public class ActivityResultResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ResultType DEFAULT_RESULT_TYPE = ResultType.LESS_IS_BETTER;
    private static final ResultType UPDATED_RESULT_TYPE = ResultType.MORE_IS_BETTER;

    private static final Float DEFAULT_RATING_WEIGHT = 1F;
    private static final Float UPDATED_RATING_WEIGHT = 2F;

    private static final Boolean DEFAULT_MAIN_RESULT = false;
    private static final Boolean UPDATED_MAIN_RESULT = true;

    @Autowired
    private ActivityResultRepository activityResultRepository;

    @Autowired
    private ActivityResultMapper activityResultMapper;

    @Autowired
    private ActivityResultService activityResultService;

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

    private MockMvc restActivityResultMockMvc;

    private ActivityResult activityResult;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivityResultResource activityResultResource = new ActivityResultResource(activityResultService);
        this.restActivityResultMockMvc = MockMvcBuilders.standaloneSetup(activityResultResource)
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
    public static ActivityResult createEntity(EntityManager em) {
        ActivityResult activityResult = new ActivityResult()
            .name(DEFAULT_NAME)
            .resultType(DEFAULT_RESULT_TYPE)
            .ratingWeight(DEFAULT_RATING_WEIGHT)
            .mainResult(DEFAULT_MAIN_RESULT);
        return activityResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityResult createUpdatedEntity(EntityManager em) {
        ActivityResult activityResult = new ActivityResult()
            .name(UPDATED_NAME)
            .resultType(UPDATED_RESULT_TYPE)
            .ratingWeight(UPDATED_RATING_WEIGHT)
            .mainResult(UPDATED_MAIN_RESULT);
        return activityResult;
    }

    @BeforeEach
    public void initTest() {
        activityResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivityResult() throws Exception {
        int databaseSizeBeforeCreate = activityResultRepository.findAll().size();

        // Create the ActivityResult
        ActivityResultDTO activityResultDTO = activityResultMapper.toDto(activityResult);
        restActivityResultMockMvc.perform(post("/api/activity-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityResultDTO)))
            .andExpect(status().isCreated());

        // Validate the ActivityResult in the database
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityResult testActivityResult = activityResultList.get(activityResultList.size() - 1);
        assertThat(testActivityResult.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testActivityResult.getResultType()).isEqualTo(DEFAULT_RESULT_TYPE);
        assertThat(testActivityResult.getRatingWeight()).isEqualTo(DEFAULT_RATING_WEIGHT);
        assertThat(testActivityResult.isMainResult()).isEqualTo(DEFAULT_MAIN_RESULT);
    }

    @Test
    @Transactional
    public void createActivityResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityResultRepository.findAll().size();

        // Create the ActivityResult with an existing ID
        activityResult.setId(1L);
        ActivityResultDTO activityResultDTO = activityResultMapper.toDto(activityResult);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityResultMockMvc.perform(post("/api/activity-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityResult in the database
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllActivityResults() throws Exception {
        // Initialize the database
        activityResultRepository.saveAndFlush(activityResult);

        // Get all the activityResultList
        restActivityResultMockMvc.perform(get("/api/activity-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].resultType").value(hasItem(DEFAULT_RESULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ratingWeight").value(hasItem(DEFAULT_RATING_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].mainResult").value(hasItem(DEFAULT_MAIN_RESULT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getActivityResult() throws Exception {
        // Initialize the database
        activityResultRepository.saveAndFlush(activityResult);

        // Get the activityResult
        restActivityResultMockMvc.perform(get("/api/activity-results/{id}", activityResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activityResult.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.resultType").value(DEFAULT_RESULT_TYPE.toString()))
            .andExpect(jsonPath("$.ratingWeight").value(DEFAULT_RATING_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.mainResult").value(DEFAULT_MAIN_RESULT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingActivityResult() throws Exception {
        // Get the activityResult
        restActivityResultMockMvc.perform(get("/api/activity-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivityResult() throws Exception {
        // Initialize the database
        activityResultRepository.saveAndFlush(activityResult);

        int databaseSizeBeforeUpdate = activityResultRepository.findAll().size();

        // Update the activityResult
        ActivityResult updatedActivityResult = activityResultRepository.findById(activityResult.getId()).get();
        // Disconnect from session so that the updates on updatedActivityResult are not directly saved in db
        em.detach(updatedActivityResult);
        updatedActivityResult
            .name(UPDATED_NAME)
            .resultType(UPDATED_RESULT_TYPE)
            .ratingWeight(UPDATED_RATING_WEIGHT)
            .mainResult(UPDATED_MAIN_RESULT);
        ActivityResultDTO activityResultDTO = activityResultMapper.toDto(updatedActivityResult);

        restActivityResultMockMvc.perform(put("/api/activity-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityResultDTO)))
            .andExpect(status().isOk());

        // Validate the ActivityResult in the database
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeUpdate);
        ActivityResult testActivityResult = activityResultList.get(activityResultList.size() - 1);
        assertThat(testActivityResult.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActivityResult.getResultType()).isEqualTo(UPDATED_RESULT_TYPE);
        assertThat(testActivityResult.getRatingWeight()).isEqualTo(UPDATED_RATING_WEIGHT);
        assertThat(testActivityResult.isMainResult()).isEqualTo(UPDATED_MAIN_RESULT);
    }

    @Test
    @Transactional
    public void updateNonExistingActivityResult() throws Exception {
        int databaseSizeBeforeUpdate = activityResultRepository.findAll().size();

        // Create the ActivityResult
        ActivityResultDTO activityResultDTO = activityResultMapper.toDto(activityResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityResultMockMvc.perform(put("/api/activity-results")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityResult in the database
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActivityResult() throws Exception {
        // Initialize the database
        activityResultRepository.saveAndFlush(activityResult);

        int databaseSizeBeforeDelete = activityResultRepository.findAll().size();

        // Delete the activityResult
        restActivityResultMockMvc.perform(delete("/api/activity-results/{id}", activityResult.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
