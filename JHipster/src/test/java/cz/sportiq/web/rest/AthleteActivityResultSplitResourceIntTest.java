package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;

import cz.sportiq.domain.AthleteActivityResultSplit;
import cz.sportiq.repository.AthleteActivityResultSplitRepository;
import cz.sportiq.repository.search.AthleteActivityResultSplitSearchRepository;
import cz.sportiq.service.AthleteActivityResultSplitService;
import cz.sportiq.service.dto.AthleteActivityResultSplitDTO;
import cz.sportiq.service.mapper.AthleteActivityResultSplitMapper;
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
 * Test class for the AthleteActivityResultSplitResource REST controller.
 *
 * @see AthleteActivityResultSplitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SportiqApp.class)
public class AthleteActivityResultSplitResourceIntTest {

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    @Autowired
    private AthleteActivityResultSplitRepository athleteActivityResultSplitRepository;

    @Autowired
    private AthleteActivityResultSplitMapper athleteActivityResultSplitMapper;
    
    @Autowired
    private AthleteActivityResultSplitService athleteActivityResultSplitService;

    /**
     * This repository is mocked in the cz.sportiq.repository.search test package.
     *
     * @see cz.sportiq.repository.search.AthleteActivityResultSplitSearchRepositoryMockConfiguration
     */
    @Autowired
    private AthleteActivityResultSplitSearchRepository mockAthleteActivityResultSplitSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAthleteActivityResultSplitMockMvc;

    private AthleteActivityResultSplit athleteActivityResultSplit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AthleteActivityResultSplitResource athleteActivityResultSplitResource = new AthleteActivityResultSplitResource(athleteActivityResultSplitService);
        this.restAthleteActivityResultSplitMockMvc = MockMvcBuilders.standaloneSetup(athleteActivityResultSplitResource)
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
    public static AthleteActivityResultSplit createEntity(EntityManager em) {
        AthleteActivityResultSplit athleteActivityResultSplit = new AthleteActivityResultSplit()
            .value(DEFAULT_VALUE);
        return athleteActivityResultSplit;
    }

    @Before
    public void initTest() {
        athleteActivityResultSplit = createEntity(em);
    }

