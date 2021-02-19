package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.JHipster6105App;
import com.sportiq.sportiq.domain.GroupMembership;
import com.sportiq.sportiq.repository.GroupMembershipRepository;

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
 * Integration tests for the {@link GroupMembershipResource} REST controller.
 */
@SpringBootTest(classes = JHipster6105App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GroupMembershipResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private GroupMembershipRepository groupMembershipRepository;

    @Mock
    private GroupMembershipRepository groupMembershipRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupMembershipMockMvc;

    private GroupMembership groupMembership;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupMembership createEntity(EntityManager em) {
        GroupMembership groupMembership = new GroupMembership()
            .created(DEFAULT_CREATED);
        return groupMembership;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupMembership createUpdatedEntity(EntityManager em) {
        GroupMembership groupMembership = new GroupMembership()
            .created(UPDATED_CREATED);
        return groupMembership;
    }

    @BeforeEach
    public void initTest() {
        groupMembership = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupMembership() throws Exception {
        int databaseSizeBeforeCreate = groupMembershipRepository.findAll().size();
        // Create the GroupMembership
        restGroupMembershipMockMvc.perform(post("/api/group-memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupMembership)))
            .andExpect(status().isCreated());

        // Validate the GroupMembership in the database
        List<GroupMembership> groupMembershipList = groupMembershipRepository.findAll();
        assertThat(groupMembershipList).hasSize(databaseSizeBeforeCreate + 1);
        GroupMembership testGroupMembership = groupMembershipList.get(groupMembershipList.size() - 1);
        assertThat(testGroupMembership.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void createGroupMembershipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupMembershipRepository.findAll().size();

        // Create the GroupMembership with an existing ID
        groupMembership.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupMembershipMockMvc.perform(post("/api/group-memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupMembership)))
            .andExpect(status().isBadRequest());

        // Validate the GroupMembership in the database
        List<GroupMembership> groupMembershipList = groupMembershipRepository.findAll();
        assertThat(groupMembershipList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGroupMemberships() throws Exception {
        // Initialize the database
        groupMembershipRepository.saveAndFlush(groupMembership);

        // Get all the groupMembershipList
        restGroupMembershipMockMvc.perform(get("/api/group-memberships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupMembership.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGroupMembershipsWithEagerRelationshipsIsEnabled() throws Exception {
        when(groupMembershipRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGroupMembershipMockMvc.perform(get("/api/group-memberships?eagerload=true"))
            .andExpect(status().isOk());

        verify(groupMembershipRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGroupMembershipsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(groupMembershipRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGroupMembershipMockMvc.perform(get("/api/group-memberships?eagerload=true"))
            .andExpect(status().isOk());

        verify(groupMembershipRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGroupMembership() throws Exception {
        // Initialize the database
        groupMembershipRepository.saveAndFlush(groupMembership);

        // Get the groupMembership
        restGroupMembershipMockMvc.perform(get("/api/group-memberships/{id}", groupMembership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupMembership.getId().intValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)));
    }
    @Test
    @Transactional
    public void getNonExistingGroupMembership() throws Exception {
        // Get the groupMembership
        restGroupMembershipMockMvc.perform(get("/api/group-memberships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupMembership() throws Exception {
        // Initialize the database
        groupMembershipRepository.saveAndFlush(groupMembership);

        int databaseSizeBeforeUpdate = groupMembershipRepository.findAll().size();

        // Update the groupMembership
        GroupMembership updatedGroupMembership = groupMembershipRepository.findById(groupMembership.getId()).get();
        // Disconnect from session so that the updates on updatedGroupMembership are not directly saved in db
        em.detach(updatedGroupMembership);
        updatedGroupMembership
            .created(UPDATED_CREATED);

        restGroupMembershipMockMvc.perform(put("/api/group-memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupMembership)))
            .andExpect(status().isOk());

        // Validate the GroupMembership in the database
        List<GroupMembership> groupMembershipList = groupMembershipRepository.findAll();
        assertThat(groupMembershipList).hasSize(databaseSizeBeforeUpdate);
        GroupMembership testGroupMembership = groupMembershipList.get(groupMembershipList.size() - 1);
        assertThat(testGroupMembership.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupMembership() throws Exception {
        int databaseSizeBeforeUpdate = groupMembershipRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupMembershipMockMvc.perform(put("/api/group-memberships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupMembership)))
            .andExpect(status().isBadRequest());

        // Validate the GroupMembership in the database
        List<GroupMembership> groupMembershipList = groupMembershipRepository.findAll();
        assertThat(groupMembershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupMembership() throws Exception {
        // Initialize the database
        groupMembershipRepository.saveAndFlush(groupMembership);

        int databaseSizeBeforeDelete = groupMembershipRepository.findAll().size();

        // Delete the groupMembership
        restGroupMembershipMockMvc.perform(delete("/api/group-memberships/{id}", groupMembership.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupMembership> groupMembershipList = groupMembershipRepository.findAll();
        assertThat(groupMembershipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
