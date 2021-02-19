package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.JHipster6105App;
import com.sportiq.sportiq.domain.LegalRepresentative;
import com.sportiq.sportiq.repository.LegalRepresentativeRepository;

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
 * Integration tests for the {@link LegalRepresentativeResource} REST controller.
 */
@SpringBootTest(classes = JHipster6105App.class)
@AutoConfigureMockMvc
@WithMockUser
public class LegalRepresentativeResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private LegalRepresentativeRepository legalRepresentativeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLegalRepresentativeMockMvc;

    private LegalRepresentative legalRepresentative;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LegalRepresentative createEntity(EntityManager em) {
        LegalRepresentative legalRepresentative = new LegalRepresentative()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE);
        return legalRepresentative;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LegalRepresentative createUpdatedEntity(EntityManager em) {
        LegalRepresentative legalRepresentative = new LegalRepresentative()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        return legalRepresentative;
    }

    @BeforeEach
    public void initTest() {
        legalRepresentative = createEntity(em);
    }

    @Test
    @Transactional
    public void createLegalRepresentative() throws Exception {
        int databaseSizeBeforeCreate = legalRepresentativeRepository.findAll().size();
        // Create the LegalRepresentative
        restLegalRepresentativeMockMvc.perform(post("/api/legal-representatives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(legalRepresentative)))
            .andExpect(status().isCreated());

        // Validate the LegalRepresentative in the database
        List<LegalRepresentative> legalRepresentativeList = legalRepresentativeRepository.findAll();
        assertThat(legalRepresentativeList).hasSize(databaseSizeBeforeCreate + 1);
        LegalRepresentative testLegalRepresentative = legalRepresentativeList.get(legalRepresentativeList.size() - 1);
        assertThat(testLegalRepresentative.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testLegalRepresentative.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testLegalRepresentative.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLegalRepresentative.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createLegalRepresentativeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = legalRepresentativeRepository.findAll().size();

        // Create the LegalRepresentative with an existing ID
        legalRepresentative.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLegalRepresentativeMockMvc.perform(post("/api/legal-representatives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(legalRepresentative)))
            .andExpect(status().isBadRequest());

        // Validate the LegalRepresentative in the database
        List<LegalRepresentative> legalRepresentativeList = legalRepresentativeRepository.findAll();
        assertThat(legalRepresentativeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLegalRepresentatives() throws Exception {
        // Initialize the database
        legalRepresentativeRepository.saveAndFlush(legalRepresentative);

        // Get all the legalRepresentativeList
        restLegalRepresentativeMockMvc.perform(get("/api/legal-representatives?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(legalRepresentative.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getLegalRepresentative() throws Exception {
        // Initialize the database
        legalRepresentativeRepository.saveAndFlush(legalRepresentative);

        // Get the legalRepresentative
        restLegalRepresentativeMockMvc.perform(get("/api/legal-representatives/{id}", legalRepresentative.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(legalRepresentative.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }
    @Test
    @Transactional
    public void getNonExistingLegalRepresentative() throws Exception {
        // Get the legalRepresentative
        restLegalRepresentativeMockMvc.perform(get("/api/legal-representatives/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLegalRepresentative() throws Exception {
        // Initialize the database
        legalRepresentativeRepository.saveAndFlush(legalRepresentative);

        int databaseSizeBeforeUpdate = legalRepresentativeRepository.findAll().size();

        // Update the legalRepresentative
        LegalRepresentative updatedLegalRepresentative = legalRepresentativeRepository.findById(legalRepresentative.getId()).get();
        // Disconnect from session so that the updates on updatedLegalRepresentative are not directly saved in db
        em.detach(updatedLegalRepresentative);
        updatedLegalRepresentative
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);

        restLegalRepresentativeMockMvc.perform(put("/api/legal-representatives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLegalRepresentative)))
            .andExpect(status().isOk());

        // Validate the LegalRepresentative in the database
        List<LegalRepresentative> legalRepresentativeList = legalRepresentativeRepository.findAll();
        assertThat(legalRepresentativeList).hasSize(databaseSizeBeforeUpdate);
        LegalRepresentative testLegalRepresentative = legalRepresentativeList.get(legalRepresentativeList.size() - 1);
        assertThat(testLegalRepresentative.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testLegalRepresentative.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testLegalRepresentative.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLegalRepresentative.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingLegalRepresentative() throws Exception {
        int databaseSizeBeforeUpdate = legalRepresentativeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLegalRepresentativeMockMvc.perform(put("/api/legal-representatives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(legalRepresentative)))
            .andExpect(status().isBadRequest());

        // Validate the LegalRepresentative in the database
        List<LegalRepresentative> legalRepresentativeList = legalRepresentativeRepository.findAll();
        assertThat(legalRepresentativeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLegalRepresentative() throws Exception {
        // Initialize the database
        legalRepresentativeRepository.saveAndFlush(legalRepresentative);

        int databaseSizeBeforeDelete = legalRepresentativeRepository.findAll().size();

        // Delete the legalRepresentative
        restLegalRepresentativeMockMvc.perform(delete("/api/legal-representatives/{id}", legalRepresentative.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LegalRepresentative> legalRepresentativeList = legalRepresentativeRepository.findAll();
        assertThat(legalRepresentativeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
