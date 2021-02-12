package com.sportiq.sportiq.web.rest;

import com.sportiq.sportiq.SportIqApp;
import com.sportiq.sportiq.config.TestSecurityConfiguration;
import com.sportiq.sportiq.domain.UserEvent;
import com.sportiq.sportiq.domain.User;
import com.sportiq.sportiq.domain.Event;
import com.sportiq.sportiq.repository.UserEventRepository;
import com.sportiq.sportiq.service.UserEventService;
import com.sportiq.sportiq.service.dto.UserEventDTO;
import com.sportiq.sportiq.service.mapper.UserEventMapper;

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
 * Integration tests for the {@link UserEventResource} REST controller.
 */
@SpringBootTest(classes = { SportIqApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserEventResourceIT {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_REGISTRATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REGISTRATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UserEventRepository userEventRepository;

    @Autowired
    private UserEventMapper userEventMapper;

    @Autowired
    private UserEventService userEventService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserEventMockMvc;

    private UserEvent userEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserEvent createEntity(EntityManager em) {
        UserEvent userEvent = new UserEvent()
            .note(DEFAULT_NOTE)
            .registrationDate(DEFAULT_REGISTRATION_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userEvent.setUser(user);
        // Add required entity
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            event = EventResourceIT.createEntity(em);
            em.persist(event);
            em.flush();
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        userEvent.setEvent(event);
        return userEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserEvent createUpdatedEntity(EntityManager em) {
        UserEvent userEvent = new UserEvent()
            .note(UPDATED_NOTE)
            .registrationDate(UPDATED_REGISTRATION_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userEvent.setUser(user);
        // Add required entity
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            event = EventResourceIT.createUpdatedEntity(em);
            em.persist(event);
            em.flush();
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        userEvent.setEvent(event);
        return userEvent;
    }

    @BeforeEach
    public void initTest() {
        userEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserEvent() throws Exception {
        int databaseSizeBeforeCreate = userEventRepository.findAll().size();
        // Create the UserEvent
        UserEventDTO userEventDTO = userEventMapper.toDto(userEvent);
        restUserEventMockMvc.perform(post("/api/user-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEventDTO)))
            .andExpect(status().isCreated());

        // Validate the UserEvent in the database
        List<UserEvent> userEventList = userEventRepository.findAll();
        assertThat(userEventList).hasSize(databaseSizeBeforeCreate + 1);
        UserEvent testUserEvent = userEventList.get(userEventList.size() - 1);
        assertThat(testUserEvent.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testUserEvent.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
    }

    @Test
    @Transactional
    public void createUserEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userEventRepository.findAll().size();

        // Create the UserEvent with an existing ID
        userEvent.setId(1L);
        UserEventDTO userEventDTO = userEventMapper.toDto(userEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserEventMockMvc.perform(post("/api/user-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserEvent in the database
        List<UserEvent> userEventList = userEventRepository.findAll();
        assertThat(userEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserEvents() throws Exception {
        // Initialize the database
        userEventRepository.saveAndFlush(userEvent);

        // Get all the userEventList
        restUserEventMockMvc.perform(get("/api/user-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(sameInstant(DEFAULT_REGISTRATION_DATE))));
    }
    
    @Test
    @Transactional
    public void getUserEvent() throws Exception {
        // Initialize the database
        userEventRepository.saveAndFlush(userEvent);

        // Get the userEvent
        restUserEventMockMvc.perform(get("/api/user-events/{id}", userEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userEvent.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.registrationDate").value(sameInstant(DEFAULT_REGISTRATION_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingUserEvent() throws Exception {
        // Get the userEvent
        restUserEventMockMvc.perform(get("/api/user-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserEvent() throws Exception {
        // Initialize the database
        userEventRepository.saveAndFlush(userEvent);

        int databaseSizeBeforeUpdate = userEventRepository.findAll().size();

        // Update the userEvent
        UserEvent updatedUserEvent = userEventRepository.findById(userEvent.getId()).get();
        // Disconnect from session so that the updates on updatedUserEvent are not directly saved in db
        em.detach(updatedUserEvent);
        updatedUserEvent
            .note(UPDATED_NOTE)
            .registrationDate(UPDATED_REGISTRATION_DATE);
        UserEventDTO userEventDTO = userEventMapper.toDto(updatedUserEvent);

        restUserEventMockMvc.perform(put("/api/user-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEventDTO)))
            .andExpect(status().isOk());

        // Validate the UserEvent in the database
        List<UserEvent> userEventList = userEventRepository.findAll();
        assertThat(userEventList).hasSize(databaseSizeBeforeUpdate);
        UserEvent testUserEvent = userEventList.get(userEventList.size() - 1);
        assertThat(testUserEvent.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testUserEvent.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserEvent() throws Exception {
        int databaseSizeBeforeUpdate = userEventRepository.findAll().size();

        // Create the UserEvent
        UserEventDTO userEventDTO = userEventMapper.toDto(userEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserEventMockMvc.perform(put("/api/user-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserEvent in the database
        List<UserEvent> userEventList = userEventRepository.findAll();
        assertThat(userEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserEvent() throws Exception {
        // Initialize the database
        userEventRepository.saveAndFlush(userEvent);

        int databaseSizeBeforeDelete = userEventRepository.findAll().size();

        // Delete the userEvent
        restUserEventMockMvc.perform(delete("/api/user-events/{id}", userEvent.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserEvent> userEventList = userEventRepository.findAll();
        assertThat(userEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
