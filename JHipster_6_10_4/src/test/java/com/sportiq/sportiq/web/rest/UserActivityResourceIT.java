package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.SportIqApp;
import com.sportiq.sportiq.config.TestSecurityConfiguration;
import com.sportiq.sportiq.domain.UserActivity;
import com.sportiq.sportiq.domain.Activity;
import com.sportiq.sportiq.repository.UserActivityRepository;
import com.sportiq.sportiq.service.UserActivityService;
import com.sportiq.sportiq.service.dto.UserActivityDTO;
import com.sportiq.sportiq.service.mapper.UserActivityMapper;

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

/**
 * Integration tests for the {@link UserActivityResource} REST controller.
 */
@SpringBootTest(classes = { SportIqApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserActivityResourceIT {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private UserActivityMapper userActivityMapper;

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserActivityMockMvc;

    private UserActivity userActivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserActivity createEntity(EntityManager em) {
        UserActivity userActivity = new UserActivity()
            .note(DEFAULT_NOTE)
            .date(DEFAULT_DATE);
        // Add required entity
        Activity activity;
        if (TestUtil.findAll(em, Activity.class).isEmpty()) {
            activity = ActivityResourceIT.createEntity(em);
            em.persist(activity);
            em.flush();
        } else {
            activity = TestUtil.findAll(em, Activity.class).get(0);
        }
        userActivity.setActivity(activity);
        return userActivity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserActivity createUpdatedEntity(EntityManager em) {
        UserActivity userActivity = new UserActivity()
            .note(UPDATED_NOTE)
            .date(UPDATED_DATE);
        // Add required entity
        Activity activity;
        if (TestUtil.findAll(em, Activity.class).isEmpty()) {
            activity = ActivityResourceIT.createUpdatedEntity(em);
            em.persist(activity);
            em.flush();
        } else {
            activity = TestUtil.findAll(em, Activity.class).get(0);
        }
        userActivity.setActivity(activity);
        return userActivity;
    }

    @BeforeEach
    public void initTest() {
        userActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserActivity() throws Exception {
        int databaseSizeBeforeCreate = userActivityRepository.findAll().size();
        // Create the UserActivity
        UserActivityDTO userActivityDTO = userActivityMapper.toDto(userActivity);
        restUserActivityMockMvc.perform(post("/api/user-activities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeCreate + 1);
        UserActivity testUserActivity = userActivityList.get(userActivityList.size() - 1);
        assertThat(testUserActivity.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testUserActivity.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createUserActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userActivityRepository.findAll().size();

        // Create the UserActivity with an existing ID
        userActivity.setId(1L);
        UserActivityDTO userActivityDTO = userActivityMapper.toDto(userActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserActivityMockMvc.perform(post("/api/user-activities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserActivities() throws Exception {
        // Initialize the database
        userActivityRepository.saveAndFlush(userActivity);

        // Get all the userActivityList
        restUserActivityMockMvc.perform(get("/api/user-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }
    
    @Test
    @Transactional
    public void getUserActivity() throws Exception {
        // Initialize the database
        userActivityRepository.saveAndFlush(userActivity);

        // Get the userActivity
        restUserActivityMockMvc.perform(get("/api/user-activities/{id}", userActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userActivity.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingUserActivity() throws Exception {
        // Get the userActivity
        restUserActivityMockMvc.perform(get("/api/user-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserActivity() throws Exception {
        // Initialize the database
        userActivityRepository.saveAndFlush(userActivity);

        int databaseSizeBeforeUpdate = userActivityRepository.findAll().size();

        // Update the userActivity
        UserActivity updatedUserActivity = userActivityRepository.findById(userActivity.getId()).get();
        // Disconnect from session so that the updates on updatedUserActivity are not directly saved in db
        em.detach(updatedUserActivity);
        updatedUserActivity
            .note(UPDATED_NOTE)
            .date(UPDATED_DATE);
        UserActivityDTO userActivityDTO = userActivityMapper.toDto(updatedUserActivity);

        restUserActivityMockMvc.perform(put("/api/user-activities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActivityDTO)))
            .andExpect(status().isOk());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeUpdate);
        UserActivity testUserActivity = userActivityList.get(userActivityList.size() - 1);
        assertThat(testUserActivity.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testUserActivity.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserActivity() throws Exception {
        int databaseSizeBeforeUpdate = userActivityRepository.findAll().size();

        // Create the UserActivity
        UserActivityDTO userActivityDTO = userActivityMapper.toDto(userActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserActivityMockMvc.perform(put("/api/user-activities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserActivity() throws Exception {
        // Initialize the database
        userActivityRepository.saveAndFlush(userActivity);

        int databaseSizeBeforeDelete = userActivityRepository.findAll().size();

        // Delete the userActivity
        restUserActivityMockMvc.perform(delete("/api/user-activities/{id}", userActivity.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
