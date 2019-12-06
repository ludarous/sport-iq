package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;

import cz.sportiq.domain.AthleteActivityResult;
import cz.sportiq.domain.ActivityResult;
import cz.sportiq.repository.AthleteActivityResultRepository;
import cz.sportiq.repository.search.AthleteActivityResultSearchRepository;
import cz.sportiq.service.AthleteActivityResultService;
import cz.sportiq.service.dto.AthleteActivityResultDTO;
import cz.sportiq.service.mapper.AthleteActivityResultMapper;
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

/**
 * Test class for the AthleteActivityResultResource REST controller.
 *
 * @see AthleteActivityResultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SportiqApp.class)
public class AthleteActivityResultResourceIntTest {

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

    /**
     * This repository is mocked in the cz.sportiq.repository.search test package.
     *
     * @see cz.sportiq.repository.search.AthleteActivityResultSearchRepositoryMockConfiguration
     */
    @Autowired
    private AthleteActivityResultSearchRepository mockAthleteActivityResultSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAthleteActivityResultMockMvc;

    private AthleteActivityResult athleteActivityResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AthleteActivityResultResource athleteActivityResultResource = new AthleteActivityResultResource(athleteActivityResultService);
        this.restAthleteActivityResultMockMvc = MockMvcBuilders.standaloneSetup(athleteActivityResultResource)
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
    public static AthleteActivityResult createEntity(EntityManager em) {
        AthleteActivityResult athleteActivityResult = new AthleteActivityResult()
            .value(DEFAULT_VALUE)
            .compareValue(DEFAULT_COMPARE_VALUE);
        // Add required entity
        ActivityResult activityResult = ActivityResultResourceIntTest.createEntity(em);
        em.persist(activityResult);
        em.flush();
        athleteActivityResult.setActivityResult(activityResult);
        return athleteActivityResult;
    }

    @Before
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityResultDTO)))
            .andExpect(status().isCreated());

        // Validate the AthleteActivityResult in the database
        List<AthleteActivityResult> athleteActivityResultList = athleteActivityResultRepository.findAll();
        assertThat(athleteActivityResultList).hasSize(databaseSizeBeforeCreate + 1);
        AthleteActivityResult testAthleteActivityResult = athleteActivityResultList.get(athleteActivityResultList.size() - 1);
        assertThat(testAthleteActivityResult.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAthleteActivityResult.getCompareValue()).isEqualTo(DEFAULT_COMPARE_VALUE);

        // Validate the AthleteActivityResult in Elasticsearch
        verify(mockAthleteActivityResultSearchRepository, times(1)).save(testAthleteActivityResult);
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteActivityResult in the database
        List<AthleteActivityResult> athleteActivityResultList = athleteActivityResultRepository.findAll();
        assertThat(athleteActivityResultList).hasSize(databaseSizeBeforeCreate);

        // Validate the AthleteActivityResult in Elasticsearch
        verify(mockAthleteActivityResultSearchRepository, times(0)).save(athleteActivityResult);
    }

    @Test
    @Transactional
    public void getAllAthleteActivityResults() throws Exception {
        // Initialize the database
        athleteActivityResultRepository.saveAndFlush(athleteActivityResult);

        // Get all the athleteActivityResultList
        restAthleteActivityResultMockMvc.perform(get("/api/athlete-activity-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityResultDTO)))
            .andExpect(status().isOk());

        // Validate the AthleteActivityResult in the database
        List<AthleteActivityResult> athleteActivityResultList = athleteActivityResultRepository.findAll();
        assertThat(athleteActivityResultList).hasSize(databaseSizeBeforeUpdate);
        AthleteActivityResult testAthleteActivityResult = athleteActivityResultList.get(athleteActivityResultList.size() - 1);
        assertThat(testAthleteActivityResult.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAthleteActivityResult.getCompareValue()).isEqualTo(UPDATED_COMPARE_VALUE);

        // Validate the AthleteActivityResult in Elasticsearch
        verify(mockAthleteActivityResultSearchRepository, times(1)).save(testAthleteActivityResult);
    }

    @Test
    @Transactional
    public void updateNonExistingAthleteActivityResult() throws Exception {
        int databaseSizeBeforeUpdate = athleteActivityResultRepository.findAll().size();

        // Create the AthleteActivityResult
        AthleteActivityResultDTO athleteActivityResultDTO = athleteActivityResultMapper.toDto(athleteActivityResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAthleteActivityResultMockMvc.perform(put("/api/athlete-activity-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteActivityResult in the database
        List<AthleteActivityResult> athleteActivityResultList = athleteActivityResultRepository.findAll();
        assertThat(athleteActivityResultList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AthleteActivityResult in Elasticsearch
        verify(mockAthleteActivityResultSearchRepository, times(0)).save(athleteActivityResult);
    }

    @Test
    @Transactional
    public void deleteAthleteActivityResult() throws Exception {
        // Initialize the database
        athleteActivityResultRepository.saveAndFlush(athleteActivityResult);

        int databaseSizeBeforeDelete = athleteActivityResultRepository.findAll().size();

        // Get the athleteActivityResult
        restAthleteActivityResultMockMvc.perform(delete("/api/athlete-activity-results/{id}", athleteActivityResult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AthleteActivityResult> athleteActivityResultList = athleteActivityResultRepository.findAll();
        assertThat(athleteActivityResultList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AthleteActivityResult in Elasticsearch
        verify(mockAthleteActivityResultSearchRepository, times(1)).deleteById(athleteActivityResult.getId());
    }

    @Test
    @Transactional
    public void searchAthleteActivityResult() throws Exception {
        // Initialize the database
        athleteActivityResultRepository.saveAndFlush(athleteActivityResult);
        when(mockAthleteActivityResultSearchRepository.search(queryStringQuery("id:" + athleteActivityResult.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(athleteActivityResult), PageRequest.of(0, 1), 1));
        // Search the athleteActivityResult
        restAthleteActivityResultMockMvc.perform(get("/api/_search/athlete-activity-results?query=id:" + athleteActivityResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(athleteActivityResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].compareValue").value(hasItem(DEFAULT_COMPARE_VALUE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteActivityResult.class);
        AthleteActivityResult athleteActivityResult1 = new AthleteActivityResult();
        athleteActivityResult1.setId(1L);
        AthleteActivityResult athleteActivityResult2 = new AthleteActivityResult();
        athleteActivityResult2.setId(athleteActivityResult1.getId());
        assertThat(athleteActivityResult1).isEqualTo(athleteActivityResult2);
        athleteActivityResult2.setId(2L);
        assertThat(athleteActivityResult1).isNotEqualTo(athleteActivityResult2);
        athleteActivityResult1.setId(null);
        assertThat(athleteActivityResult1).isNotEqualTo(athleteActivityResult2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteActivityResultDTO.class);
        AthleteActivityResultDTO athleteActivityResultDTO1 = new AthleteActivityResultDTO();
        athleteActivityResultDTO1.setId(1L);
        AthleteActivityResultDTO athleteActivityResultDTO2 = new AthleteActivityResultDTO();
        assertThat(athleteActivityResultDTO1).isNotEqualTo(athleteActivityResultDTO2);
        athleteActivityResultDTO2.setId(athleteActivityResultDTO1.getId());
        assertThat(athleteActivityResultDTO1).isEqualTo(athleteActivityResultDTO2);
        athleteActivityResultDTO2.setId(2L);
        assertThat(athleteActivityResultDTO1).isNotEqualTo(athleteActivityResultDTO2);
        athleteActivityResultDTO1.setId(null);
        assertThat(athleteActivityResultDTO1).isNotEqualTo(athleteActivityResultDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(athleteActivityResultMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(athleteActivityResultMapper.fromId(null)).isNull();
    }
}
