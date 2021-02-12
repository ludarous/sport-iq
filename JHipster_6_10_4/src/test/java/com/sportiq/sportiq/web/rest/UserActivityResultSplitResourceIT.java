package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.SportIqApp;
import com.sportiq.sportiq.config.TestSecurityConfiguration;
import com.sportiq.sportiq.domain.UserActivityResultSplit;
import com.sportiq.sportiq.domain.ActivityResultSplit;
import com.sportiq.sportiq.repository.UserActivityResultSplitRepository;
import com.sportiq.sportiq.service.UserActivityResultSplitService;
import com.sportiq.sportiq.service.dto.UserActivityResultSplitDTO;
import com.sportiq.sportiq.service.mapper.UserActivityResultSplitMapper;

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
 * Integration tests for the {@link UserActivityResultSplitResource} REST controller.
 */
@SpringBootTest(classes = { SportIqApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserActivityResultSplitResourceIT {

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final Float DEFAULT_COMPARE_VALUE = 1F;
    private static final Float UPDATED_COMPARE_VALUE = 2F;

    @Autowired
    private UserActivityResultSplitRepository userActivityResultSplitRepository;

    @Autowired
    private UserActivityResultSplitMapper userActivityResultSplitMapper;

    @Autowired
    private UserActivityResultSplitService userActivityResultSplitService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserActivityResultSplitMockMvc;

    private UserActivityResultSplit userActivityResultSplit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserActivityResultSplit createEntity(EntityManager em) {
        UserActivityResultSplit userActivityResultSplit = new UserActivityResultSplit()
            .value(DEFAULT_VALUE)
            .compareValue(DEFAULT_COMPARE_VALUE);
        // Add required entity
        ActivityResultSplit activityResultSplit;
        if (TestUtil.findAll(em, ActivityResultSplit.class).isEmpty()) {
            activityResultSplit = ActivityResultSplitResourceIT.createEntity(em);
            em.persist(activityResultSplit);
            em.flush();
        } else {
            activityResultSplit = TestUtil.findAll(em, ActivityResultSplit.class).get(0);
        }
        userActivityResultSplit.setActivityResultSplit(activityResultSplit);
        return userActivityResultSplit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserActivityResultSplit createUpdatedEntity(EntityManager em) {
        UserActivityResultSplit userActivityResultSplit = new UserActivityResultSplit()
            .value(UPDATED_VALUE)
            .compareValue(UPDATED_COMPARE_VALUE);
        // Add required entity
        ActivityResultSplit activityResultSplit;
        if (TestUtil.findAll(em, ActivityResultSplit.class).isEmpty()) {
            activityResultSplit = ActivityResultSplitResourceIT.createUpdatedEntity(em);
            em.persist(activityResultSplit);
            em.flush();
        } else {
            activityResultSplit = TestUtil.findAll(em, ActivityResultSplit.class).get(0);
        }
        userActivityResultSplit.setActivityResultSplit(activityResultSplit);
        return userActivityResultSplit;
    }

    @BeforeEach
    public void initTest() {
        userActivityResultSplit = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserActivityResultSplit() throws Exception {
        int databaseSizeBeforeCreate = userActivityResultSplitRepository.findAll().size();
        // Create the UserActivityResultSplit
        UserActivityResultSplitDTO userActivityResultSplitDTO = userActivityResultSplitMapper.toDto(userActivityResultSplit);
        restUserActivityResultSplitMockMvc.perform(post("/api/user-activity-result-splits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActivityResultSplitDTO)))
            .andExpect(status().isCreated());

        // Validate the UserActivityResultSplit in the database
        List<UserActivityResultSplit> userActivityResultSplitList = userActivityResultSplitRepository.findAll();
        assertThat(userActivityResultSplitList).hasSize(databaseSizeBeforeCreate + 1);
        UserActivityResultSplit testUserActivityResultSplit = userActivityResultSplitList.get(userActivityResultSplitList.size() - 1);
        assertThat(testUserActivityResultSplit.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testUserActivityResultSplit.getCompareValue()).isEqualTo(DEFAULT_COMPARE_VALUE);
    }

    @Test
    @Transactional
    public void createUserActivityResultSplitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userActivityResultSplitRepository.findAll().size();

        // Create the UserActivityResultSplit with an existing ID
        userActivityResultSplit.setId(1L);
        UserActivityResultSplitDTO userActivityResultSplitDTO = userActivityResultSplitMapper.toDto(userActivityResultSplit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserActivityResultSplitMockMvc.perform(post("/api/user-activity-result-splits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActivityResultSplitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserActivityResultSplit in the database
        List<UserActivityResultSplit> userActivityResultSplitList = userActivityResultSplitRepository.findAll();
        assertThat(userActivityResultSplitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserActivityResultSplits() throws Exception {
        // Initialize the database
        userActivityResultSplitRepository.saveAndFlush(userActivityResultSplit);

        // Get all the userActivityResultSplitList
        restUserActivityResultSplitMockMvc.perform(get("/api/user-activity-result-splits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userActivityResultSplit.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].compareValue").value(hasItem(DEFAULT_COMPARE_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getUserActivityResultSplit() throws Exception {
        // Initialize the database
        userActivityResultSplitRepository.saveAndFlush(userActivityResultSplit);

        // Get the userActivityResultSplit
        restUserActivityResultSplitMockMvc.perform(get("/api/user-activity-result-splits/{id}", userActivityResultSplit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userActivityResultSplit.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.compareValue").value(DEFAULT_COMPARE_VALUE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserActivityResultSplit() throws Exception {
        // Get the userActivityResultSplit
        restUserActivityResultSplitMockMvc.perform(get("/api/user-activity-result-splits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserActivityResultSplit() throws Exception {
        // Initialize the database
        userActivityResultSplitRepository.saveAndFlush(userActivityResultSplit);

        int databaseSizeBeforeUpdate = userActivityResultSplitRepository.findAll().size();

        // Update the userActivityResultSplit
        UserActivityResultSplit updatedUserActivityResultSplit = userActivityResultSplitRepository.findById(userActivityResultSplit.getId()).get();
        // Disconnect from session so that the updates on updatedUserActivityResultSplit are not directly saved in db
        em.detach(updatedUserActivityResultSplit);
        updatedUserActivityResultSplit
            .value(UPDATED_VALUE)
            .compareValue(UPDATED_COMPARE_VALUE);
        UserActivityResultSplitDTO userActivityResultSplitDTO = userActivityResultSplitMapper.toDto(updatedUserActivityResultSplit);

        restUserActivityResultSplitMockMvc.perform(put("/api/user-activity-result-splits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActivityResultSplitDTO)))
            .andExpect(status().isOk());

        // Validate the UserActivityResultSplit in the database
        List<UserActivityResultSplit> userActivityResultSplitList = userActivityResultSplitRepository.findAll();
        assertThat(userActivityResultSplitList).hasSize(databaseSizeBeforeUpdate);
        UserActivityResultSplit testUserActivityResultSplit = userActivityResultSplitList.get(userActivityResultSplitList.size() - 1);
        assertThat(testUserActivityResultSplit.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testUserActivityResultSplit.getCompareValue()).isEqualTo(UPDATED_COMPARE_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserActivityResultSplit() throws Exception {
        int databaseSizeBeforeUpdate = userActivityResultSplitRepository.findAll().size();

        // Create the UserActivityResultSplit
        UserActivityResultSplitDTO userActivityResultSplitDTO = userActivityResultSplitMapper.toDto(userActivityResultSplit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserActivityResultSplitMockMvc.perform(put("/api/user-activity-result-splits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userActivityResultSplitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserActivityResultSplit in the database
        List<UserActivityResultSplit> userActivityResultSplitList = userActivityResultSplitRepository.findAll();
        assertThat(userActivityResultSplitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserActivityResultSplit() throws Exception {
        // Initialize the database
        userActivityResultSplitRepository.saveAndFlush(userActivityResultSplit);

        int databaseSizeBeforeDelete = userActivityResultSplitRepository.findAll().size();

        // Delete the userActivityResultSplit
        restUserActivityResultSplitMockMvc.perform(delete("/api/user-activity-result-splits/{id}", userActivityResultSplit.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserActivityResultSplit> userActivityResultSplitList = userActivityResultSplitRepository.findAll();
        assertThat(userActivityResultSplitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
