package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;

import cz.sportiq.domain.Sport;
import cz.sportiq.repository.SportRepository;
import cz.sportiq.repository.search.SportSearchRepository;
import cz.sportiq.service.SportService;
import cz.sportiq.service.dto.SportDTO;
import cz.sportiq.service.mapper.SportMapper;
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
 * Test class for the SportResource REST controller.
 *
 * @see SportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SportiqApp.class)
public class SportResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private SportMapper sportMapper;
    
    @Autowired
    private SportService sportService;

    /**
     * This repository is mocked in the cz.sportiq.repository.search test package.
     *
     * @see cz.sportiq.repository.search.SportSearchRepositoryMockConfiguration
     */
    @Autowired
    private SportSearchRepository mockSportSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSportMockMvc;

    private Sport sport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SportResource sportResource = new SportResource(sportService);
        this.restSportMockMvc = MockMvcBuilders.standaloneSetup(sportResource)
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
    public static Sport createEntity(EntityManager em) {
        Sport sport = new Sport()
            .name(DEFAULT_NAME);
        return sport;
    }

    @Before
    public void initTest() {
        sport = createEntity(em);
    }

    @Test
    @Transactional
    public void createSport() throws Exception {
        int databaseSizeBeforeCreate = sportRepository.findAll().size();

        // Create the Sport
        SportDTO sportDTO = sportMapper.toDto(sport);
        restSportMockMvc.perform(post("/api/sports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sportDTO)))
            .andExpect(status().isCreated());

        // Validate the Sport in the database
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeCreate + 1);
        Sport testSport = sportList.get(sportList.size() - 1);
        assertThat(testSport.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Sport in Elasticsearch
        verify(mockSportSearchRepository, times(1)).save(testSport);
    }

    @Test
    @Transactional
    public void createSportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sportRepository.findAll().size();

        // Create the Sport with an existing ID
        sport.setId(1L);
        SportDTO sportDTO = sportMapper.toDto(sport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSportMockMvc.perform(post("/api/sports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sport in the database
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeCreate);

        // Validate the Sport in Elasticsearch
        verify(mockSportSearchRepository, times(0)).save(sport);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sportRepository.findAll().size();
        // set the field null
        sport.setName(null);

        // Create the Sport, which fails.
        SportDTO sportDTO = sportMapper.toDto(sport);

        restSportMockMvc.perform(post("/api/sports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sportDTO)))
            .andExpect(status().isBadRequest());

        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSports() throws Exception {
        // Initialize the database
        sportRepository.saveAndFlush(sport);

        // Get all the sportList
        restSportMockMvc.perform(get("/api/sports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sport.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getSport() throws Exception {
        // Initialize the database
        sportRepository.saveAndFlush(sport);

        // Get the sport
        restSportMockMvc.perform(get("/api/sports/{id}", sport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sport.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSport() throws Exception {
        // Get the sport
        restSportMockMvc.perform(get("/api/sports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSport() throws Exception {
        // Initialize the database
        sportRepository.saveAndFlush(sport);

        int databaseSizeBeforeUpdate = sportRepository.findAll().size();

        // Update the sport
        Sport updatedSport = sportRepository.findById(sport.getId()).get();
        // Disconnect from session so that the updates on updatedSport are not directly saved in db
        em.detach(updatedSport);
        updatedSport
            .name(UPDATED_NAME);
        SportDTO sportDTO = sportMapper.toDto(updatedSport);

        restSportMockMvc.perform(put("/api/sports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sportDTO)))
            .andExpect(status().isOk());

        // Validate the Sport in the database
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeUpdate);
        Sport testSport = sportList.get(sportList.size() - 1);
        assertThat(testSport.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Sport in Elasticsearch
        verify(mockSportSearchRepository, times(1)).save(testSport);
    }

    @Test
    @Transactional
    public void updateNonExistingSport() throws Exception {
        int databaseSizeBeforeUpdate = sportRepository.findAll().size();

        // Create the Sport
        SportDTO sportDTO = sportMapper.toDto(sport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportMockMvc.perform(put("/api/sports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sport in the database
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Sport in Elasticsearch
        verify(mockSportSearchRepository, times(0)).save(sport);
    }

    @Test
    @Transactional
    public void deleteSport() throws Exception {
        // Initialize the database
        sportRepository.saveAndFlush(sport);

        int databaseSizeBeforeDelete = sportRepository.findAll().size();

        // Get the sport
        restSportMockMvc.perform(delete("/api/sports/{id}", sport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Sport in Elasticsearch
        verify(mockSportSearchRepository, times(1)).deleteById(sport.getId());
    }

    @Test
    @Transactional
    public void searchSport() throws Exception {
        // Initialize the database
        sportRepository.saveAndFlush(sport);
        when(mockSportSearchRepository.search(queryStringQuery("id:" + sport.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(sport), PageRequest.of(0, 1), 1));
        // Search the sport
        restSportMockMvc.perform(get("/api/_search/sports?query=id:" + sport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sport.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sport.class);
        Sport sport1 = new Sport();
        sport1.setId(1L);
        Sport sport2 = new Sport();
        sport2.setId(sport1.getId());
        assertThat(sport1).isEqualTo(sport2);
        sport2.setId(2L);
        assertThat(sport1).isNotEqualTo(sport2);
        sport1.setId(null);
        assertThat(sport1).isNotEqualTo(sport2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SportDTO.class);
        SportDTO sportDTO1 = new SportDTO();
        sportDTO1.setId(1L);
        SportDTO sportDTO2 = new SportDTO();
        assertThat(sportDTO1).isNotEqualTo(sportDTO2);
        sportDTO2.setId(sportDTO1.getId());
        assertThat(sportDTO1).isEqualTo(sportDTO2);
        sportDTO2.setId(2L);
        assertThat(sportDTO1).isNotEqualTo(sportDTO2);
        sportDTO1.setId(null);
        assertThat(sportDTO1).isNotEqualTo(sportDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sportMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sportMapper.fromId(null)).isNull();
    }
}
