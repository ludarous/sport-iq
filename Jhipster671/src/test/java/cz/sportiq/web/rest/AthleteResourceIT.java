package cz.sportiq.web.rest;

import cz.sportiq.SportiqApp;
import cz.sportiq.config.TestSecurityConfiguration;
import cz.sportiq.domain.Athlete;
import cz.sportiq.repository.AthleteRepository;
import cz.sportiq.service.AthleteService;
import cz.sportiq.service.dto.AthleteDTO;
import cz.sportiq.service.mapper.AthleteMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static cz.sportiq.web.rest.TestUtil.sameInstant;
import static cz.sportiq.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cz.sportiq.domain.enumeration.Sex;
/**
 * Integration tests for the {@link AthleteResource} REST controller.
 */
@SpringBootTest(classes = {SportiqApp.class, TestSecurityConfiguration.class})
public class AthleteResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BIRTH_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BIRTH_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final Sex DEFAULT_SEX = Sex.MALE;
    private static final Sex UPDATED_SEX = Sex.FEMALE;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private AthleteMapper athleteMapper;

    @Autowired
    private AthleteService athleteService;

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

    private MockMvc restAthleteMockMvc;

    private Athlete athlete;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AthleteResource athleteResource = new AthleteResource(athleteService);
        this.restAthleteMockMvc = MockMvcBuilders.standaloneSetup(athleteResource)
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
    public static Athlete createEntity(EntityManager em) {
        Athlete athlete = new Athlete()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .birthDate(DEFAULT_BIRTH_DATE)
            .nationality(DEFAULT_NATIONALITY)
            .sex(DEFAULT_SEX);
        return athlete;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Athlete createUpdatedEntity(EntityManager em) {
        Athlete athlete = new Athlete()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .birthDate(UPDATED_BIRTH_DATE)
            .nationality(UPDATED_NATIONALITY)
            .sex(UPDATED_SEX);
        return athlete;
    }

    @BeforeEach
    public void initTest() {
        athlete = createEntity(em);
    }

    @Test
    @Transactional
    public void createAthlete() throws Exception {
        int databaseSizeBeforeCreate = athleteRepository.findAll().size();

        // Create the Athlete
        AthleteDTO athleteDTO = athleteMapper.toDto(athlete);
        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteDTO)))
            .andExpect(status().isCreated());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeCreate + 1);
        Athlete testAthlete = athleteList.get(athleteList.size() - 1);
        assertThat(testAthlete.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAthlete.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAthlete.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAthlete.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testAthlete.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testAthlete.getSex()).isEqualTo(DEFAULT_SEX);
    }

    @Test
    @Transactional
    public void createAthleteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = athleteRepository.findAll().size();

        // Create the Athlete with an existing ID
        athlete.setId(1L);
        AthleteDTO athleteDTO = athleteMapper.toDto(athlete);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = athleteRepository.findAll().size();
        // set the field null
        athlete.setFirstName(null);

        // Create the Athlete, which fails.
        AthleteDTO athleteDTO = athleteMapper.toDto(athlete);

        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteDTO)))
            .andExpect(status().isBadRequest());

        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = athleteRepository.findAll().size();
        // set the field null
        athlete.setLastName(null);

        // Create the Athlete, which fails.
        AthleteDTO athleteDTO = athleteMapper.toDto(athlete);

        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteDTO)))
            .andExpect(status().isBadRequest());

        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = athleteRepository.findAll().size();
        // set the field null
        athlete.setEmail(null);

        // Create the Athlete, which fails.
        AthleteDTO athleteDTO = athleteMapper.toDto(athlete);

        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteDTO)))
            .andExpect(status().isBadRequest());

        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAthletes() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList
        restAthleteMockMvc.perform(get("/api/athletes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(athlete.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(sameInstant(DEFAULT_BIRTH_DATE))))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())));
    }
    
    @Test
    @Transactional
    public void getAthlete() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get the athlete
        restAthleteMockMvc.perform(get("/api/athletes/{id}", athlete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(athlete.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.birthDate").value(sameInstant(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAthlete() throws Exception {
        // Get the athlete
        restAthleteMockMvc.perform(get("/api/athletes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAthlete() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        int databaseSizeBeforeUpdate = athleteRepository.findAll().size();

        // Update the athlete
        Athlete updatedAthlete = athleteRepository.findById(athlete.getId()).get();
        // Disconnect from session so that the updates on updatedAthlete are not directly saved in db
        em.detach(updatedAthlete);
        updatedAthlete
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .birthDate(UPDATED_BIRTH_DATE)
            .nationality(UPDATED_NATIONALITY)
            .sex(UPDATED_SEX);
        AthleteDTO athleteDTO = athleteMapper.toDto(updatedAthlete);

        restAthleteMockMvc.perform(put("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteDTO)))
            .andExpect(status().isOk());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeUpdate);
        Athlete testAthlete = athleteList.get(athleteList.size() - 1);
        assertThat(testAthlete.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAthlete.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAthlete.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAthlete.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testAthlete.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testAthlete.getSex()).isEqualTo(UPDATED_SEX);
    }

    @Test
    @Transactional
    public void updateNonExistingAthlete() throws Exception {
        int databaseSizeBeforeUpdate = athleteRepository.findAll().size();

        // Create the Athlete
        AthleteDTO athleteDTO = athleteMapper.toDto(athlete);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAthleteMockMvc.perform(put("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(athleteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAthlete() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        int databaseSizeBeforeDelete = athleteRepository.findAll().size();

        // Delete the athlete
        restAthleteMockMvc.perform(delete("/api/athletes/{id}", athlete.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
