package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.SportIqApp;
import com.sportiq.sportiq.config.TestSecurityConfiguration;
import com.sportiq.sportiq.domain.ActivityResult;
import com.sportiq.sportiq.repository.ActivityResultRepository;
import com.sportiq.sportiq.service.ActivityResultService;
import com.sportiq.sportiq.service.dto.ActivityResultDTO;
import com.sportiq.sportiq.service.mapper.ActivityResultMapper;

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

import com.sportiq.sportiq.domain.enumeration.ResultType;
/**
 * Integration tests for the {@link ActivityResultResource} REST controller.
 */
@SpringBootTest(classes = { SportIqApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ActivityResultResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ResultType DEFAULT_RESULT_TYPE = ResultType.LESS_IS_BETTER;
    private static final ResultType UPDATED_RESULT_TYPE = ResultType.MORE_IS_BETTER;

    private static final Float DEFAULT_RATING_WEIGHT = 1F;
    private static final Float UPDATED_RATING_WEIGHT = 2F;

    private static final Boolean DEFAULT_MAIN_RESULT = false;
    private static final Boolean UPDATED_MAIN_RESULT = true;

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final Float DEFAULT_IRV_BEST = 1F;
    private static final Float UPDATED_IRV_BEST = 2F;

    private static final Float DEFAULT_IRV_WORST = 1F;
    private static final Float UPDATED_IRV_WORST = 2F;

    @Autowired
    private ActivityResultRepository activityResultRepository;

    @Autowired
    private ActivityResultMapper activityResultMapper;

    @Autowired
    private ActivityResultService activityResultService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityResultMockMvc;

    private ActivityResult activityResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityResult createEntity(EntityManager em) {
        ActivityResult activityResult = new ActivityResult()
            .name(DEFAULT_NAME)
            .resultType(DEFAULT_RESULT_TYPE)
            .ratingWeight(DEFAULT_RATING_WEIGHT)
            .mainResult(DEFAULT_MAIN_RESULT)
            .order(DEFAULT_ORDER)
            .irvBest(DEFAULT_IRV_BEST)
            .irvWorst(DEFAULT_IRV_WORST);
        return activityResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityResult createUpdatedEntity(EntityManager em) {
        ActivityResult activityResult = new ActivityResult()
            .name(UPDATED_NAME)
            .resultType(UPDATED_RESULT_TYPE)
            .ratingWeight(UPDATED_RATING_WEIGHT)
            .mainResult(UPDATED_MAIN_RESULT)
            .order(UPDATED_ORDER)
            .irvBest(UPDATED_IRV_BEST)
            .irvWorst(UPDATED_IRV_WORST);
        return activityResult;
    }

    @BeforeEach
    public void initTest() {
        activityResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivityResult() throws Exception {
        int databaseSizeBeforeCreate = activityResultRepository.findAll().size();
        // Create the ActivityResult
        ActivityResultDTO activityResultDTO = activityResultMapper.toDto(activityResult);
        restActivityResultMockMvc.perform(post("/api/activity-results").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityResultDTO)))
            .andExpect(status().isCreated());

        // Validate the ActivityResult in the database
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityResult testActivityResult = activityResultList.get(activityResultList.size() - 1);
        assertThat(testActivityResult.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testActivityResult.getResultType()).isEqualTo(DEFAULT_RESULT_TYPE);
        assertThat(testActivityResult.getRatingWeight()).isEqualTo(DEFAULT_RATING_WEIGHT);
        assertThat(testActivityResult.isMainResult()).isEqualTo(DEFAULT_MAIN_RESULT);
        assertThat(testActivityResult.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testActivityResult.getIrvBest()).isEqualTo(DEFAULT_IRV_BEST);
        assertThat(testActivityResult.getIrvWorst()).isEqualTo(DEFAULT_IRV_WORST);
    }

    @Test
    @Transactional
    public void createActivityResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityResultRepository.findAll().size();

        // Create the ActivityResult with an existing ID
        activityResult.setId(1L);
        ActivityResultDTO activityResultDTO = activityResultMapper.toDto(activityResult);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityResultMockMvc.perform(post("/api/activity-results").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityResult in the database
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllActivityResults() throws Exception {
        // Initialize the database
        activityResultRepository.saveAndFlush(activityResult);

        // Get all the activityResultList
        restActivityResultMockMvc.perform(get("/api/activity-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].resultType").value(hasItem(DEFAULT_RESULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ratingWeight").value(hasItem(DEFAULT_RATING_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].mainResult").value(hasItem(DEFAULT_MAIN_RESULT.booleanValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].irvBest").value(hasItem(DEFAULT_IRV_BEST.doubleValue())))
            .andExpect(jsonPath("$.[*].irvWorst").value(hasItem(DEFAULT_IRV_WORST.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getActivityResult() throws Exception {
        // Initialize the database
        activityResultRepository.saveAndFlush(activityResult);

        // Get the activityResult
        restActivityResultMockMvc.perform(get("/api/activity-results/{id}", activityResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activityResult.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.resultType").value(DEFAULT_RESULT_TYPE.toString()))
            .andExpect(jsonPath("$.ratingWeight").value(DEFAULT_RATING_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.mainResult").value(DEFAULT_MAIN_RESULT.booleanValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.irvBest").value(DEFAULT_IRV_BEST.doubleValue()))
            .andExpect(jsonPath("$.irvWorst").value(DEFAULT_IRV_WORST.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingActivityResult() throws Exception {
        // Get the activityResult
        restActivityResultMockMvc.perform(get("/api/activity-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivityResult() throws Exception {
        // Initialize the database
        activityResultRepository.saveAndFlush(activityResult);

        int databaseSizeBeforeUpdate = activityResultRepository.findAll().size();

        // Update the activityResult
        ActivityResult updatedActivityResult = activityResultRepository.findById(activityResult.getId()).get();
        // Disconnect from session so that the updates on updatedActivityResult are not directly saved in db
        em.detach(updatedActivityResult);
        updatedActivityResult
            .name(UPDATED_NAME)
            .resultType(UPDATED_RESULT_TYPE)
            .ratingWeight(UPDATED_RATING_WEIGHT)
            .mainResult(UPDATED_MAIN_RESULT)
            .order(UPDATED_ORDER)
            .irvBest(UPDATED_IRV_BEST)
            .irvWorst(UPDATED_IRV_WORST);
        ActivityResultDTO activityResultDTO = activityResultMapper.toDto(updatedActivityResult);

        restActivityResultMockMvc.perform(put("/api/activity-results").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityResultDTO)))
            .andExpect(status().isOk());

        // Validate the ActivityResult in the database
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeUpdate);
        ActivityResult testActivityResult = activityResultList.get(activityResultList.size() - 1);
        assertThat(testActivityResult.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActivityResult.getResultType()).isEqualTo(UPDATED_RESULT_TYPE);
        assertThat(testActivityResult.getRatingWeight()).isEqualTo(UPDATED_RATING_WEIGHT);
        assertThat(testActivityResult.isMainResult()).isEqualTo(UPDATED_MAIN_RESULT);
        assertThat(testActivityResult.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testActivityResult.getIrvBest()).isEqualTo(UPDATED_IRV_BEST);
        assertThat(testActivityResult.getIrvWorst()).isEqualTo(UPDATED_IRV_WORST);
    }

    @Test
    @Transactional
    public void updateNonExistingActivityResult() throws Exception {
        int databaseSizeBeforeUpdate = activityResultRepository.findAll().size();

        // Create the ActivityResult
        ActivityResultDTO activityResultDTO = activityResultMapper.toDto(activityResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityResultMockMvc.perform(put("/api/activity-results").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activityResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityResult in the database
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActivityResult() throws Exception {
        // Initialize the database
        activityResultRepository.saveAndFlush(activityResult);

        int databaseSizeBeforeDelete = activityResultRepository.findAll().size();

        // Delete the activityResult
        restActivityResultMockMvc.perform(delete("/api/activity-results/{id}", activityResult.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivityResult> activityResultList = activityResultRepository.findAll();
        assertThat(activityResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
