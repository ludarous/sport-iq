package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.SportIqApp;
import com.sportiq.sportiq.config.TestSecurityConfiguration;
import com.sportiq.sportiq.domain.Sport;
import com.sportiq.sportiq.repository.SportRepository;
import com.sportiq.sportiq.service.SportService;
import com.sportiq.sportiq.service.dto.SportDTO;
import com.sportiq.sportiq.service.mapper.SportMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SportResource} REST controller.
 */
@SpringBootTest(classes = { SportIqApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SportResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private SportMapper sportMapper;

    @Autowired
    private SportService sportService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSportMockMvc;

    private Sport sport;

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
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sport createUpdatedEntity(EntityManager em) {
        Sport sport = new Sport()
            .name(UPDATED_NAME);
        return sport;
    }

    @BeforeEach
    public void initTest() {
        sport = createEntity(em);
    }

    @Test
    @Transactional
    public void createSport() throws Exception {
        int databaseSizeBeforeCreate = sportRepository.findAll().size();
        // Create the Sport
        SportDTO sportDTO = sportMapper.toDto(sport);
        restSportMockMvc.perform(post("/api/sports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sportDTO)))
            .andExpect(status().isCreated());

        // Validate the Sport in the database
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeCreate + 1);
        Sport testSport = sportList.get(sportList.size() - 1);
        assertThat(testSport.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sportRepository.findAll().size();

        // Create the Sport with an existing ID
        sport.setId(1L);
        SportDTO sportDTO = sportMapper.toDto(sport);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSportMockMvc.perform(post("/api/sports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sport in the database
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sportRepository.findAll().size();
        // set the field null
        sport.setName(null);

        // Create the Sport, which fails.
        SportDTO sportDTO = sportMapper.toDto(sport);


        restSportMockMvc.perform(post("/api/sports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sport.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getSport() throws Exception {
        // Initialize the database
        sportRepository.saveAndFlush(sport);

        // Get the sport
        restSportMockMvc.perform(get("/api/sports/{id}", sport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sport.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
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

        restSportMockMvc.perform(put("/api/sports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sportDTO)))
            .andExpect(status().isOk());

        // Validate the Sport in the database
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeUpdate);
        Sport testSport = sportList.get(sportList.size() - 1);
        assertThat(testSport.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSport() throws Exception {
        int databaseSizeBeforeUpdate = sportRepository.findAll().size();

        // Create the Sport
        SportDTO sportDTO = sportMapper.toDto(sport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSportMockMvc.perform(put("/api/sports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sport in the database
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSport() throws Exception {
        // Initialize the database
        sportRepository.saveAndFlush(sport);

        int databaseSizeBeforeDelete = sportRepository.findAll().size();

        // Delete the sport
        restSportMockMvc.perform(delete("/api/sports/{id}", sport.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sport> sportList = sportRepository.findAll();
        assertThat(sportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
