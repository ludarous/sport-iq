package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.JHipster6105App;
import com.sportiq.sportiq.domain.OrganisationMembership;
import com.sportiq.sportiq.repository.OrganisationMembershipRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.sportiq.sportiq.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrganisationMembershipResource} REST controller.
 */
@SpringBootTest(classes = JHipster6105App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrganisationMembershipResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private OrganisationMembershipRepository organisationMembershipRepository;

    @Mock
    private OrganisationMembershipRepository organisationMembershipRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganisationMembershipMockMvc;

    private OrganisationMembership organisationMembership;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganisationMembership createEntity(EntityManager em) {
        OrganisationMembership organisationMembership = new OrganisationMembership()
            .created(DEFAULT_CREATED);
        return organisationMembership;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganisationMembership createUpdatedEntity(EntityManager em) {
        OrganisationMembership organisationMembership = new OrganisationMembership()
            .created(UPDATED_CREATED);
        return organisationMembership;
    }

    @BeforeEach
    public void initTest() {
        organisationMembership = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganisationMembership() throws Exception {
        int databaseSizeBeforeCreate = organisationMembershipRepository.findAll().size();
        // Create the OrganisationMembership
        restOrganisationMembershipMockMvc.perform(post("/api/organisation-memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organisationMembership)))
            .andExpect(status().isCreated());

        // Validate the OrganisationMembership in the database
        List<OrganisationMembership> organisationMembershipList = organisationMembershipRepository.findAll();
        assertThat(organisationMembershipList).hasSize(databaseSizeBeforeCreate + 1);
        OrganisationMembership testOrganisationMembership = organisationMembershipList.get(organisationMembershipList.size() - 1);
        assertThat(testOrganisationMembership.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void createOrganisationMembershipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organisationMembershipRepository.findAll().size();

        // Create the OrganisationMembership with an existing ID
        organisationMembership.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisationMembershipMockMvc.perform(post("/api/organisation-memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organisationMembership)))
            .andExpect(status().isBadRequest());

        // Validate the OrganisationMembership in the database
        List<OrganisationMembership> organisationMembershipList = organisationMembershipRepository.findAll();
        assertThat(organisationMembershipList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrganisationMemberships() throws Exception {
        // Initialize the database
        organisationMembershipRepository.saveAndFlush(organisationMembership);

        // Get all the organisationMembershipList
        restOrganisationMembershipMockMvc.perform(get("/api/organisation-memberships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisationMembership.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllOrganisationMembershipsWithEagerRelationshipsIsEnabled() throws Exception {
        when(organisationMembershipRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrganisationMembershipMockMvc.perform(get("/api/organisation-memberships?eagerload=true"))
            .andExpect(status().isOk());

        verify(organisationMembershipRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllOrganisationMembershipsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(organisationMembershipRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrganisationMembershipMockMvc.perform(get("/api/organisation-memberships?eagerload=true"))
            .andExpect(status().isOk());

        verify(organisationMembershipRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getOrganisationMembership() throws Exception {
        // Initialize the database
        organisationMembershipRepository.saveAndFlush(organisationMembership);

        // Get the organisationMembership
        restOrganisationMembershipMockMvc.perform(get("/api/organisation-memberships/{id}", organisationMembership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organisationMembership.getId().intValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }
    @Test
    @Transactional
    public void getNonExistingOrganisationMembership() throws Exception {
        // Get the organisationMembership
        restOrganisationMembershipMockMvc.perform(get("/api/organisation-memberships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganisationMembership() throws Exception {
        // Initialize the database
        organisationMembershipRepository.saveAndFlush(organisationMembership);

        int databaseSizeBeforeUpdate = organisationMembershipRepository.findAll().size();

        // Update the organisationMembership
        OrganisationMembership updatedOrganisationMembership = organisationMembershipRepository.findById(organisationMembership.getId()).get();
        // Disconnect from session so that the updates on updatedOrganisationMembership are not directly saved in db
        em.detach(updatedOrganisationMembership);
        updatedOrganisationMembership
            .created(UPDATED_CREATED);

        restOrganisationMembershipMockMvc.perform(put("/api/organisation-memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrganisationMembership)))
            .andExpect(status().isOk());

        // Validate the OrganisationMembership in the database
        List<OrganisationMembership> organisationMembershipList = organisationMembershipRepository.findAll();
        assertThat(organisationMembershipList).hasSize(databaseSizeBeforeUpdate);
        OrganisationMembership testOrganisationMembership = organisationMembershipList.get(organisationMembershipList.size() - 1);
        assertThat(testOrganisationMembership.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganisationMembership() throws Exception {
        int databaseSizeBeforeUpdate = organisationMembershipRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisationMembershipMockMvc.perform(put("/api/organisation-memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organisationMembership)))
            .andExpect(status().isBadRequest());

        // Validate the OrganisationMembership in the database
        List<OrganisationMembership> organisationMembershipList = organisationMembershipRepository.findAll();
        assertThat(organisationMembershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrganisationMembership() throws Exception {
        // Initialize the database
        organisationMembershipRepository.saveAndFlush(organisationMembership);

        int databaseSizeBeforeDelete = organisationMembershipRepository.findAll().size();

        // Delete the organisationMembership
        restOrganisationMembershipMockMvc.perform(delete("/api/organisation-memberships/{id}", organisationMembership.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganisationMembership> organisationMembershipList = organisationMembershipRepository.findAll();
        assertThat(organisationMembershipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
