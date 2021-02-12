package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.SportIqApp;
import com.sportiq.sportiq.config.TestSecurityConfiguration;
import com.sportiq.sportiq.domain.Agreement;
import com.sportiq.sportiq.repository.AgreementRepository;
import com.sportiq.sportiq.service.AgreementService;
import com.sportiq.sportiq.service.dto.AgreementDTO;
import com.sportiq.sportiq.service.mapper.AgreementMapper;

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
 * Integration tests for the {@link AgreementResource} REST controller.
 */
@SpringBootTest(classes = { SportIqApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AgreementResourceIT {

    private static final Boolean DEFAULT_TERMS_AGREEMENT = false;
    private static final Boolean UPDATED_TERMS_AGREEMENT = true;

    private static final Boolean DEFAULT_GDPR_AGREEMENT = false;
    private static final Boolean UPDATED_GDPR_AGREEMENT = true;

    private static final Boolean DEFAULT_PHOTOGRAPHY_AGREEMENT = false;
    private static final Boolean UPDATED_PHOTOGRAPHY_AGREEMENT = true;

    private static final Boolean DEFAULT_MARKETING_AGREEMENT = false;
    private static final Boolean UPDATED_MARKETING_AGREEMENT = true;

    private static final Boolean DEFAULT_MEDICAL_FITNESS_AGREEMENT = false;
    private static final Boolean UPDATED_MEDICAL_FITNESS_AGREEMENT = true;

    @Autowired
    private AgreementRepository agreementRepository;

    @Autowired
    private AgreementMapper agreementMapper;

    @Autowired
    private AgreementService agreementService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgreementMockMvc;

    private Agreement agreement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agreement createEntity(EntityManager em) {
        Agreement agreement = new Agreement()
            .termsAgreement(DEFAULT_TERMS_AGREEMENT)
            .gdprAgreement(DEFAULT_GDPR_AGREEMENT)
            .photographyAgreement(DEFAULT_PHOTOGRAPHY_AGREEMENT)
            .marketingAgreement(DEFAULT_MARKETING_AGREEMENT)
            .medicalFitnessAgreement(DEFAULT_MEDICAL_FITNESS_AGREEMENT);
        return agreement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agreement createUpdatedEntity(EntityManager em) {
        Agreement agreement = new Agreement()
            .termsAgreement(UPDATED_TERMS_AGREEMENT)
            .gdprAgreement(UPDATED_GDPR_AGREEMENT)
            .photographyAgreement(UPDATED_PHOTOGRAPHY_AGREEMENT)
            .marketingAgreement(UPDATED_MARKETING_AGREEMENT)
            .medicalFitnessAgreement(UPDATED_MEDICAL_FITNESS_AGREEMENT);
        return agreement;
    }

    @BeforeEach
    public void initTest() {
        agreement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgreement() throws Exception {
        int databaseSizeBeforeCreate = agreementRepository.findAll().size();
        // Create the Agreement
        AgreementDTO agreementDTO = agreementMapper.toDto(agreement);
        restAgreementMockMvc.perform(post("/api/agreements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agreementDTO)))
            .andExpect(status().isCreated());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeCreate + 1);
        Agreement testAgreement = agreementList.get(agreementList.size() - 1);
        assertThat(testAgreement.isTermsAgreement()).isEqualTo(DEFAULT_TERMS_AGREEMENT);
        assertThat(testAgreement.isGdprAgreement()).isEqualTo(DEFAULT_GDPR_AGREEMENT);
        assertThat(testAgreement.isPhotographyAgreement()).isEqualTo(DEFAULT_PHOTOGRAPHY_AGREEMENT);
        assertThat(testAgreement.isMarketingAgreement()).isEqualTo(DEFAULT_MARKETING_AGREEMENT);
        assertThat(testAgreement.isMedicalFitnessAgreement()).isEqualTo(DEFAULT_MEDICAL_FITNESS_AGREEMENT);
    }

    @Test
    @Transactional
    public void createAgreementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agreementRepository.findAll().size();

        // Create the Agreement with an existing ID
        agreement.setId(1L);
        AgreementDTO agreementDTO = agreementMapper.toDto(agreement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgreementMockMvc.perform(post("/api/agreements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agreementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAgreements() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        // Get all the agreementList
        restAgreementMockMvc.perform(get("/api/agreements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agreement.getId().intValue())))
            .andExpect(jsonPath("$.[*].termsAgreement").value(hasItem(DEFAULT_TERMS_AGREEMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].gdprAgreement").value(hasItem(DEFAULT_GDPR_AGREEMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].photographyAgreement").value(hasItem(DEFAULT_PHOTOGRAPHY_AGREEMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].marketingAgreement").value(hasItem(DEFAULT_MARKETING_AGREEMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].medicalFitnessAgreement").value(hasItem(DEFAULT_MEDICAL_FITNESS_AGREEMENT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAgreement() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        // Get the agreement
        restAgreementMockMvc.perform(get("/api/agreements/{id}", agreement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agreement.getId().intValue()))
            .andExpect(jsonPath("$.termsAgreement").value(DEFAULT_TERMS_AGREEMENT.booleanValue()))
            .andExpect(jsonPath("$.gdprAgreement").value(DEFAULT_GDPR_AGREEMENT.booleanValue()))
            .andExpect(jsonPath("$.photographyAgreement").value(DEFAULT_PHOTOGRAPHY_AGREEMENT.booleanValue()))
            .andExpect(jsonPath("$.marketingAgreement").value(DEFAULT_MARKETING_AGREEMENT.booleanValue()))
            .andExpect(jsonPath("$.medicalFitnessAgreement").value(DEFAULT_MEDICAL_FITNESS_AGREEMENT.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAgreement() throws Exception {
        // Get the agreement
        restAgreementMockMvc.perform(get("/api/agreements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgreement() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        int databaseSizeBeforeUpdate = agreementRepository.findAll().size();

        // Update the agreement
        Agreement updatedAgreement = agreementRepository.findById(agreement.getId()).get();
        // Disconnect from session so that the updates on updatedAgreement are not directly saved in db
        em.detach(updatedAgreement);
        updatedAgreement
            .termsAgreement(UPDATED_TERMS_AGREEMENT)
            .gdprAgreement(UPDATED_GDPR_AGREEMENT)
            .photographyAgreement(UPDATED_PHOTOGRAPHY_AGREEMENT)
            .marketingAgreement(UPDATED_MARKETING_AGREEMENT)
            .medicalFitnessAgreement(UPDATED_MEDICAL_FITNESS_AGREEMENT);
        AgreementDTO agreementDTO = agreementMapper.toDto(updatedAgreement);

        restAgreementMockMvc.perform(put("/api/agreements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agreementDTO)))
            .andExpect(status().isOk());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeUpdate);
        Agreement testAgreement = agreementList.get(agreementList.size() - 1);
        assertThat(testAgreement.isTermsAgreement()).isEqualTo(UPDATED_TERMS_AGREEMENT);
        assertThat(testAgreement.isGdprAgreement()).isEqualTo(UPDATED_GDPR_AGREEMENT);
        assertThat(testAgreement.isPhotographyAgreement()).isEqualTo(UPDATED_PHOTOGRAPHY_AGREEMENT);
        assertThat(testAgreement.isMarketingAgreement()).isEqualTo(UPDATED_MARKETING_AGREEMENT);
        assertThat(testAgreement.isMedicalFitnessAgreement()).isEqualTo(UPDATED_MEDICAL_FITNESS_AGREEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingAgreement() throws Exception {
        int databaseSizeBeforeUpdate = agreementRepository.findAll().size();

        // Create the Agreement
        AgreementDTO agreementDTO = agreementMapper.toDto(agreement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgreementMockMvc.perform(put("/api/agreements").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agreementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgreement() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        int databaseSizeBeforeDelete = agreementRepository.findAll().size();

        // Delete the agreement
        restAgreementMockMvc.perform(delete("/api/agreements/{id}", agreement.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
