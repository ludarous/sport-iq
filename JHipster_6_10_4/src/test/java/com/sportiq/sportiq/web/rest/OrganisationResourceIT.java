package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.SportIqApp;
import com.sportiq.sportiq.config.TestSecurityConfiguration;
import com.sportiq.sportiq.domain.Organisation;
import com.sportiq.sportiq.repository.OrganisationRepository;
import com.sportiq.sportiq.service.OrganisationService;
import com.sportiq.sportiq.service.dto.OrganisationDTO;
import com.sportiq.sportiq.service.mapper.OrganisationMapper;

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
 * Integration tests for the {@link OrganisationResource} REST controller.
 */
@SpringBootTest(classes = { SportIqApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class OrganisationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private OrganisationMapper organisationMapper;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganisationMockMvc;

    private Organisation organisation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organisation createEntity(EntityManager em) {
        Organisation organisation = new Organisation()
            .name(DEFAULT_NAME)
            .created(DEFAULT_CREATED)
            .description(DEFAULT_DESCRIPTION);
        return organisation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organisation createUpdatedEntity(EntityManager em) {
        Organisation organisation = new Organisation()
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .description(UPDATED_DESCRIPTION);
        return organisation;
    }

    @BeforeEach
    public void initTest() {
        organisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganisation() throws Exception {
        int databaseSizeBeforeCreate = organisationRepository.findAll().size();
        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);
        restOrganisationMockMvc.perform(post("/api/organisations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organisationDTO)))
            .andExpect(status().isCreated());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeCreate + 1);
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganisation.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testOrganisation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOrganisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organisationRepository.findAll().size();

        // Create the Organisation with an existing ID
        organisation.setId(1L);
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisationMockMvc.perform(post("/api/organisations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrganisations() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList
        restOrganisationMockMvc.perform(get("/api/organisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get the organisation
        restOrganisationMockMvc.perform(get("/api/organisations/{id}", organisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organisation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingOrganisation() throws Exception {
        // Get the organisation
        restOrganisationMockMvc.perform(get("/api/organisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();

        // Update the organisation
        Organisation updatedOrganisation = organisationRepository.findById(organisation.getId()).get();
        // Disconnect from session so that the updates on updatedOrganisation are not directly saved in db
        em.detach(updatedOrganisation);
        updatedOrganisation
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .description(UPDATED_DESCRIPTION);
        OrganisationDTO organisationDTO = organisationMapper.toDto(updatedOrganisation);

        restOrganisationMockMvc.perform(put("/api/organisations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organisationDTO)))
            .andExpect(status().isOk());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganisation.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testOrganisation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisationMockMvc.perform(put("/api/organisations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        int databaseSizeBeforeDelete = organisationRepository.findAll().size();

        // Delete the organisation
        restOrganisationMockMvc.perform(delete("/api/organisations/{id}", organisation.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
