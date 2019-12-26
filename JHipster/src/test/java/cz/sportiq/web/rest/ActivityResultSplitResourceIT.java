package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;
import cz.sportiq.config.TestSecurityConfiguration;
import cz.sportiq.domain.ActivityResultSplit;
import cz.sportiq.repository.ActivityResultSplitRepository;
import cz.sportiq.repository.search.ActivityResultSplitSearchRepository;
import cz.sportiq.service.ActivityResultSplitService;
import cz.sportiq.service.dto.ActivityResultSplitDTO;
import cz.sportiq.service.mapper.ActivityResultSplitMapper;
import cz.sportiq.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

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

/**
 * Integration tests for the {@link ActivityResultSplitResource} REST controller.
 */
@SpringBootTest(classes = {SportiqApp.class, TestSecurityConfiguration.class})
public class ActivityResultSplitResourceIT {

    private static final Float DEFAULT_SPLIT_VALUE = 1F;
    private static final Float UPDATED_SPLIT_VALUE = 2F;

    @Autowired
    private ActivityResultSplitRepository activityResultSplitRepository;

    @Autowired
    private ActivityResultSplitMapper activityResultSplitMapper;

    @Autowired
    private ActivityResultSplitService activityResultSplitService;

    /**
     * This repository is mocked in the cz.sportiq.repository.search test package.
     *
     * @see cz.sportiq.repository.search.ActivityResultSplitSearchRepositoryMockConfiguration
     */
    @Autowired
    private ActivityResultSplitSearchRepository mockActivityResultSplitSearchRepository;

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

    private MockMvc restActivityResultSplitMockMvc;

