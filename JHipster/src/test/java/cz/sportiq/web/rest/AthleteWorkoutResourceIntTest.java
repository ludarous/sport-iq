package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;

import cz.sportiq.domain.AthleteWorkout;
import cz.sportiq.domain.Workout;
import cz.sportiq.repository.AthleteWorkoutRepository;
import cz.sportiq.repository.search.AthleteWorkoutSearchRepository;
import cz.sportiq.service.AthleteWorkoutService;
import cz.sportiq.service.dto.AthleteWorkoutDTO;
import cz.sportiq.service.mapper.AthleteWorkoutMapper;
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
 * Test class for the AthleteWorkoutResource REST controller.
 *
 * @see AthleteWorkoutResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SportiqApp.class)
public class AthleteWorkoutResourceIntTest {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private AthleteWorkoutRepository athleteWorkoutRepository;

    @Autowired
    private AthleteWorkoutMapper athleteWorkoutMapper;
    
    @Autowired
    private AthleteWorkoutService athleteWorkoutService;

    /**
     * This repository is mocked in the cz.sportiq.repository.search test package.
     *
     * @see cz.sportiq.repository.search.AthleteWorkoutSearchRepositoryMockConfiguration
     */
    @Autowired
    private AthleteWorkoutSearchRepository mockAthleteWorkoutSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAthleteWorkoutMockMvc;

    private AthleteWorkout athleteWorkout;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AthleteWorkoutResource athleteWorkoutResource = new AthleteWorkoutResource(athleteWorkoutService);
        this.restAthleteWorkoutMockMvc = MockMvcBuilders.standaloneSetup(athleteWorkoutResource)
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
    public static AthleteWorkout createEntity(EntityManager em) {
        AthleteWorkout athleteWorkout = new AthleteWorkout()
            .note(DEFAULT_NOTE);
        // Add required entity
        Workout workout = WorkoutResourceIntTest.createEntity(em);
        em.persist(workout);
        em.flush();
        athleteWorkout.setWorkout(workout);
        return athleteWorkout;
    }

