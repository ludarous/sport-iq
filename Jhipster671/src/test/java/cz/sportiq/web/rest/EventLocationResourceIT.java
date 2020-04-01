package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;
import cz.sportiq.config.TestSecurityConfiguration;
import cz.sportiq.domain.EventLocation;
import cz.sportiq.repository.EventLocationRepository;
import cz.sportiq.service.EventLocationService;
import cz.sportiq.service.dto.EventLocationDTO;
import cz.sportiq.service.mapper.EventLocationMapper;
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
 * Integration tests for the {@link EventLocationResource} REST controller.
 */
@SpringBootTest(classes = {SportiqApp.class, TestSecurityConfiguration.class})
public class EventLocationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NUMBER = "BBBBBBBBBB";

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static final String DEFAULT_MAP_LINK = "AAAAAAAAAA";
    private static final String UPDATED_MAP_LINK = "BBBBBBBBBB";

    @Autowired
    private EventLocationRepository eventLocationRepository;

    @Autowired
    private EventLocationMapper eventLocationMapper;

    @Autowired
    private EventLocationService eventLocationService;

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

    private MockMvc restEventLocationMockMvc;

    private EventLocation eventLocation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventLocationResource eventLocationResource = new EventLocationResource(eventLocationService);
        this.restEventLocationMockMvc = MockMvcBuilders.standaloneSetup(eventLocationResource)
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
    public static EventLocation createEntity(EntityManager em) {
        EventLocation eventLocation = new EventLocation()
            .name(DEFAULT_NAME)
            .state(DEFAULT_STATE)
            .city(DEFAULT_CITY)
            .street(DEFAULT_STREET)
            .streetNumber(DEFAULT_STREET_NUMBER)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .capacity(DEFAULT_CAPACITY)
            .mapLink(DEFAULT_MAP_LINK);
        return eventLocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventLocation createUpdatedEntity(EntityManager em) {
        EventLocation eventLocation = new EventLocation()
            .name(UPDATED_NAME)
            .state(UPDATED_STATE)
            .city(UPDATED_CITY)
            .street(UPDATED_STREET)
            .streetNumber(UPDATED_STREET_NUMBER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .capacity(UPDATED_CAPACITY)
            .mapLink(UPDATED_MAP_LINK);
        return eventLocation;
    }

    @BeforeEach
    public void initTest() {
        eventLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventLocation() throws Exception {
        int databaseSizeBeforeCreate = eventLocationRepository.findAll().size();

        // Create the EventLocation
        EventLocationDTO eventLocationDTO = eventLocationMapper.toDto(eventLocation);
        restEventLocationMockMvc.perform(post("/api/event-locations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the EventLocation in the database
        List<EventLocation> eventLocationList = eventLocationRepository.findAll();
        assertThat(eventLocationList).hasSize(databaseSizeBeforeCreate + 1);
        EventLocation testEventLocation = eventLocationList.get(eventLocationList.size() - 1);
        assertThat(testEventLocation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEventLocation.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testEventLocation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testEventLocation.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testEventLocation.getStreetNumber()).isEqualTo(DEFAULT_STREET_NUMBER);
        assertThat(testEventLocation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testEventLocation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testEventLocation.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testEventLocation.getMapLink()).isEqualTo(DEFAULT_MAP_LINK);
    }

    @Test
    @Transactional
    public void createEventLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventLocationRepository.findAll().size();

        // Create the EventLocation with an existing ID
        eventLocation.setId(1L);
        EventLocationDTO eventLocationDTO = eventLocationMapper.toDto(eventLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventLocationMockMvc.perform(post("/api/event-locations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventLocation in the database
        List<EventLocation> eventLocationList = eventLocationRepository.findAll();
        assertThat(eventLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventLocationRepository.findAll().size();
        // set the field null
        eventLocation.setName(null);

        // Create the EventLocation, which fails.
        EventLocationDTO eventLocationDTO = eventLocationMapper.toDto(eventLocation);

        restEventLocationMockMvc.perform(post("/api/event-locations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventLocationDTO)))
            .andExpect(status().isBadRequest());

        List<EventLocation> eventLocationList = eventLocationRepository.findAll();
        assertThat(eventLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventLocations() throws Exception {
        // Initialize the database
        eventLocationRepository.saveAndFlush(eventLocation);

        // Get all the eventLocationList
        restEventLocationMockMvc.perform(get("/api/event-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].streetNumber").value(hasItem(DEFAULT_STREET_NUMBER)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].mapLink").value(hasItem(DEFAULT_MAP_LINK)));
    }
    
    @Test
    @Transactional
    public void getEventLocation() throws Exception {
        // Initialize the database
        eventLocationRepository.saveAndFlush(eventLocation);

        // Get the eventLocation
        restEventLocationMockMvc.perform(get("/api/event-locations/{id}", eventLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventLocation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.streetNumber").value(DEFAULT_STREET_NUMBER))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.mapLink").value(DEFAULT_MAP_LINK));
    }

    @Test
    @Transactional
    public void getNonExistingEventLocation() throws Exception {
        // Get the eventLocation
        restEventLocationMockMvc.perform(get("/api/event-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventLocation() throws Exception {
        // Initialize the database
        eventLocationRepository.saveAndFlush(eventLocation);

        int databaseSizeBeforeUpdate = eventLocationRepository.findAll().size();

        // Update the eventLocation
        EventLocation updatedEventLocation = eventLocationRepository.findById(eventLocation.getId()).get();
        // Disconnect from session so that the updates on updatedEventLocation are not directly saved in db
        em.detach(updatedEventLocation);
        updatedEventLocation
            .name(UPDATED_NAME)
            .state(UPDATED_STATE)
            .city(UPDATED_CITY)
            .street(UPDATED_STREET)
            .streetNumber(UPDATED_STREET_NUMBER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .capacity(UPDATED_CAPACITY)
            .mapLink(UPDATED_MAP_LINK);
        EventLocationDTO eventLocationDTO = eventLocationMapper.toDto(updatedEventLocation);

        restEventLocationMockMvc.perform(put("/api/event-locations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventLocationDTO)))
            .andExpect(status().isOk());

        // Validate the EventLocation in the database
        List<EventLocation> eventLocationList = eventLocationRepository.findAll();
        assertThat(eventLocationList).hasSize(databaseSizeBeforeUpdate);
        EventLocation testEventLocation = eventLocationList.get(eventLocationList.size() - 1);
        assertThat(testEventLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEventLocation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testEventLocation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEventLocation.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testEventLocation.getStreetNumber()).isEqualTo(UPDATED_STREET_NUMBER);
        assertThat(testEventLocation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEventLocation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testEventLocation.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testEventLocation.getMapLink()).isEqualTo(UPDATED_MAP_LINK);
    }

    @Test
    @Transactional
    public void updateNonExistingEventLocation() throws Exception {
        int databaseSizeBeforeUpdate = eventLocationRepository.findAll().size();

        // Create the EventLocation
        EventLocationDTO eventLocationDTO = eventLocationMapper.toDto(eventLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventLocationMockMvc.perform(put("/api/event-locations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventLocation in the database
        List<EventLocation> eventLocationList = eventLocationRepository.findAll();
        assertThat(eventLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventLocation() throws Exception {
        // Initialize the database
        eventLocationRepository.saveAndFlush(eventLocation);

        int databaseSizeBeforeDelete = eventLocationRepository.findAll().size();

        // Delete the eventLocation
        restEventLocationMockMvc.perform(delete("/api/event-locations/{id}", eventLocation.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventLocation> eventLocationList = eventLocationRepository.findAll();
        assertThat(eventLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