    private ActivityResultSplit activityResultSplit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivityResultSplitResource activityResultSplitResource = new ActivityResultSplitResource(activityResultSplitService);
        this.restActivityResultSplitMockMvc = MockMvcBuilders.standaloneSetup(activityResultSplitResource)
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
    public static ActivityResultSplit createEntity(EntityManager em) {
        ActivityResultSplit activityResultSplit = new ActivityResultSplit()
            .splitValue(DEFAULT_SPLIT_VALUE);
        return activityResultSplit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityResultSplit createUpdatedEntity(EntityManager em) {
        ActivityResultSplit activityResultSplit = new ActivityResultSplit()
            .splitValue(UPDATED_SPLIT_VALUE);
        return activityResultSplit;
    }

    @BeforeEach
    public void initTest() {
        activityResultSplit = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivityResultSplit() throws Exception {
        int databaseSizeBeforeCreate = activityResultSplitRepository.findAll().size();

        // Create the ActivityResultSplit
        ActivityResultSplitDTO activityResultSplitDTO = activityResultSplitMapper.toDto(activityResultSplit);
        restActivityResultSplitMockMvc.perform(post("/api/activity-result-splits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityResultSplitDTO)))
            .andExpect(status().isCreated());

        // Validate the ActivityResultSplit in the database
        List<ActivityResultSplit> activityResultSplitList = activityResultSplitRepository.findAll();
        assertThat(activityResultSplitList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityResultSplit testActivityResultSplit = activityResultSplitList.get(activityResultSplitList.size() - 1);
        assertThat(testActivityResultSplit.getSplitValue()).isEqualTo(DEFAULT_SPLIT_VALUE);

        // Validate the ActivityResultSplit in Elasticsearch
        verify(mockActivityResultSplitSearchRepository, times(1)).save(testActivityResultSplit);
    }

    @Test
    @Transactional
    public void createActivityResultSplitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityResultSplitRepository.findAll().size();

        // Create the ActivityResultSplit with an existing ID
        activityResultSplit.setId(1L);
        ActivityResultSplitDTO activityResultSplitDTO = activityResultSplitMapper.toDto(activityResultSplit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityResultSplitMockMvc.perform(post("/api/activity-result-splits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityResultSplitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityResultSplit in the database
        List<ActivityResultSplit> activityResultSplitList = activityResultSplitRepository.findAll();
        assertThat(activityResultSplitList).hasSize(databaseSizeBeforeCreate);

        // Validate the ActivityResultSplit in Elasticsearch
        verify(mockActivityResultSplitSearchRepository, times(0)).save(activityResultSplit);
    }


    @Test
    @Transactional
    public void getAllActivityResultSplits() throws Exception {
        // Initialize the database
        activityResultSplitRepository.saveAndFlush(activityResultSplit);

        // Get all the activityResultSplitList
        restActivityResultSplitMockMvc.perform(get("/api/activity-result-splits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityResultSplit.getId().intValue())))
            .andExpect(jsonPath("$.[*].splitValue").value(hasItem(DEFAULT_SPLIT_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getActivityResultSplit() throws Exception {
        // Initialize the database
        activityResultSplitRepository.saveAndFlush(activityResultSplit);

        // Get the activityResultSplit
        restActivityResultSplitMockMvc.perform(get("/api/activity-result-splits/{id}", activityResultSplit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activityResultSplit.getId().intValue()))
            .andExpect(jsonPath("$.splitValue").value(DEFAULT_SPLIT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingActivityResultSplit() throws Exception {
        // Get the activityResultSplit
        restActivityResultSplitMockMvc.perform(get("/api/activity-result-splits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivityResultSplit() throws Exception {
        // Initialize the database
        activityResultSplitRepository.saveAndFlush(activityResultSplit);

        int databaseSizeBeforeUpdate = activityResultSplitRepository.findAll().size();

        // Update the activityResultSplit
        ActivityResultSplit updatedActivityResultSplit = activityResultSplitRepository.findById(activityResultSplit.getId()).get();
        // Disconnect from session so that the updates on updatedActivityResultSplit are not directly saved in db
        em.detach(updatedActivityResultSplit);
        updatedActivityResultSplit
            .splitValue(UPDATED_SPLIT_VALUE);
        ActivityResultSplitDTO activityResultSplitDTO = activityResultSplitMapper.toDto(updatedActivityResultSplit);

        restActivityResultSplitMockMvc.perform(put("/api/activity-result-splits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityResultSplitDTO)))
            .andExpect(status().isOk());

        // Validate the ActivityResultSplit in the database
        List<ActivityResultSplit> activityResultSplitList = activityResultSplitRepository.findAll();
        assertThat(activityResultSplitList).hasSize(databaseSizeBeforeUpdate);
        ActivityResultSplit testActivityResultSplit = activityResultSplitList.get(activityResultSplitList.size() - 1);
        assertThat(testActivityResultSplit.getSplitValue()).isEqualTo(UPDATED_SPLIT_VALUE);

        // Validate the ActivityResultSplit in Elasticsearch
        verify(mockActivityResultSplitSearchRepository, times(1)).save(testActivityResultSplit);
    }

    @Test
    @Transactional
    public void updateNonExistingActivityResultSplit() throws Exception {
        int databaseSizeBeforeUpdate = activityResultSplitRepository.findAll().size();

        // Create the ActivityResultSplit
        ActivityResultSplitDTO activityResultSplitDTO = activityResultSplitMapper.toDto(activityResultSplit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityResultSplitMockMvc.perform(put("/api/activity-result-splits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityResultSplitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityResultSplit in the database
        List<ActivityResultSplit> activityResultSplitList = activityResultSplitRepository.findAll();
        assertThat(activityResultSplitList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ActivityResultSplit in Elasticsearch
        verify(mockActivityResultSplitSearchRepository, times(0)).save(activityResultSplit);
    }

    @Test
    @Transactional
    public void deleteActivityResultSplit() throws Exception {
        // Initialize the database
        activityResultSplitRepository.saveAndFlush(activityResultSplit);

        int databaseSizeBeforeDelete = activityResultSplitRepository.findAll().size();

        // Delete the activityResultSplit
        restActivityResultSplitMockMvc.perform(delete("/api/activity-result-splits/{id}", activityResultSplit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivityResultSplit> activityResultSplitList = activityResultSplitRepository.findAll();
        assertThat(activityResultSplitList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ActivityResultSplit in Elasticsearch
        verify(mockActivityResultSplitSearchRepository, times(1)).deleteById(activityResultSplit.getId());
    }

    @Test
    @Transactional
    public void searchActivityResultSplit() throws Exception {
        // Initialize the database
        activityResultSplitRepository.saveAndFlush(activityResultSplit);
        when(mockActivityResultSplitSearchRepository.search(queryStringQuery("id:" + activityResultSplit.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(activityResultSplit), PageRequest.of(0, 1), 1));
        // Search the activityResultSplit
        restActivityResultSplitMockMvc.perform(get("/api/_search/activity-result-splits?query=id:" + activityResultSplit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityResultSplit.getId().intValue())))
            .andExpect(jsonPath("$.[*].splitValue").value(hasItem(DEFAULT_SPLIT_VALUE.doubleValue())));
    }
}
