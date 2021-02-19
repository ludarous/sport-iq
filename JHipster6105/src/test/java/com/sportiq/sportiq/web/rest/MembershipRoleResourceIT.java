package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.JHipster6105App;
import com.sportiq.sportiq.domain.MembershipRole;
import com.sportiq.sportiq.repository.MembershipRoleRepository;

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
 * Integration tests for the {@link MembershipRoleResource} REST controller.
 */
@SpringBootTest(classes = JHipster6105App.class)
@AutoConfigureMockMvc
@WithMockUser
public class MembershipRoleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MembershipRoleRepository membershipRoleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMembershipRoleMockMvc;

    private MembershipRole membershipRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembershipRole createEntity(EntityManager em) {
        MembershipRole membershipRole = new MembershipRole()
            .name(DEFAULT_NAME);
        return membershipRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembershipRole createUpdatedEntity(EntityManager em) {
        MembershipRole membershipRole = new MembershipRole()
            .name(UPDATED_NAME);
        return membershipRole;
    }

    @BeforeEach
    public void initTest() {
        membershipRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembershipRole() throws Exception {
        int databaseSizeBeforeCreate = membershipRoleRepository.findAll().size();
        // Create the MembershipRole
        restMembershipRoleMockMvc.perform(post("/api/membership-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipRole)))
            .andExpect(status().isCreated());

        // Validate the MembershipRole in the database
        List<MembershipRole> membershipRoleList = membershipRoleRepository.findAll();
        assertThat(membershipRoleList).hasSize(databaseSizeBeforeCreate + 1);
        MembershipRole testMembershipRole = membershipRoleList.get(membershipRoleList.size() - 1);
        assertThat(testMembershipRole.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMembershipRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membershipRoleRepository.findAll().size();

        // Create the MembershipRole with an existing ID
        membershipRole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembershipRoleMockMvc.perform(post("/api/membership-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipRole)))
            .andExpect(status().isBadRequest());

        // Validate the MembershipRole in the database
        List<MembershipRole> membershipRoleList = membershipRoleRepository.findAll();
        assertThat(membershipRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMembershipRoles() throws Exception {
        // Initialize the database
        membershipRoleRepository.saveAndFlush(membershipRole);

        // Get all the membershipRoleList
        restMembershipRoleMockMvc.perform(get("/api/membership-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membershipRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getMembershipRole() throws Exception {
        // Initialize the database
        membershipRoleRepository.saveAndFlush(membershipRole);

        // Get the membershipRole
        restMembershipRoleMockMvc.perform(get("/api/membership-roles/{id}", membershipRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(membershipRole.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingMembershipRole() throws Exception {
        // Get the membershipRole
        restMembershipRoleMockMvc.perform(get("/api/membership-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembershipRole() throws Exception {
        // Initialize the database
        membershipRoleRepository.saveAndFlush(membershipRole);

        int databaseSizeBeforeUpdate = membershipRoleRepository.findAll().size();

        // Update the membershipRole
        MembershipRole updatedMembershipRole = membershipRoleRepository.findById(membershipRole.getId()).get();
        // Disconnect from session so that the updates on updatedMembershipRole are not directly saved in db
        em.detach(updatedMembershipRole);
        updatedMembershipRole
            .name(UPDATED_NAME);

        restMembershipRoleMockMvc.perform(put("/api/membership-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMembershipRole)))
            .andExpect(status().isOk());

        // Validate the MembershipRole in the database
        List<MembershipRole> membershipRoleList = membershipRoleRepository.findAll();
        assertThat(membershipRoleList).hasSize(databaseSizeBeforeUpdate);
        MembershipRole testMembershipRole = membershipRoleList.get(membershipRoleList.size() - 1);
        assertThat(testMembershipRole.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMembershipRole() throws Exception {
        int databaseSizeBeforeUpdate = membershipRoleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembershipRoleMockMvc.perform(put("/api/membership-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membershipRole)))
            .andExpect(status().isBadRequest());

        // Validate the MembershipRole in the database
        List<MembershipRole> membershipRoleList = membershipRoleRepository.findAll();
        assertThat(membershipRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMembershipRole() throws Exception {
        // Initialize the database
        membershipRoleRepository.saveAndFlush(membershipRole);

        int databaseSizeBeforeDelete = membershipRoleRepository.findAll().size();

        // Delete the membershipRole
        restMembershipRoleMockMvc.perform(delete("/api/membership-roles/{id}", membershipRole.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MembershipRole> membershipRoleList = membershipRoleRepository.findAll();
        assertThat(membershipRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
