package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.JHipster6105App;
import com.sportiq.sportiq.domain.BodyCharacteristics;
import com.sportiq.sportiq.repository.BodyCharacteristicsRepository;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BodyCharacteristicsResource} REST controller.
 */
@SpringBootTest(classes = JHipster6105App.class)
@AutoConfigureMockMvc
@WithMockUser
public class BodyCharacteristicsResourceIT {

    private static final Float DEFAULT_HEIGHT = 1F;
    private static final Float UPDATED_HEIGHT = 2F;

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BodyCharacteristicsRepository bodyCharacteristicsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBodyCharacteristicsMockMvc;

    private BodyCharacteristics bodyCharacteristics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BodyCharacteristics createEntity(EntityManager em) {
        BodyCharacteristics bodyCharacteristics = new BodyCharacteristics()
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT)
            .date(DEFAULT_DATE);
        return bodyCharacteristics;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BodyCharacteristics createUpdatedEntity(EntityManager em) {
        BodyCharacteristics bodyCharacteristics = new BodyCharacteristics()
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .date(UPDATED_DATE);
        return bodyCharacteristics;
    }

    @BeforeEach
    public void initTest() {
        bodyCharacteristics = createEntity(em);
    }

    @Test
    @Transactional
    public void createBodyCharacteristics() throws Exception {
        int databaseSizeBeforeCreate = bodyCharacteristicsRepository.findAll().size();
        // Create the BodyCharacteristics
        restBodyCharacteristicsMockMvc.perform(post("/api/body-characteristics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bodyCharacteristics)))
            .andExpect(status().isCreated());

        // Validate the BodyCharacteristics in the database
        List<BodyCharacteristics> bodyCharacteristicsList = bodyCharacteristicsRepository.findAll();
        assertThat(bodyCharacteristicsList).hasSize(databaseSizeBeforeCreate + 1);
        BodyCharacteristics testBodyCharacteristics = bodyCharacteristicsList.get(bodyCharacteristicsList.size() - 1);
        assertThat(testBodyCharacteristics.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testBodyCharacteristics.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testBodyCharacteristics.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createBodyCharacteristicsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bodyCharacteristicsRepository.findAll().size();

        // Create the BodyCharacteristics with an existing ID
        bodyCharacteristics.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBodyCharacteristicsMockMvc.perform(post("/api/body-characteristics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bodyCharacteristics)))
            .andExpect(status().isBadRequest());

        // Validate the BodyCharacteristics in the database
        List<BodyCharacteristics> bodyCharacteristicsList = bodyCharacteristicsRepository.findAll();
        assertThat(bodyCharacteristicsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBodyCharacteristics() throws Exception {
        // Initialize the database
        bodyCharacteristicsRepository.saveAndFlush(bodyCharacteristics);

        // Get all the bodyCharacteristicsList
        restBodyCharacteristicsMockMvc.perform(get("/api/body-characteristics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bodyCharacteristics.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }
    
    @Test
    @Transactional
    public void getBodyCharacteristics() throws Exception {
        // Initialize the database
        bodyCharacteristicsRepository.saveAndFlush(bodyCharacteristics);

        // Get the bodyCharacteristics
        restBodyCharacteristicsMockMvc.perform(get("/api/body-characteristics/{id}", bodyCharacteristics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bodyCharacteristics.getId().intValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingBodyCharacteristics() throws Exception {
        // Get the bodyCharacteristics
        restBodyCharacteristicsMockMvc.perform(get("/api/body-characteristics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBodyCharacteristics() throws Exception {
        // Initialize the database
        bodyCharacteristicsRepository.saveAndFlush(bodyCharacteristics);

        int databaseSizeBeforeUpdate = bodyCharacteristicsRepository.findAll().size();

        // Update the bodyCharacteristics
        BodyCharacteristics updatedBodyCharacteristics = bodyCharacteristicsRepository.findById(bodyCharacteristics.getId()).get();
        // Disconnect from session so that the updates on updatedBodyCharacteristics are not directly saved in db
        em.detach(updatedBodyCharacteristics);
        updatedBodyCharacteristics
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .date(UPDATED_DATE);

        restBodyCharacteristicsMockMvc.perform(put("/api/body-characteristics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBodyCharacteristics)))
            .andExpect(status().isOk());

        // Validate the BodyCharacteristics in the database
        List<BodyCharacteristics> bodyCharacteristicsList = bodyCharacteristicsRepository.findAll();
        assertThat(bodyCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
        BodyCharacteristics testBodyCharacteristics = bodyCharacteristicsList.get(bodyCharacteristicsList.size() - 1);
        assertThat(testBodyCharacteristics.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testBodyCharacteristics.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testBodyCharacteristics.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBodyCharacteristics() throws Exception {
        int databaseSizeBeforeUpdate = bodyCharacteristicsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBodyCharacteristicsMockMvc.perform(put("/api/body-characteristics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bodyCharacteristics)))
            .andExpect(status().isBadRequest());

        // Validate the BodyCharacteristics in the database
        List<BodyCharacteristics> bodyCharacteristicsList = bodyCharacteristicsRepository.findAll();
        assertThat(bodyCharacteristicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBodyCharacteristics() throws Exception {
        // Initialize the database
        bodyCharacteristicsRepository.saveAndFlush(bodyCharacteristics);

        int databaseSizeBeforeDelete = bodyCharacteristicsRepository.findAll().size();

        // Delete the bodyCharacteristics
        restBodyCharacteristicsMockMvc.perform(delete("/api/body-characteristics/{id}", bodyCharacteristics.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BodyCharacteristics> bodyCharacteristicsList = bodyCharacteristicsRepository.findAll();
        assertThat(bodyCharacteristicsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
