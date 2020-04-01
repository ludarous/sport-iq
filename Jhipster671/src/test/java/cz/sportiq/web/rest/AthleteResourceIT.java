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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.List;

import static cz.sportiq.web.rest.TestUtil.sameInstant;
import static cz.sportiq.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cz.sportiq.domain.enumeration.Sex;
import cz.sportiq.domain.enumeration.Laterality;
import cz.sportiq.domain.enumeration.Laterality;
import cz.sportiq.domain.enumeration.Laterality;
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

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BIRTH_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BIRTH_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final Sex DEFAULT_SEX = Sex.MALE;
    private static final Sex UPDATED_SEX = Sex.FEMALE;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final Laterality DEFAULT_HAND_LATERALITY = Laterality.LEFT;
    private static final Laterality UPDATED_HAND_LATERALITY = Laterality.RIGHT;

    private static final Laterality DEFAULT_FOOT_LATERALITY = Laterality.LEFT;
    private static final Laterality UPDATED_FOOT_LATERALITY = Laterality.RIGHT;

    private static final Laterality DEFAULT_STEPPING_FOOT = Laterality.LEFT;
    private static final Laterality UPDATED_STEPPING_FOOT = Laterality.RIGHT;

    private static final Boolean DEFAULT_TERMS_AGREEMENT = false;
    private static final Boolean UPDATED_TERMS_AGREEMENT = true;

    private static final Boolean DEFAULT_GDPR_AGREEMENT = false;
    private static final Boolean UPDATED_GDPR_AGREEMENT = true;

    private static final Boolean DEFAULT_PHOTOGRAPHY_AGREEMENT = false;
    private static final Boolean UPDATED_PHOTOGRAPHY_AGREEMENT = true;

    private static final Boolean DEFAULT_MEDICAL_FITNESS_AGREEMENT = false;
    private static final Boolean UPDATED_MEDICAL_FITNESS_AGREEMENT = true;

    private static final Boolean DEFAULT_MARKETING_AGREEMENT = false;
    private static final Boolean UPDATED_MARKETING_AGREEMENT = true;

    private static final String DEFAULT_LR_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LR_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LR_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LR_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LR_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_LR_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_LR_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_LR_PHONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PROFILE_COMPLETED = false;
    private static final Boolean UPDATED_PROFILE_COMPLETED = true;

    @Autowired
    private AthleteRepository athleteRepository;

    @Mock
    private AthleteRepository athleteRepositoryMock;

    @Autowired
    private AthleteMapper athleteMapper;

    @Mock
    private AthleteService athleteServiceMock;

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
            .phone(DEFAULT_PHONE)
            .birthDate(DEFAULT_BIRTH_DATE)
            .nationality(DEFAULT_NATIONALITY)
            .sex(DEFAULT_SEX)
            .country(DEFAULT_COUNTRY)
            .city(DEFAULT_CITY)
            .street(DEFAULT_STREET)
            .zipCode(DEFAULT_ZIP_CODE)
            .handLaterality(DEFAULT_HAND_LATERALITY)
            .footLaterality(DEFAULT_FOOT_LATERALITY)
            .steppingFoot(DEFAULT_STEPPING_FOOT)
            .termsAgreement(DEFAULT_TERMS_AGREEMENT)
            .gdprAgreement(DEFAULT_GDPR_AGREEMENT)
            .photographyAgreement(DEFAULT_PHOTOGRAPHY_AGREEMENT)
            .medicalFitnessAgreement(DEFAULT_MEDICAL_FITNESS_AGREEMENT)
            .marketingAgreement(DEFAULT_MARKETING_AGREEMENT)
            .lrFirstName(DEFAULT_LR_FIRST_NAME)
            .lrLastName(DEFAULT_LR_LAST_NAME)
            .lrEmail(DEFAULT_LR_EMAIL)
            .lrPhone(DEFAULT_LR_PHONE)
            .profileCompleted(DEFAULT_PROFILE_COMPLETED);
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
            .phone(UPDATED_PHONE)
            .birthDate(UPDATED_BIRTH_DATE)
            .nationality(UPDATED_NATIONALITY)
            .sex(UPDATED_SEX)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .street(UPDATED_STREET)
            .zipCode(UPDATED_ZIP_CODE)
            .handLaterality(UPDATED_HAND_LATERALITY)
            .footLaterality(UPDATED_FOOT_LATERALITY)
            .steppingFoot(UPDATED_STEPPING_FOOT)
            .termsAgreement(UPDATED_TERMS_AGREEMENT)
            .gdprAgreement(UPDATED_GDPR_AGREEMENT)
            .photographyAgreement(UPDATED_PHOTOGRAPHY_AGREEMENT)
            .medicalFitnessAgreement(UPDATED_MEDICAL_FITNESS_AGREEMENT)
            .marketingAgreement(UPDATED_MARKETING_AGREEMENT)
            .lrFirstName(UPDATED_LR_FIRST_NAME)
            .lrLastName(UPDATED_LR_LAST_NAME)
            .lrEmail(UPDATED_LR_EMAIL)
            .lrPhone(UPDATED_LR_PHONE)
            .profileCompleted(UPDATED_PROFILE_COMPLETED);
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
        assertThat(testAthlete.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testAthlete.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testAthlete.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testAthlete.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testAthlete.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAthlete.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAthlete.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testAthlete.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testAthlete.getHandLaterality()).isEqualTo(DEFAULT_HAND_LATERALITY);
        assertThat(testAthlete.getFootLaterality()).isEqualTo(DEFAULT_FOOT_LATERALITY);
        assertThat(testAthlete.getSteppingFoot()).isEqualTo(DEFAULT_STEPPING_FOOT);
        assertThat(testAthlete.isTermsAgreement()).isEqualTo(DEFAULT_TERMS_AGREEMENT);
        assertThat(testAthlete.isGdprAgreement()).isEqualTo(DEFAULT_GDPR_AGREEMENT);
        assertThat(testAthlete.isPhotographyAgreement()).isEqualTo(DEFAULT_PHOTOGRAPHY_AGREEMENT);
        assertThat(testAthlete.isMedicalFitnessAgreement()).isEqualTo(DEFAULT_MEDICAL_FITNESS_AGREEMENT);
        assertThat(testAthlete.isMarketingAgreement()).isEqualTo(DEFAULT_MARKETING_AGREEMENT);
        assertThat(testAthlete.getLrFirstName()).isEqualTo(DEFAULT_LR_FIRST_NAME);
        assertThat(testAthlete.getLrLastName()).isEqualTo(DEFAULT_LR_LAST_NAME);
        assertThat(testAthlete.getLrEmail()).isEqualTo(DEFAULT_LR_EMAIL);
        assertThat(testAthlete.getLrPhone()).isEqualTo(DEFAULT_LR_PHONE);
        assertThat(testAthlete.isProfileCompleted()).isEqualTo(DEFAULT_PROFILE_COMPLETED);
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
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(sameInstant(DEFAULT_BIRTH_DATE))))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].handLaterality").value(hasItem(DEFAULT_HAND_LATERALITY.toString())))
            .andExpect(jsonPath("$.[*].footLaterality").value(hasItem(DEFAULT_FOOT_LATERALITY.toString())))
            .andExpect(jsonPath("$.[*].steppingFoot").value(hasItem(DEFAULT_STEPPING_FOOT.toString())))
            .andExpect(jsonPath("$.[*].termsAgreement").value(hasItem(DEFAULT_TERMS_AGREEMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].gdprAgreement").value(hasItem(DEFAULT_GDPR_AGREEMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].photographyAgreement").value(hasItem(DEFAULT_PHOTOGRAPHY_AGREEMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].medicalFitnessAgreement").value(hasItem(DEFAULT_MEDICAL_FITNESS_AGREEMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].marketingAgreement").value(hasItem(DEFAULT_MARKETING_AGREEMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].lrFirstName").value(hasItem(DEFAULT_LR_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lrLastName").value(hasItem(DEFAULT_LR_LAST_NAME)))
            .andExpect(jsonPath("$.[*].lrEmail").value(hasItem(DEFAULT_LR_EMAIL)))
            .andExpect(jsonPath("$.[*].lrPhone").value(hasItem(DEFAULT_LR_PHONE)))
            .andExpect(jsonPath("$.[*].profileCompleted").value(hasItem(DEFAULT_PROFILE_COMPLETED.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAthletesWithEagerRelationshipsIsEnabled() throws Exception {
        AthleteResource athleteResource = new AthleteResource(athleteServiceMock);
        when(athleteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAthleteMockMvc = MockMvcBuilders.standaloneSetup(athleteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAthleteMockMvc.perform(get("/api/athletes?eagerload=true"))
        .andExpect(status().isOk());

        verify(athleteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAthletesWithEagerRelationshipsIsNotEnabled() throws Exception {
        AthleteResource athleteResource = new AthleteResource(athleteServiceMock);
            when(athleteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAthleteMockMvc = MockMvcBuilders.standaloneSetup(athleteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAthleteMockMvc.perform(get("/api/athletes?eagerload=true"))
        .andExpect(status().isOk());

            verify(athleteServiceMock, times(1)).findAllWithEagerRelationships(any());
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
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.birthDate").value(sameInstant(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.handLaterality").value(DEFAULT_HAND_LATERALITY.toString()))
            .andExpect(jsonPath("$.footLaterality").value(DEFAULT_FOOT_LATERALITY.toString()))
            .andExpect(jsonPath("$.steppingFoot").value(DEFAULT_STEPPING_FOOT.toString()))
            .andExpect(jsonPath("$.termsAgreement").value(DEFAULT_TERMS_AGREEMENT.booleanValue()))
            .andExpect(jsonPath("$.gdprAgreement").value(DEFAULT_GDPR_AGREEMENT.booleanValue()))
            .andExpect(jsonPath("$.photographyAgreement").value(DEFAULT_PHOTOGRAPHY_AGREEMENT.booleanValue()))
            .andExpect(jsonPath("$.medicalFitnessAgreement").value(DEFAULT_MEDICAL_FITNESS_AGREEMENT.booleanValue()))
            .andExpect(jsonPath("$.marketingAgreement").value(DEFAULT_MARKETING_AGREEMENT.booleanValue()))
            .andExpect(jsonPath("$.lrFirstName").value(DEFAULT_LR_FIRST_NAME))
            .andExpect(jsonPath("$.lrLastName").value(DEFAULT_LR_LAST_NAME))
            .andExpect(jsonPath("$.lrEmail").value(DEFAULT_LR_EMAIL))
            .andExpect(jsonPath("$.lrPhone").value(DEFAULT_LR_PHONE))
            .andExpect(jsonPath("$.profileCompleted").value(DEFAULT_PROFILE_COMPLETED.booleanValue()));
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
            .phone(UPDATED_PHONE)
            .birthDate(UPDATED_BIRTH_DATE)
            .nationality(UPDATED_NATIONALITY)
            .sex(UPDATED_SEX)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .street(UPDATED_STREET)
            .zipCode(UPDATED_ZIP_CODE)
            .handLaterality(UPDATED_HAND_LATERALITY)
            .footLaterality(UPDATED_FOOT_LATERALITY)
            .steppingFoot(UPDATED_STEPPING_FOOT)
            .termsAgreement(UPDATED_TERMS_AGREEMENT)
            .gdprAgreement(UPDATED_GDPR_AGREEMENT)
            .photographyAgreement(UPDATED_PHOTOGRAPHY_AGREEMENT)
            .medicalFitnessAgreement(UPDATED_MEDICAL_FITNESS_AGREEMENT)
            .marketingAgreement(UPDATED_MARKETING_AGREEMENT)
            .lrFirstName(UPDATED_LR_FIRST_NAME)
            .lrLastName(UPDATED_LR_LAST_NAME)
            .lrEmail(UPDATED_LR_EMAIL)
            .lrPhone(UPDATED_LR_PHONE)
            .profileCompleted(UPDATED_PROFILE_COMPLETED);
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
        assertThat(testAthlete.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAthlete.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testAthlete.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testAthlete.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testAthlete.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAthlete.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAthlete.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testAthlete.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testAthlete.getHandLaterality()).isEqualTo(UPDATED_HAND_LATERALITY);
        assertThat(testAthlete.getFootLaterality()).isEqualTo(UPDATED_FOOT_LATERALITY);
        assertThat(testAthlete.getSteppingFoot()).isEqualTo(UPDATED_STEPPING_FOOT);
        assertThat(testAthlete.isTermsAgreement()).isEqualTo(UPDATED_TERMS_AGREEMENT);
        assertThat(testAthlete.isGdprAgreement()).isEqualTo(UPDATED_GDPR_AGREEMENT);
        assertThat(testAthlete.isPhotographyAgreement()).isEqualTo(UPDATED_PHOTOGRAPHY_AGREEMENT);
        assertThat(testAthlete.isMedicalFitnessAgreement()).isEqualTo(UPDATED_MEDICAL_FITNESS_AGREEMENT);
        assertThat(testAthlete.isMarketingAgreement()).isEqualTo(UPDATED_MARKETING_AGREEMENT);
        assertThat(testAthlete.getLrFirstName()).isEqualTo(UPDATED_LR_FIRST_NAME);
        assertThat(testAthlete.getLrLastName()).isEqualTo(UPDATED_LR_LAST_NAME);
        assertThat(testAthlete.getLrEmail()).isEqualTo(UPDATED_LR_EMAIL);
        assertThat(testAthlete.getLrPhone()).isEqualTo(UPDATED_LR_PHONE);
        assertThat(testAthlete.isProfileCompleted()).isEqualTo(UPDATED_PROFILE_COMPLETED);
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