    @Before
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athleteWorkoutDTO)))
            .andExpect(status().isCreated());

        // Validate the AthleteWorkout in the database
        List<AthleteWorkout> athleteWorkoutList = athleteWorkoutRepository.findAll();
        assertThat(athleteWorkoutList).hasSize(databaseSizeBeforeCreate + 1);
        AthleteWorkout testAthleteWorkout = athleteWorkoutList.get(athleteWorkoutList.size() - 1);
        assertThat(testAthleteWorkout.getNote()).isEqualTo(DEFAULT_NOTE);

        // Validate the AthleteWorkout in Elasticsearch
        verify(mockAthleteWorkoutSearchRepository, times(1)).save(testAthleteWorkout);
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athleteWorkoutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteWorkout in the database
        List<AthleteWorkout> athleteWorkoutList = athleteWorkoutRepository.findAll();
        assertThat(athleteWorkoutList).hasSize(databaseSizeBeforeCreate);

        // Validate the AthleteWorkout in Elasticsearch
        verify(mockAthleteWorkoutSearchRepository, times(0)).save(athleteWorkout);
    }

    @Test
    @Transactional
    public void getAllAthleteWorkouts() throws Exception {
        // Initialize the database
        athleteWorkoutRepository.saveAndFlush(athleteWorkout);

        // Get all the athleteWorkoutList
        restAthleteWorkoutMockMvc.perform(get("/api/athlete-workouts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(athleteWorkout.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getAthleteWorkout() throws Exception {
        // Initialize the database
        athleteWorkoutRepository.saveAndFlush(athleteWorkout);

        // Get the athleteWorkout
        restAthleteWorkoutMockMvc.perform(get("/api/athlete-workouts/{id}", athleteWorkout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(athleteWorkout.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athleteWorkoutDTO)))
            .andExpect(status().isOk());

        // Validate the AthleteWorkout in the database
        List<AthleteWorkout> athleteWorkoutList = athleteWorkoutRepository.findAll();
        assertThat(athleteWorkoutList).hasSize(databaseSizeBeforeUpdate);
        AthleteWorkout testAthleteWorkout = athleteWorkoutList.get(athleteWorkoutList.size() - 1);
        assertThat(testAthleteWorkout.getNote()).isEqualTo(UPDATED_NOTE);

        // Validate the AthleteWorkout in Elasticsearch
        verify(mockAthleteWorkoutSearchRepository, times(1)).save(testAthleteWorkout);
    }

    @Test
    @Transactional
    public void updateNonExistingAthleteWorkout() throws Exception {
        int databaseSizeBeforeUpdate = athleteWorkoutRepository.findAll().size();

        // Create the AthleteWorkout
        AthleteWorkoutDTO athleteWorkoutDTO = athleteWorkoutMapper.toDto(athleteWorkout);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAthleteWorkoutMockMvc.perform(put("/api/athlete-workouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athleteWorkoutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteWorkout in the database
        List<AthleteWorkout> athleteWorkoutList = athleteWorkoutRepository.findAll();
        assertThat(athleteWorkoutList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AthleteWorkout in Elasticsearch
        verify(mockAthleteWorkoutSearchRepository, times(0)).save(athleteWorkout);
    }

    @Test
    @Transactional
    public void deleteAthleteWorkout() throws Exception {
        // Initialize the database
        athleteWorkoutRepository.saveAndFlush(athleteWorkout);

        int databaseSizeBeforeDelete = athleteWorkoutRepository.findAll().size();

        // Get the athleteWorkout
        restAthleteWorkoutMockMvc.perform(delete("/api/athlete-workouts/{id}", athleteWorkout.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AthleteWorkout> athleteWorkoutList = athleteWorkoutRepository.findAll();
        assertThat(athleteWorkoutList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AthleteWorkout in Elasticsearch
        verify(mockAthleteWorkoutSearchRepository, times(1)).deleteById(athleteWorkout.getId());
    }

    @Test
    @Transactional
    public void searchAthleteWorkout() throws Exception {
        // Initialize the database
        athleteWorkoutRepository.saveAndFlush(athleteWorkout);
        when(mockAthleteWorkoutSearchRepository.search(queryStringQuery("id:" + athleteWorkout.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(athleteWorkout), PageRequest.of(0, 1), 1));
        // Search the athleteWorkout
        restAthleteWorkoutMockMvc.perform(get("/api/_search/athlete-workouts?query=id:" + athleteWorkout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(athleteWorkout.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteWorkout.class);
        AthleteWorkout athleteWorkout1 = new AthleteWorkout();
        athleteWorkout1.setId(1L);
        AthleteWorkout athleteWorkout2 = new AthleteWorkout();
        athleteWorkout2.setId(athleteWorkout1.getId());
        assertThat(athleteWorkout1).isEqualTo(athleteWorkout2);
        athleteWorkout2.setId(2L);
        assertThat(athleteWorkout1).isNotEqualTo(athleteWorkout2);
        athleteWorkout1.setId(null);
        assertThat(athleteWorkout1).isNotEqualTo(athleteWorkout2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteWorkoutDTO.class);
        AthleteWorkoutDTO athleteWorkoutDTO1 = new AthleteWorkoutDTO();
        athleteWorkoutDTO1.setId(1L);
        AthleteWorkoutDTO athleteWorkoutDTO2 = new AthleteWorkoutDTO();
        assertThat(athleteWorkoutDTO1).isNotEqualTo(athleteWorkoutDTO2);
        athleteWorkoutDTO2.setId(athleteWorkoutDTO1.getId());
        assertThat(athleteWorkoutDTO1).isEqualTo(athleteWorkoutDTO2);
        athleteWorkoutDTO2.setId(2L);
        assertThat(athleteWorkoutDTO1).isNotEqualTo(athleteWorkoutDTO2);
        athleteWorkoutDTO1.setId(null);
        assertThat(athleteWorkoutDTO1).isNotEqualTo(athleteWorkoutDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(athleteWorkoutMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(athleteWorkoutMapper.fromId(null)).isNull();
    }
}