    @Test
    @Transactional
    public void createAthleteActivityResultSplit() throws Exception {
        int databaseSizeBeforeCreate = athleteActivityResultSplitRepository.findAll().size();

        // Create the AthleteActivityResultSplit
        AthleteActivityResultSplitDTO athleteActivityResultSplitDTO = athleteActivityResultSplitMapper.toDto(athleteActivityResultSplit);
        restAthleteActivityResultSplitMockMvc.perform(post("/api/athlete-activity-result-splits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityResultSplitDTO)))
            .andExpect(status().isCreated());

        // Validate the AthleteActivityResultSplit in the database
        List<AthleteActivityResultSplit> athleteActivityResultSplitList = athleteActivityResultSplitRepository.findAll();
        assertThat(athleteActivityResultSplitList).hasSize(databaseSizeBeforeCreate + 1);
        AthleteActivityResultSplit testAthleteActivityResultSplit = athleteActivityResultSplitList.get(athleteActivityResultSplitList.size() - 1);
        assertThat(testAthleteActivityResultSplit.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the AthleteActivityResultSplit in Elasticsearch
        verify(mockAthleteActivityResultSplitSearchRepository, times(1)).save(testAthleteActivityResultSplit);
    }

    @Test
    @Transactional
    public void createAthleteActivityResultSplitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = athleteActivityResultSplitRepository.findAll().size();

        // Create the AthleteActivityResultSplit with an existing ID
        athleteActivityResultSplit.setId(1L);
        AthleteActivityResultSplitDTO athleteActivityResultSplitDTO = athleteActivityResultSplitMapper.toDto(athleteActivityResultSplit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAthleteActivityResultSplitMockMvc.perform(post("/api/athlete-activity-result-splits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityResultSplitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteActivityResultSplit in the database
        List<AthleteActivityResultSplit> athleteActivityResultSplitList = athleteActivityResultSplitRepository.findAll();
        assertThat(athleteActivityResultSplitList).hasSize(databaseSizeBeforeCreate);

        // Validate the AthleteActivityResultSplit in Elasticsearch
        verify(mockAthleteActivityResultSplitSearchRepository, times(0)).save(athleteActivityResultSplit);
    }

    @Test
    @Transactional
    public void getAllAthleteActivityResultSplits() throws Exception {
        // Initialize the database
        athleteActivityResultSplitRepository.saveAndFlush(athleteActivityResultSplit);

        // Get all the athleteActivityResultSplitList
        restAthleteActivityResultSplitMockMvc.perform(get("/api/athlete-activity-result-splits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(athleteActivityResultSplit.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAthleteActivityResultSplit() throws Exception {
        // Initialize the database
        athleteActivityResultSplitRepository.saveAndFlush(athleteActivityResultSplit);

        // Get the athleteActivityResultSplit
        restAthleteActivityResultSplitMockMvc.perform(get("/api/athlete-activity-result-splits/{id}", athleteActivityResultSplit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(athleteActivityResultSplit.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAthleteActivityResultSplit() throws Exception {
        // Get the athleteActivityResultSplit
        restAthleteActivityResultSplitMockMvc.perform(get("/api/athlete-activity-result-splits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAthleteActivityResultSplit() throws Exception {
        // Initialize the database
        athleteActivityResultSplitRepository.saveAndFlush(athleteActivityResultSplit);

        int databaseSizeBeforeUpdate = athleteActivityResultSplitRepository.findAll().size();

        // Update the athleteActivityResultSplit
        AthleteActivityResultSplit updatedAthleteActivityResultSplit = athleteActivityResultSplitRepository.findById(athleteActivityResultSplit.getId()).get();
        // Disconnect from session so that the updates on updatedAthleteActivityResultSplit are not directly saved in db
        em.detach(updatedAthleteActivityResultSplit);
        updatedAthleteActivityResultSplit
            .value(UPDATED_VALUE);
        AthleteActivityResultSplitDTO athleteActivityResultSplitDTO = athleteActivityResultSplitMapper.toDto(updatedAthleteActivityResultSplit);

        restAthleteActivityResultSplitMockMvc.perform(put("/api/athlete-activity-result-splits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityResultSplitDTO)))
            .andExpect(status().isOk());

        // Validate the AthleteActivityResultSplit in the database
        List<AthleteActivityResultSplit> athleteActivityResultSplitList = athleteActivityResultSplitRepository.findAll();
        assertThat(athleteActivityResultSplitList).hasSize(databaseSizeBeforeUpdate);
        AthleteActivityResultSplit testAthleteActivityResultSplit = athleteActivityResultSplitList.get(athleteActivityResultSplitList.size() - 1);
        assertThat(testAthleteActivityResultSplit.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the AthleteActivityResultSplit in Elasticsearch
        verify(mockAthleteActivityResultSplitSearchRepository, times(1)).save(testAthleteActivityResultSplit);
    }

    @Test
    @Transactional
    public void updateNonExistingAthleteActivityResultSplit() throws Exception {
        int databaseSizeBeforeUpdate = athleteActivityResultSplitRepository.findAll().size();

        // Create the AthleteActivityResultSplit
        AthleteActivityResultSplitDTO athleteActivityResultSplitDTO = athleteActivityResultSplitMapper.toDto(athleteActivityResultSplit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAthleteActivityResultSplitMockMvc.perform(put("/api/athlete-activity-result-splits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athleteActivityResultSplitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteActivityResultSplit in the database
        List<AthleteActivityResultSplit> athleteActivityResultSplitList = athleteActivityResultSplitRepository.findAll();
        assertThat(athleteActivityResultSplitList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AthleteActivityResultSplit in Elasticsearch
        verify(mockAthleteActivityResultSplitSearchRepository, times(0)).save(athleteActivityResultSplit);
    }

    @Test
    @Transactional
    public void deleteAthleteActivityResultSplit() throws Exception {
        // Initialize the database
        athleteActivityResultSplitRepository.saveAndFlush(athleteActivityResultSplit);

        int databaseSizeBeforeDelete = athleteActivityResultSplitRepository.findAll().size();

        // Get the athleteActivityResultSplit
        restAthleteActivityResultSplitMockMvc.perform(delete("/api/athlete-activity-result-splits/{id}", athleteActivityResultSplit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AthleteActivityResultSplit> athleteActivityResultSplitList = athleteActivityResultSplitRepository.findAll();
        assertThat(athleteActivityResultSplitList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AthleteActivityResultSplit in Elasticsearch
        verify(mockAthleteActivityResultSplitSearchRepository, times(1)).deleteById(athleteActivityResultSplit.getId());
    }

    @Test
    @Transactional
    public void searchAthleteActivityResultSplit() throws Exception {
        // Initialize the database
        athleteActivityResultSplitRepository.saveAndFlush(athleteActivityResultSplit);
        when(mockAthleteActivityResultSplitSearchRepository.search(queryStringQuery("id:" + athleteActivityResultSplit.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(athleteActivityResultSplit), PageRequest.of(0, 1), 1));
        // Search the athleteActivityResultSplit
        restAthleteActivityResultSplitMockMvc.perform(get("/api/_search/athlete-activity-result-splits?query=id:" + athleteActivityResultSplit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(athleteActivityResultSplit.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteActivityResultSplit.class);
        AthleteActivityResultSplit athleteActivityResultSplit1 = new AthleteActivityResultSplit();
        athleteActivityResultSplit1.setId(1L);
        AthleteActivityResultSplit athleteActivityResultSplit2 = new AthleteActivityResultSplit();
        athleteActivityResultSplit2.setId(athleteActivityResultSplit1.getId());
        assertThat(athleteActivityResultSplit1).isEqualTo(athleteActivityResultSplit2);
        athleteActivityResultSplit2.setId(2L);
        assertThat(athleteActivityResultSplit1).isNotEqualTo(athleteActivityResultSplit2);
        athleteActivityResultSplit1.setId(null);
        assertThat(athleteActivityResultSplit1).isNotEqualTo(athleteActivityResultSplit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteActivityResultSplitDTO.class);
        AthleteActivityResultSplitDTO athleteActivityResultSplitDTO1 = new AthleteActivityResultSplitDTO();
        athleteActivityResultSplitDTO1.setId(1L);
        AthleteActivityResultSplitDTO athleteActivityResultSplitDTO2 = new AthleteActivityResultSplitDTO();
        assertThat(athleteActivityResultSplitDTO1).isNotEqualTo(athleteActivityResultSplitDTO2);
        athleteActivityResultSplitDTO2.setId(athleteActivityResultSplitDTO1.getId());
        assertThat(athleteActivityResultSplitDTO1).isEqualTo(athleteActivityResultSplitDTO2);
        athleteActivityResultSplitDTO2.setId(2L);
        assertThat(athleteActivityResultSplitDTO1).isNotEqualTo(athleteActivityResultSplitDTO2);
        athleteActivityResultSplitDTO1.setId(null);
        assertThat(athleteActivityResultSplitDTO1).isNotEqualTo(athleteActivityResultSplitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(athleteActivityResultSplitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(athleteActivityResultSplitMapper.fromId(null)).isNull();
    }
}
