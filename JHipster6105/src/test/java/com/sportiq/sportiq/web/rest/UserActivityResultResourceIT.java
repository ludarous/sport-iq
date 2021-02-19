package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.JHipster6105App;
import com.sportiq.sportiq.domain.UserActivityResult;
import com.sportiq.sportiq.domain.ActivityResult;
import com.sportiq.sportiq.repository.UserActivityResultRepository;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserActivityResultResource} REST controller.
 */
@SpringBootTest(classes = JHipster6105App.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserActivityResultResourceIT {

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final Float DEFAULT_COMPARE_VALUE = 1F;
    private static final Float UPDATED_COMPARE_VALUE = 2F;

    @Autowired
    private UserActivityResultRepository userActivityResultRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserActivityResultMockMvc;

    private UserActivityResult userActivityResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserActivityResult createEntity(EntityManager em) {
        UserActivityResult userActivityResult = new UserActivityResult()
            .value(DEFAULT_VALUE)
            .compareValue(DEFAULT_COMPARE_VALUE);
        // Add required entity
        ActivityResult activityResult;
        if (TestUtil.findAll(em, ActivityResult.class).isEmpty()) {
            activityResult = ActivityResultResourceIT.createEntity(em);
            em.persist(activityResult);
            em.flush();
        } else {
            activityResult = TestUtil.findAll(em, ActivityResult.class).get(0);
        }
        userActivityResult.setActivityResult(activityResult);
        return userActivityResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserActivityResult createUpdatedEntity(EntityManager em) {
        UserActivityResult userActivityResult = new UserActivityResult()
            .value(UPDATED_VALUE)
            .compareValue(UPDATED_COMPARE_VALUE);
        // Add required entity
        ActivityResult activityResult;
        if (TestUtil.findAll(em, ActivityResult.class).isEmpty()) {
            activityResult = ActivityResultResourceIT.createUpdatedEntity(em);
            em.persist(activityResult);
            em.flush();
        } else {
            activityResult = TestUtil.findAll(em, ActivityResult.class).get(0);
        }
        userActivityResult.setActivityResult(activityResult);
        return userActivityResult;
    }

    @BeforeEach
    public void initTest() {
        userActivityResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserActivityResult() throws Exception {
        int databaseSizeBeforeCreate = userActivityResultRepository.findAll().size();
        // Create the UserActivityResult
        restUserActivityResultMockMvc.perform(post("/api/user-activity-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActivityResult)))
            .andExpect(status().isCreated());

        // Validate the UserActivityResult in the database
        List<UserActivityResult> userActivityResultList = userActivityResultRepository.findAll();
        assertThat(userActivityResultList).hasSize(databaseSizeBeforeCreate + 1);
        UserActivityResult testUserActivityResult = userActivityResultList.get(userActivityResultList.size() - 1);
        assertThat(testUserActivityResult.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testUserActivityResult.getCompareValue()).isEqualTo(DEFAULT_COMPARE_VALUE);
    }

    @Test
    @Transactional
    public void createUserActivityResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userActivityResultRepository.findAll().size();

        // Create the UserActivityResult with an existing ID
        userActivityResult.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserActivityResultMockMvc.perform(post("/api/user-activity-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActivityResult)))
            .andExpect(status().isBadRequest());

        // Validate the UserActivityResult in the database
        List<UserActivityResult> userActivityResultList = userActivityResultRepository.findAll();
        assertThat(userActivityResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserActivityResults() throws Exception {
        // Initialize the database
        userActivityResultRepository.saveAndFlush(userActivityResult);

        // Get all the userActivityResultList
        restUserActivityResultMockMvc.perform(get("/api/user-activity-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userActivityResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].compareValue").value(hasItem(DEFAULT_COMPARE_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getUserActivityResult() throws Exception {
        // Initialize the database
        userActivityResultRepository.saveAndFlush(userActivityResult);

        // Get the userActivityResult
        restUserActivityResultMockMvc.perform(get("/api/user-activity-results/{id}", userActivityResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userActivityResult.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.compareValue").value(DEFAULT_COMPARE_VALUE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserActivityResult() throws Exception {
        // Get the userActivityResult
        restUserActivityResultMockMvc.perform(get("/api/user-activity-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserActivityResult() throws Exception {
        // Initialize the database
        userActivityResultRepository.saveAndFlush(userActivityResult);

        int databaseSizeBeforeUpdate = userActivityResultRepository.findAll().size();

        // Update the userActivityResult
        UserActivityResult updatedUserActivityResult = userActivityResultRepository.findById(userActivityResult.getId()).get();
        // Disconnect from session so that the updates on updatedUserActivityResult are not directly saved in db
        em.detach(updatedUserActivityResult);
        updatedUserActivityResult
            .value(UPDATED_VALUE)
            .compareValue(UPDATED_COMPARE_VALUE);

        restUserActivityResultMockMvc.perform(put("/api/user-activity-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserActivityResult)))
            .andExpect(status().isOk());

        // Validate the UserActivityResult in the database
        List<UserActivityResult> userActivityResultList = userActivityResultRepository.findAll();
        assertThat(userActivityResultList).hasSize(databaseSizeBeforeUpdate);
        UserActivityResult testUserActivityResult = userActivityResultList.get(userActivityResultList.size() - 1);
        assertThat(testUserActivityResult.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testUserActivityResult.getCompareValue()).isEqualTo(UPDATED_COMPARE_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserActivityResult() throws Exception {
        int databaseSizeBeforeUpdate = userActivityResultRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserActivityResultMockMvc.perform(put("/api/user-activity-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActivityResult)))
            .andExpect(status().isBadRequest());

        // Validate the UserActivityResult in the database
        List<UserActivityResult> userActivityResultList = userActivityResultRepository.findAll();
        assertThat(userActivityResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserActivityResult() throws Exception {
        // Initialize the database
        userActivityResultRepository.saveAndFlush(userActivityResult);

        int databaseSizeBeforeDelete = userActivityResultRepository.findAll().size();

        // Delete the userActivityResult
        restUserActivityResultMockMvc.perform(delete("/api/user-activity-results/{id}", userActivityResult.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserActivityResult> userActivityResultList = userActivityResultRepository.findAll();
        assertThat(userActivityResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
