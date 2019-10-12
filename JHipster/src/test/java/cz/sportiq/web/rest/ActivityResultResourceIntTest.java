package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;

import cz.sportiq.domain.ActivityResult;
import cz.sportiq.repository.ActivityResultRepository;
import cz.sportiq.repository.search.ActivityResultSearchRepository;
import cz.sportiq.service.ActivityResultService;
import cz.sportiq.service.dto.ActivityResultDTO;
import cz.sportiq.service.mapper.ActivityResultMapper;
import cz.sportiq.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static cz.sportiq.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cz.sportiq.domain.enumeration.ResultType;
/**
 * Test class for the ActivityResultResource REST controller.
 *
 * @see ActivityResultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SportiqApp.class)
public class ActivityResultResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ResultType DEFAULT_RESULT_TYPE = ResultType.LESS_IS_BETTER;
    private static final ResultType UPDATED_RESULT_TYPE = ResultType.MORE_IS_BETTER;

    private static final Float DEFAULT_RATING_WEIGHT = 1F;
    private static final Float UPDATED_RATING_WEIGHT = 2F;

    @Autowired
    private ActivityResultRepository activityResultRepository;

    @Autowired
    private ActivityResultMapper activityResultMapper;
    
    @Autowired
    private ActivityResultService activityResultService;

    /**
     * This repository is mocked in the cz.sportiq.repository.search test package.
     *
     * @see cz.sportiq.repository.search.ActivityResultSearchRepositoryMockConfiguration
     */
    @Autowired
    private ActivityResultSearchRepository mockActivityResultSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActivityResultMockMvc;

    private ActivityResult activityResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivityResultResource activityResultResource = new ActivityResultResource(activityResultService);
        this.restActivityResultMockMvc = MockMvcBuilders.standaloneSetup(activityResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
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
            .ratingWeight(DEFAULT_RATING_WEIGHT);
        return activityResult;
    }

    @Before
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityResultDTO)))
            .andExpect(status().isCreated());

        // Validate the ActivityResult in the database
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityResult testActivityResult = activityResultList.get(activityResultList.size() - 1);
        assertThat(testActivityResult.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testActivityResult.getResultType()).isEqualTo(DEFAULT_RESULT_TYPE);
        assertThat(testActivityResult.getRatingWeight()).isEqualTo(DEFAULT_RATING_WEIGHT);

        // Validate the ActivityResult in Elasticsearch
        verify(mockActivityResultSearchRepository, times(1)).save(testActivityResult);
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityResult in the database
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeCreate);

        // Validate the ActivityResult in Elasticsearch
        verify(mockActivityResultSearchRepository, times(0)).save(activityResult);
    }

    @Test
    @Transactional
    public void getAllActivityResults() throws Exception {
        // Initialize the database
        activityResultRepository.saveAndFlush(activityResult);

        // Get all the activityResultList
        restActivityResultMockMvc.perform(get("/api/activity-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].resultType").value(hasItem(DEFAULT_RESULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ratingWeight").value(hasItem(DEFAULT_RATING_WEIGHT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getActivityResult() throws Exception {
        // Initialize the database
        activityResultRepository.saveAndFlush(activityResult);

        // Get the activityResult
        restActivityResultMockMvc.perform(get("/api/activity-results/{id}", activityResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activityResult.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.resultType").value(DEFAULT_RESULT_TYPE.toString()))
            .andExpect(jsonPath("$.ratingWeight").value(DEFAULT_RATING_WEIGHT.doubleValue()));
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
            .ratingWeight(UPDATED_RATING_WEIGHT);
        ActivityResultDTO activityResultDTO = activityResultMapper.toDto(updatedActivityResult);

        restActivityResultMockMvc.perform(put("/api/activity-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityResultDTO)))
            .andExpect(status().isOk());

        // Validate the ActivityResult in the database
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeUpdate);
        ActivityResult testActivityResult = activityResultList.get(activityResultList.size() - 1);
        assertThat(testActivityResult.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActivityResult.getResultType()).isEqualTo(UPDATED_RESULT_TYPE);
        assertThat(testActivityResult.getRatingWeight()).isEqualTo(UPDATED_RATING_WEIGHT);

        // Validate the ActivityResult in Elasticsearch
        verify(mockActivityResultSearchRepository, times(1)).save(testActivityResult);
    }

    @Test
    @Transactional
    public void updateNonExistingActivityResult() throws Exception {
        int databaseSizeBeforeUpdate = activityResultRepository.findAll().size();

        // Create the ActivityResult
        ActivityResultDTO activityResultDTO = activityResultMapper.toDto(activityResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityResultMockMvc.perform(put("/api/activity-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityResult in the database
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ActivityResult in Elasticsearch
        verify(mockActivityResultSearchRepository, times(0)).save(activityResult);
    }

    @Test
    @Transactional
    public void deleteActivityResult() throws Exception {
        // Initialize the database
        activityResultRepository.saveAndFlush(activityResult);

        int databaseSizeBeforeDelete = activityResultRepository.findAll().size();

        // Get the activityResult
        restActivityResultMockMvc.perform(delete("/api/activity-results/{id}", activityResult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ActivityResult in Elasticsearch
        verify(mockActivityResultSearchRepository, times(1)).deleteById(activityResult.getId());
    }

    @Test
    @Transactional
    public void searchActivityResult() throws Exception {
        // Initialize the database
        activityResultRepository.saveAndFlush(activityResult);
        when(mockActivityResultSearchRepository.search(queryStringQuery("id:" + activityResult.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(activityResult), PageRequest.of(0, 1), 1));
        // Search the activityResult
        restActivityResultMockMvc.perform(get("/api/_search/activity-results?query=id:" + activityResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].resultType").value(hasItem(DEFAULT_RESULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ratingWeight").value(hasItem(DEFAULT_RATING_WEIGHT.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityResult.class);
        ActivityResult activityResult1 = new ActivityResult();
        activityResult1.setId(1L);
        ActivityResult activityResult2 = new ActivityResult();
        activityResult2.setId(activityResult1.getId());
        assertThat(activityResult1).isEqualTo(activityResult2);
        activityResult2.setId(2L);
        assertThat(activityResult1).isNotEqualTo(activityResult2);
        activityResult1.setId(null);
        assertThat(activityResult1).isNotEqualTo(activityResult2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityResultDTO.class);
        ActivityResultDTO activityResultDTO1 = new ActivityResultDTO();
        activityResultDTO1.setId(1L);
        ActivityResultDTO activityResultDTO2 = new ActivityResultDTO();
        assertThat(activityResultDTO1).isNotEqualTo(activityResultDTO2);
        activityResultDTO2.setId(activityResultDTO1.getId());
        assertThat(activityResultDTO1).isEqualTo(activityResultDTO2);
        activityResultDTO2.setId(2L);
        assertThat(activityResultDTO1).isNotEqualTo(activityResultDTO2);
        activityResultDTO1.setId(null);
        assertThat(activityResultDTO1).isNotEqualTo(activityResultDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(activityResultMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(activityResultMapper.fromId(null)).isNull();
    }
}
