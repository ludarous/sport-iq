package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.SportIqApp;
import com.sportiq.sportiq.config.TestSecurityConfiguration;
import com.sportiq.sportiq.domain.ActivityResultSplit;
import com.sportiq.sportiq.repository.ActivityResultSplitRepository;
import com.sportiq.sportiq.service.ActivityResultSplitService;
import com.sportiq.sportiq.service.dto.ActivityResultSplitDTO;
import com.sportiq.sportiq.service.mapper.ActivityResultSplitMapper;

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
 * Integration tests for the {@link ActivityResultSplitResource} REST controller.
 */
@SpringBootTest(classes = { SportIqApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ActivityResultSplitResourceIT {

    private static final Float DEFAULT_SPLIT_VALUE = 1F;
    private static final Float UPDATED_SPLIT_VALUE = 2F;

    @Autowired
    private ActivityResultSplitRepository activityResultSplitRepository;

    @Autowired
    private ActivityResultSplitMapper activityResultSplitMapper;

    @Autowired
    private ActivityResultSplitService activityResultSplitService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityResultSplitMockMvc;

    private ActivityResultSplit activityResultSplit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityResultSplit createEntity(EntityManager em) {
        ActivityResultSplit activityResultSplit = new ActivityResultSplit()
            .splitValue(DEFAULT_SPLIT_VALUE);
        return activityResultSplit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityResultSplit createUpdatedEntity(EntityManager em) {
        ActivityResultSplit activityResultSplit = new ActivityResultSplit()
            .splitValue(UPDATED_SPLIT_VALUE);
        return activityResultSplit;
    }

    @BeforeEach
    public void initTest() {
        activityResultSplit = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivityResultSplit() throws Exception {
        int databaseSizeBeforeCreate = activityResultSplitRepository.findAll().size();
        // Create the ActivityResultSplit
        ActivityResultSplitDTO activityResultSplitDTO = activityResultSplitMapper.toDto(activityResultSplit);
        restActivityResultSplitMockMvc.perform(post("/api/activity-result-splits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityResultSplitDTO)))
            .andExpect(status().isCreated());

        // Validate the ActivityResultSplit in the database
        List<ActivityResultSplit> activityResultSplitList = activityResultSplitRepository.findAll();
        assertThat(activityResultSplitList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityResultSplit testActivityResultSplit = activityResultSplitList.get(activityResultSplitList.size() - 1);
        assertThat(testActivityResultSplit.getSplitValue()).isEqualTo(DEFAULT_SPLIT_VALUE);
    }

    @Test
    @Transactional
    public void createActivityResultSplitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityResultSplitRepository.findAll().size();

        // Create the ActivityResultSplit with an existing ID
        activityResultSplit.setId(1L);
        ActivityResultSplitDTO activityResultSplitDTO = activityResultSplitMapper.toDto(activityResultSplit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityResultSplitMockMvc.perform(post("/api/activity-result-splits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityResultSplitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityResultSplit in the database
        List<ActivityResultSplit> activityResultSplitList = activityResultSplitRepository.findAll();
        assertThat(activityResultSplitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllActivityResultSplits() throws Exception {
        // Initialize the database
        activityResultSplitRepository.saveAndFlush(activityResultSplit);

        // Get all the activityResultSplitList
        restActivityResultSplitMockMvc.perform(get("/api/activity-result-splits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityResultSplit.getId().intValue())))
            .andExpect(jsonPath("$.[*].splitValue").value(hasItem(DEFAULT_SPLIT_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getActivityResultSplit() throws Exception {
        // Initialize the database
        activityResultSplitRepository.saveAndFlush(activityResultSplit);

        // Get the activityResultSplit
        restActivityResultSplitMockMvc.perform(get("/api/activity-result-splits/{id}", activityResultSplit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activityResultSplit.getId().intValue()))
            .andExpect(jsonPath("$.splitValue").value(DEFAULT_SPLIT_VALUE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingActivityResultSplit() throws Exception {
        // Get the activityResultSplit
        restActivityResultSplitMockMvc.perform(get("/api/activity-result-splits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivityResultSplit() throws Exception {
        // Initialize the database
        activityResultSplitRepository.saveAndFlush(activityResultSplit);

        int databaseSizeBeforeUpdate = activityResultSplitRepository.findAll().size();

        // Update the activityResultSplit
        ActivityResultSplit updatedActivityResultSplit = activityResultSplitRepository.findById(activityResultSplit.getId()).get();
        // Disconnect from session so that the updates on updatedActivityResultSplit are not directly saved in db
        em.detach(updatedActivityResultSplit);
        updatedActivityResultSplit
            .splitValue(UPDATED_SPLIT_VALUE);
        ActivityResultSplitDTO activityResultSplitDTO = activityResultSplitMapper.toDto(updatedActivityResultSplit);

        restActivityResultSplitMockMvc.perform(put("/api/activity-result-splits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityResultSplitDTO)))
            .andExpect(status().isOk());

        // Validate the ActivityResultSplit in the database
        List<ActivityResultSplit> activityResultSplitList = activityResultSplitRepository.findAll();
        assertThat(activityResultSplitList).hasSize(databaseSizeBeforeUpdate);
        ActivityResultSplit testActivityResultSplit = activityResultSplitList.get(activityResultSplitList.size() - 1);
        assertThat(testActivityResultSplit.getSplitValue()).isEqualTo(UPDATED_SPLIT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingActivityResultSplit() throws Exception {
        int databaseSizeBeforeUpdate = activityResultSplitRepository.findAll().size();

        // Create the ActivityResultSplit
        ActivityResultSplitDTO activityResultSplitDTO = activityResultSplitMapper.toDto(activityResultSplit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityResultSplitMockMvc.perform(put("/api/activity-result-splits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityResultSplitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityResultSplit in the database
        List<ActivityResultSplit> activityResultSplitList = activityResultSplitRepository.findAll();
        assertThat(activityResultSplitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActivityResultSplit() throws Exception {
        // Initialize the database
        activityResultSplitRepository.saveAndFlush(activityResultSplit);

        int databaseSizeBeforeDelete = activityResultSplitRepository.findAll().size();

        // Delete the activityResultSplit
        restActivityResultSplitMockMvc.perform(delete("/api/activity-result-splits/{id}", activityResultSplit.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivityResultSplit> activityResultSplitList = activityResultSplitRepository.findAll();
        assertThat(activityResultSplitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
