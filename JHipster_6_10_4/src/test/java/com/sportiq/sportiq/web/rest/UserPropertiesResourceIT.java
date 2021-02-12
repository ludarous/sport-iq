package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.SportIqApp;
import com.sportiq.sportiq.config.TestSecurityConfiguration;
import com.sportiq.sportiq.domain.UserProperties;
import com.sportiq.sportiq.repository.UserPropertiesRepository;
import com.sportiq.sportiq.service.UserPropertiesService;
import com.sportiq.sportiq.service.dto.UserPropertiesDTO;
import com.sportiq.sportiq.service.mapper.UserPropertiesMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.sportiq.sportiq.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sportiq.sportiq.domain.enumeration.Sex;
/**
 * Integration tests for the {@link UserPropertiesResource} REST controller.
 */
@SpringBootTest(classes = { SportIqApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserPropertiesResourceIT {

    private static final ZonedDateTime DEFAULT_BIRTH_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BIRTH_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final Sex DEFAULT_SEX = Sex.MALE;
    private static final Sex UPDATED_SEX = Sex.FEMALE;

    @Autowired
    private UserPropertiesRepository userPropertiesRepository;

    @Autowired
    private UserPropertiesMapper userPropertiesMapper;

    @Autowired
    private UserPropertiesService userPropertiesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserPropertiesMockMvc;

    private UserProperties userProperties;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProperties createEntity(EntityManager em) {
        UserProperties userProperties = new UserProperties()
            .birthDate(DEFAULT_BIRTH_DATE)
            .phone(DEFAULT_PHONE)
            .nationality(DEFAULT_NATIONALITY)
            .sex(DEFAULT_SEX);
        return userProperties;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProperties createUpdatedEntity(EntityManager em) {
        UserProperties userProperties = new UserProperties()
            .birthDate(UPDATED_BIRTH_DATE)
            .phone(UPDATED_PHONE)
            .nationality(UPDATED_NATIONALITY)
            .sex(UPDATED_SEX);
        return userProperties;
    }

    @BeforeEach
    public void initTest() {
        userProperties = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProperties() throws Exception {
        int databaseSizeBeforeCreate = userPropertiesRepository.findAll().size();
        // Create the UserProperties
        UserPropertiesDTO userPropertiesDTO = userPropertiesMapper.toDto(userProperties);
        restUserPropertiesMockMvc.perform(post("/api/user-properties").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPropertiesDTO)))
            .andExpect(status().isCreated());

        // Validate the UserProperties in the database
        List<UserProperties> userPropertiesList = userPropertiesRepository.findAll();
        assertThat(userPropertiesList).hasSize(databaseSizeBeforeCreate + 1);
        UserProperties testUserProperties = userPropertiesList.get(userPropertiesList.size() - 1);
        assertThat(testUserProperties.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testUserProperties.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testUserProperties.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testUserProperties.getSex()).isEqualTo(DEFAULT_SEX);
    }

    @Test
    @Transactional
    public void createUserPropertiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPropertiesRepository.findAll().size();

        // Create the UserProperties with an existing ID
        userProperties.setId(1L);
        UserPropertiesDTO userPropertiesDTO = userPropertiesMapper.toDto(userProperties);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPropertiesMockMvc.perform(post("/api/user-properties").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPropertiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProperties in the database
        List<UserProperties> userPropertiesList = userPropertiesRepository.findAll();
        assertThat(userPropertiesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserProperties() throws Exception {
        // Initialize the database
        userPropertiesRepository.saveAndFlush(userProperties);

        // Get all the userPropertiesList
        restUserPropertiesMockMvc.perform(get("/api/user-properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProperties.getId().intValue())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(sameInstant(DEFAULT_BIRTH_DATE))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())));
    }
    
    @Test
    @Transactional
    public void getUserProperties() throws Exception {
        // Initialize the database
        userPropertiesRepository.saveAndFlush(userProperties);

        // Get the userProperties
        restUserPropertiesMockMvc.perform(get("/api/user-properties/{id}", userProperties.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userProperties.getId().intValue()))
            .andExpect(jsonPath("$.birthDate").value(sameInstant(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUserProperties() throws Exception {
        // Get the userProperties
        restUserPropertiesMockMvc.perform(get("/api/user-properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProperties() throws Exception {
        // Initialize the database
        userPropertiesRepository.saveAndFlush(userProperties);

        int databaseSizeBeforeUpdate = userPropertiesRepository.findAll().size();

        // Update the userProperties
        UserProperties updatedUserProperties = userPropertiesRepository.findById(userProperties.getId()).get();
        // Disconnect from session so that the updates on updatedUserProperties are not directly saved in db
        em.detach(updatedUserProperties);
        updatedUserProperties
            .birthDate(UPDATED_BIRTH_DATE)
            .phone(UPDATED_PHONE)
            .nationality(UPDATED_NATIONALITY)
            .sex(UPDATED_SEX);
        UserPropertiesDTO userPropertiesDTO = userPropertiesMapper.toDto(updatedUserProperties);

        restUserPropertiesMockMvc.perform(put("/api/user-properties").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPropertiesDTO)))
            .andExpect(status().isOk());

        // Validate the UserProperties in the database
        List<UserProperties> userPropertiesList = userPropertiesRepository.findAll();
        assertThat(userPropertiesList).hasSize(databaseSizeBeforeUpdate);
        UserProperties testUserProperties = userPropertiesList.get(userPropertiesList.size() - 1);
        assertThat(testUserProperties.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testUserProperties.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testUserProperties.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testUserProperties.getSex()).isEqualTo(UPDATED_SEX);
    }

    @Test
    @Transactional
    public void updateNonExistingUserProperties() throws Exception {
        int databaseSizeBeforeUpdate = userPropertiesRepository.findAll().size();

        // Create the UserProperties
        UserPropertiesDTO userPropertiesDTO = userPropertiesMapper.toDto(userProperties);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPropertiesMockMvc.perform(put("/api/user-properties").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPropertiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProperties in the database
        List<UserProperties> userPropertiesList = userPropertiesRepository.findAll();
        assertThat(userPropertiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserProperties() throws Exception {
        // Initialize the database
        userPropertiesRepository.saveAndFlush(userProperties);

        int databaseSizeBeforeDelete = userPropertiesRepository.findAll().size();

        // Delete the userProperties
        restUserPropertiesMockMvc.perform(delete("/api/user-properties/{id}", userProperties.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserProperties> userPropertiesList = userPropertiesRepository.findAll();
        assertThat(userPropertiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
