package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;
import cz.sportiq.config.TestSecurityConfiguration;
import cz.sportiq.domain.WorkoutCategory;
import cz.sportiq.repository.WorkoutCategoryRepository;
import cz.sportiq.repository.search.WorkoutCategorySearchRepository;
import cz.sportiq.service.WorkoutCategoryService;
import cz.sportiq.service.dto.WorkoutCategoryDTO;
import cz.sportiq.service.mapper.WorkoutCategoryMapper;
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
 * Integration tests for the {@link WorkoutCategoryResource} REST controller.
 */
@SpringBootTest(classes = {SportiqApp.class, TestSecurityConfiguration.class})
public class WorkoutCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private WorkoutCategoryRepository workoutCategoryRepository;

    @Autowired
    private WorkoutCategoryMapper workoutCategoryMapper;

    @Autowired
    private WorkoutCategoryService workoutCategoryService;

    /**
     * This repository is mocked in the cz.sportiq.repository.search test package.
     *
     * @see cz.sportiq.repository.search.WorkoutCategorySearchRepositoryMockConfiguration
     */
    @Autowired
    private WorkoutCategorySearchRepository mockWorkoutCategorySearchRepository;

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

    private MockMvc restWorkoutCategoryMockMvc;

    private WorkoutCategory workoutCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkoutCategoryResource workoutCategoryResource = new WorkoutCategoryResource(workoutCategoryService);
        this.restWorkoutCategoryMockMvc = MockMvcBuilders.standaloneSetup(workoutCategoryResource)
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
    public static WorkoutCategory createEntity(EntityManager em) {
        WorkoutCategory workoutCategory = new WorkoutCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return workoutCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkoutCategory createUpdatedEntity(EntityManager em) {
        WorkoutCategory workoutCategory = new WorkoutCategory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return workoutCategory;
    }

    @BeforeEach
    public void initTest() {
        workoutCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkoutCategory() throws Exception {
        int databaseSizeBeforeCreate = workoutCategoryRepository.findAll().size();

        // Create the WorkoutCategory
        WorkoutCategoryDTO workoutCategoryDTO = workoutCategoryMapper.toDto(workoutCategory);
        restWorkoutCategoryMockMvc.perform(post("/api/workout-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkoutCategory in the database
        List<WorkoutCategory> workoutCategoryList = workoutCategoryRepository.findAll();
        assertThat(workoutCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        WorkoutCategory testWorkoutCategory = workoutCategoryList.get(workoutCategoryList.size() - 1);
        assertThat(testWorkoutCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkoutCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the WorkoutCategory in Elasticsearch
        verify(mockWorkoutCategorySearchRepository, times(1)).save(testWorkoutCategory);
    }

    @Test
    @Transactional
    public void createWorkoutCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workoutCategoryRepository.findAll().size();

        // Create the WorkoutCategory with an existing ID
        workoutCategory.setId(1L);
        WorkoutCategoryDTO workoutCategoryDTO = workoutCategoryMapper.toDto(workoutCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkoutCategoryMockMvc.perform(post("/api/workout-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkoutCategory in the database
        List<WorkoutCategory> workoutCategoryList = workoutCategoryRepository.findAll();
        assertThat(workoutCategoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the WorkoutCategory in Elasticsearch
        verify(mockWorkoutCategorySearchRepository, times(0)).save(workoutCategory);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutCategoryRepository.findAll().size();
        // set the field null
        workoutCategory.setName(null);

        // Create the WorkoutCategory, which fails.
        WorkoutCategoryDTO workoutCategoryDTO = workoutCategoryMapper.toDto(workoutCategory);

        restWorkoutCategoryMockMvc.perform(post("/api/workout-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<WorkoutCategory> workoutCategoryList = workoutCategoryRepository.findAll();
        assertThat(workoutCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkoutCategories() throws Exception {
        // Initialize the database
        workoutCategoryRepository.saveAndFlush(workoutCategory);

        // Get all the workoutCategoryList
        restWorkoutCategoryMockMvc.perform(get("/api/workout-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workoutCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getWorkoutCategory() throws Exception {
        // Initialize the database
        workoutCategoryRepository.saveAndFlush(workoutCategory);

        // Get the workoutCategory
        restWorkoutCategoryMockMvc.perform(get("/api/workout-categories/{id}", workoutCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workoutCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingWorkoutCategory() throws Exception {
        // Get the workoutCategory
        restWorkoutCategoryMockMvc.perform(get("/api/workout-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkoutCategory() throws Exception {
        // Initialize the database
        workoutCategoryRepository.saveAndFlush(workoutCategory);

        int databaseSizeBeforeUpdate = workoutCategoryRepository.findAll().size();

        // Update the workoutCategory
        WorkoutCategory updatedWorkoutCategory = workoutCategoryRepository.findById(workoutCategory.getId()).get();
        // Disconnect from session so that the updates on updatedWorkoutCategory are not directly saved in db
        em.detach(updatedWorkoutCategory);
        updatedWorkoutCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        WorkoutCategoryDTO workoutCategoryDTO = workoutCategoryMapper.toDto(updatedWorkoutCategory);

        restWorkoutCategoryMockMvc.perform(put("/api/workout-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the WorkoutCategory in the database
        List<WorkoutCategory> workoutCategoryList = workoutCategoryRepository.findAll();
        assertThat(workoutCategoryList).hasSize(databaseSizeBeforeUpdate);
        WorkoutCategory testWorkoutCategory = workoutCategoryList.get(workoutCategoryList.size() - 1);
        assertThat(testWorkoutCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkoutCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the WorkoutCategory in Elasticsearch
        verify(mockWorkoutCategorySearchRepository, times(1)).save(testWorkoutCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkoutCategory() throws Exception {
        int databaseSizeBeforeUpdate = workoutCategoryRepository.findAll().size();

        // Create the WorkoutCategory
        WorkoutCategoryDTO workoutCategoryDTO = workoutCategoryMapper.toDto(workoutCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkoutCategoryMockMvc.perform(put("/api/workout-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workoutCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkoutCategory in the database
        List<WorkoutCategory> workoutCategoryList = workoutCategoryRepository.findAll();
        assertThat(workoutCategoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the WorkoutCategory in Elasticsearch
        verify(mockWorkoutCategorySearchRepository, times(0)).save(workoutCategory);
    }

    @Test
    @Transactional
    public void deleteWorkoutCategory() throws Exception {
        // Initialize the database
        workoutCategoryRepository.saveAndFlush(workoutCategory);

        int databaseSizeBeforeDelete = workoutCategoryRepository.findAll().size();

        // Delete the workoutCategory
        restWorkoutCategoryMockMvc.perform(delete("/api/workout-categories/{id}", workoutCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkoutCategory> workoutCategoryList = workoutCategoryRepository.findAll();
        assertThat(workoutCategoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the WorkoutCategory in Elasticsearch
        verify(mockWorkoutCategorySearchRepository, times(1)).deleteById(workoutCategory.getId());
    }

    @Test
    @Transactional
    public void searchWorkoutCategory() throws Exception {
        // Initialize the database
        workoutCategoryRepository.saveAndFlush(workoutCategory);
        when(mockWorkoutCategorySearchRepository.search(queryStringQuery("id:" + workoutCategory.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(workoutCategory), PageRequest.of(0, 1), 1));
        // Search the workoutCategory
        restWorkoutCategoryMockMvc.perform(get("/api/_search/workout-categories?query=id:" + workoutCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workoutCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
