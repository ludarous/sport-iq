package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;
import cz.sportiq.config.TestSecurityConfiguration;
import cz.sportiq.domain.AthleteEvent;
import cz.sportiq.domain.Athlete;
import cz.sportiq.repository.AthleteEventRepository;
import cz.sportiq.service.AthleteEventService;
import cz.sportiq.service.dto.AthleteEventDTO;
import cz.sportiq.service.mapper.AthleteEventMapper;
import cz.sportiq.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static cz.sportiq.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AthleteEventResource} REST controller.
 */
@SpringBootTest(classes = {SportiqApp.class, TestSecurityConfiguration.class})
public class AthleteEventResourceIT {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Float DEFAULT_ACTUAL_HEIGHT_IN_CM = 1F;
    private static final Float UPDATED_ACTUAL_HEIGHT_IN_CM = 2F;

    private static final Float DEFAULT_ACTUAL_WEIGHT_IN_KG = 1F;
    private static final Float UPDATED_ACTUAL_WEIGHT_IN_KG = 2F;

    @Autowired
    private AthleteEventRepository athleteEventRepository;

    @Autowired
    private AthleteEventMapper athleteEventMapper;

    @Autowired
    private AthleteEventService athleteEventService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAthleteEventMockMvc;

    private AthleteEvent athleteEvent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AthleteEventResource athleteEventResource = new AthleteEventResource(athleteEventService);
        this.restAthleteEventMockMvc = MockMvcBuilders.standaloneSetup(athleteEventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AthleteEvent createEntity(EntityManager em) {
        AthleteEvent athleteEvent = new AthleteEvent()
            .note(DEFAULT_NOTE)
            .actualHeightInCm(DEFAULT_ACTUAL_HEIGHT_IN_CM)
            .actualWeightInKg(DEFAULT_ACTUAL_WEIGHT_IN_KG);
        // Add required entity
        Athlete athlete;
        if (TestUtil.findAll(em, Athlete.class).isEmpty()) {
            athlete = AthleteResourceIT.createEntity(em);
            em.persist(athlete);
            em.flush();
        } else {
            athlete = TestUtil.findAll(em, Athlete.class).get(0);
        }
        athleteEvent.setAthlete(athlete);
        return athleteEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AthleteEvent createUpdatedEntity(EntityManager em) {
        AthleteEvent athleteEvent = new AthleteEvent()
            .note(UPDATED_NOTE)
            .actualHeightInCm(UPDATED_ACTUAL_HEIGHT_IN_CM)
            .actualWeightInKg(UPDATED_ACTUAL_WEIGHT_IN_KG);
        // Add required entity
        Athlete athlete;
        if (TestUtil.findAll(em, Athlete.class).isEmpty()) {
            athlete = AthleteResourceIT.createUpdatedEntity(em);
            em.persist(athlete);
            em.flush();
        } else {
            athlete = TestUtil.findAll(em, Athlete.class).get(0);
        }
        athleteEvent.setAthlete(athlete);
        return athleteEvent;
    }

    @BeforeEach
    public void initTest() {
        athleteEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createAthleteEvent() throws Exception {
        int databaseSizeBeforeCreate = athleteEventRepository.findAll().size();

        // Create the AthleteEvent
        AthleteEventDTO athleteEventDTO = athleteEventMapper.toDto(athleteEvent);
        restAthleteEventMockMvc.perform(post("/api/athlete-events")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteEventDTO)))
            .andExpect(status().isCreated());

        // Validate the AthleteEvent in the database
        List<AthleteEvent> athleteEventList = athleteEventRepository.findAll();
        assertThat(athleteEventList).hasSize(databaseSizeBeforeCreate + 1);
        AthleteEvent testAthleteEvent = athleteEventList.get(athleteEventList.size() - 1);
        assertThat(testAthleteEvent.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testAthleteEvent.getActualHeightInCm()).isEqualTo(DEFAULT_ACTUAL_HEIGHT_IN_CM);
        assertThat(testAthleteEvent.getActualWeightInKg()).isEqualTo(DEFAULT_ACTUAL_WEIGHT_IN_KG);
    }

    @Test
    @Transactional
    public void createAthleteEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = athleteEventRepository.findAll().size();

        // Create the AthleteEvent with an existing ID
        athleteEvent.setId(1L);
        AthleteEventDTO athleteEventDTO = athleteEventMapper.toDto(athleteEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAthleteEventMockMvc.perform(post("/api/athlete-events")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteEvent in the database
        List<AthleteEvent> athleteEventList = athleteEventRepository.findAll();
        assertThat(athleteEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAthleteEvents() throws Exception {
        // Initialize the database
        athleteEventRepository.saveAndFlush(athleteEvent);

        // Get all the athleteEventList
        restAthleteEventMockMvc.perform(get("/api/athlete-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(athleteEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].actualHeightInCm").value(hasItem(DEFAULT_ACTUAL_HEIGHT_IN_CM.doubleValue())))
            .andExpect(jsonPath("$.[*].actualWeightInKg").value(hasItem(DEFAULT_ACTUAL_WEIGHT_IN_KG.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAthleteEvent() throws Exception {
        // Initialize the database
        athleteEventRepository.saveAndFlush(athleteEvent);

        // Get the athleteEvent
        restAthleteEventMockMvc.perform(get("/api/athlete-events/{id}", athleteEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(athleteEvent.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.actualHeightInCm").value(DEFAULT_ACTUAL_HEIGHT_IN_CM.doubleValue()))
            .andExpect(jsonPath("$.actualWeightInKg").value(DEFAULT_ACTUAL_WEIGHT_IN_KG.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAthleteEvent() throws Exception {
        // Get the athleteEvent
        restAthleteEventMockMvc.perform(get("/api/athlete-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAthleteEvent() throws Exception {
        // Initialize the database
        athleteEventRepository.saveAndFlush(athleteEvent);

        int databaseSizeBeforeUpdate = athleteEventRepository.findAll().size();

        // Update the athleteEvent
        AthleteEvent updatedAthleteEvent = athleteEventRepository.findById(athleteEvent.getId()).get();
        // Disconnect from session so that the updates on updatedAthleteEvent are not directly saved in db
        em.detach(updatedAthleteEvent);
        updatedAthleteEvent
            .note(UPDATED_NOTE)
            .actualHeightInCm(UPDATED_ACTUAL_HEIGHT_IN_CM)
            .actualWeightInKg(UPDATED_ACTUAL_WEIGHT_IN_KG);
        AthleteEventDTO athleteEventDTO = athleteEventMapper.toDto(updatedAthleteEvent);

        restAthleteEventMockMvc.perform(put("/api/athlete-events")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteEventDTO)))
            .andExpect(status().isOk());

        // Validate the AthleteEvent in the database
        List<AthleteEvent> athleteEventList = athleteEventRepository.findAll();
        assertThat(athleteEventList).hasSize(databaseSizeBeforeUpdate);
        AthleteEvent testAthleteEvent = athleteEventList.get(athleteEventList.size() - 1);
        assertThat(testAthleteEvent.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testAthleteEvent.getActualHeightInCm()).isEqualTo(UPDATED_ACTUAL_HEIGHT_IN_CM);
        assertThat(testAthleteEvent.getActualWeightInKg()).isEqualTo(UPDATED_ACTUAL_WEIGHT_IN_KG);
    }

    @Test
    @Transactional
    public void updateNonExistingAthleteEvent() throws Exception {
        int databaseSizeBeforeUpdate = athleteEventRepository.findAll().size();

        // Create the AthleteEvent
        AthleteEventDTO athleteEventDTO = athleteEventMapper.toDto(athleteEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAthleteEventMockMvc.perform(put("/api/athlete-events")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AthleteEvent in the database
        List<AthleteEvent> athleteEventList = athleteEventRepository.findAll();
        assertThat(athleteEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAthleteEvent() throws Exception {
        // Initialize the database
        athleteEventRepository.saveAndFlush(athleteEvent);

        int databaseSizeBeforeDelete = athleteEventRepository.findAll().size();

        // Delete the athleteEvent
        restAthleteEventMockMvc.perform(delete("/api/athlete-events/{id}", athleteEvent.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AthleteEvent> athleteEventList = athleteEventRepository.findAll();
        assertThat(athleteEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
