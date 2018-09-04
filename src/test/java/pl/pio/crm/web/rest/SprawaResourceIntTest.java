package pl.pio.crm.web.rest;

import pl.pio.crm.CrmApp;

import pl.pio.crm.domain.Sprawa;
import pl.pio.crm.repository.SprawaRepository;
import pl.pio.crm.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static pl.pio.crm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SprawaResource REST controller.
 *
 * @see SprawaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrmApp.class)
public class SprawaResourceIntTest {

    private static final String DEFAULT_STAN = "AAAAAAAAAA";
    private static final String UPDATED_STAN = "BBBBBBBBBB";

    private static final String DEFAULT_KWOTA = "AAAAAAAAAA";
    private static final String UPDATED_KWOTA = "BBBBBBBBBB";

    private static final String DEFAULT_KLIENT = "AAAAAAAAAA";
    private static final String UPDATED_KLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_ADRES = "AAAAAAAAAA";
    private static final String UPDATED_ADRES = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_NUMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UTWORZONA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UTWORZONA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SprawaRepository sprawaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSprawaMockMvc;

    private Sprawa sprawa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SprawaResource sprawaResource = new SprawaResource(sprawaRepository);
        this.restSprawaMockMvc = MockMvcBuilders.standaloneSetup(sprawaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sprawa createEntity(EntityManager em) {
        Sprawa sprawa = new Sprawa()
            .stan(DEFAULT_STAN)
            .kwota(DEFAULT_KWOTA)
            .klient(DEFAULT_KLIENT)
            .adres(DEFAULT_ADRES)
            .telefon(DEFAULT_TELEFON)
            .status(DEFAULT_STATUS)
            .numer(DEFAULT_NUMER)
            .utworzona(DEFAULT_UTWORZONA)
            .data(DEFAULT_DATA);
        return sprawa;
    }

    @Before
    public void initTest() {
        sprawa = createEntity(em);
    }

    @Test
    @Transactional
    public void createSprawa() throws Exception {
        int databaseSizeBeforeCreate = sprawaRepository.findAll().size();

        // Create the Sprawa
        restSprawaMockMvc.perform(post("/api/sprawas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sprawa)))
            .andExpect(status().isCreated());

        // Validate the Sprawa in the database
        List<Sprawa> sprawaList = sprawaRepository.findAll();
        assertThat(sprawaList).hasSize(databaseSizeBeforeCreate + 1);
        Sprawa testSprawa = sprawaList.get(sprawaList.size() - 1);
        assertThat(testSprawa.getStan()).isEqualTo(DEFAULT_STAN);
        assertThat(testSprawa.getKwota()).isEqualTo(DEFAULT_KWOTA);
        assertThat(testSprawa.getKlient()).isEqualTo(DEFAULT_KLIENT);
        assertThat(testSprawa.getAdres()).isEqualTo(DEFAULT_ADRES);
        assertThat(testSprawa.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testSprawa.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSprawa.getNumer()).isEqualTo(DEFAULT_NUMER);
        assertThat(testSprawa.getUtworzona()).isEqualTo(DEFAULT_UTWORZONA);
        assertThat(testSprawa.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createSprawaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sprawaRepository.findAll().size();

        // Create the Sprawa with an existing ID
        sprawa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSprawaMockMvc.perform(post("/api/sprawas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sprawa)))
            .andExpect(status().isBadRequest());

        // Validate the Sprawa in the database
        List<Sprawa> sprawaList = sprawaRepository.findAll();
        assertThat(sprawaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSprawas() throws Exception {
        // Initialize the database
        sprawaRepository.saveAndFlush(sprawa);

        // Get all the sprawaList
        restSprawaMockMvc.perform(get("/api/sprawas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sprawa.getId().intValue())))
            .andExpect(jsonPath("$.[*].stan").value(hasItem(DEFAULT_STAN.toString())))
            .andExpect(jsonPath("$.[*].kwota").value(hasItem(DEFAULT_KWOTA.toString())))
            .andExpect(jsonPath("$.[*].klient").value(hasItem(DEFAULT_KLIENT.toString())))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES.toString())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].numer").value(hasItem(DEFAULT_NUMER.toString())))
            .andExpect(jsonPath("$.[*].utworzona").value(hasItem(DEFAULT_UTWORZONA.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getSprawa() throws Exception {
        // Initialize the database
        sprawaRepository.saveAndFlush(sprawa);

        // Get the sprawa
        restSprawaMockMvc.perform(get("/api/sprawas/{id}", sprawa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sprawa.getId().intValue()))
            .andExpect(jsonPath("$.stan").value(DEFAULT_STAN.toString()))
            .andExpect(jsonPath("$.kwota").value(DEFAULT_KWOTA.toString()))
            .andExpect(jsonPath("$.klient").value(DEFAULT_KLIENT.toString()))
            .andExpect(jsonPath("$.adres").value(DEFAULT_ADRES.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.numer").value(DEFAULT_NUMER.toString()))
            .andExpect(jsonPath("$.utworzona").value(DEFAULT_UTWORZONA.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSprawa() throws Exception {
        // Get the sprawa
        restSprawaMockMvc.perform(get("/api/sprawas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSprawa() throws Exception {
        // Initialize the database
        sprawaRepository.saveAndFlush(sprawa);

        int databaseSizeBeforeUpdate = sprawaRepository.findAll().size();

        // Update the sprawa
        Sprawa updatedSprawa = sprawaRepository.findById(sprawa.getId()).get();
        // Disconnect from session so that the updates on updatedSprawa are not directly saved in db
        em.detach(updatedSprawa);
        updatedSprawa
            .stan(UPDATED_STAN)
            .kwota(UPDATED_KWOTA)
            .klient(UPDATED_KLIENT)
            .adres(UPDATED_ADRES)
            .telefon(UPDATED_TELEFON)
            .status(UPDATED_STATUS)
            .numer(UPDATED_NUMER)
            .utworzona(UPDATED_UTWORZONA)
            .data(UPDATED_DATA);

        restSprawaMockMvc.perform(put("/api/sprawas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSprawa)))
            .andExpect(status().isOk());

        // Validate the Sprawa in the database
        List<Sprawa> sprawaList = sprawaRepository.findAll();
        assertThat(sprawaList).hasSize(databaseSizeBeforeUpdate);
        Sprawa testSprawa = sprawaList.get(sprawaList.size() - 1);
        assertThat(testSprawa.getStan()).isEqualTo(UPDATED_STAN);
        assertThat(testSprawa.getKwota()).isEqualTo(UPDATED_KWOTA);
        assertThat(testSprawa.getKlient()).isEqualTo(UPDATED_KLIENT);
        assertThat(testSprawa.getAdres()).isEqualTo(UPDATED_ADRES);
        assertThat(testSprawa.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testSprawa.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSprawa.getNumer()).isEqualTo(UPDATED_NUMER);
        assertThat(testSprawa.getUtworzona()).isEqualTo(UPDATED_UTWORZONA);
        assertThat(testSprawa.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingSprawa() throws Exception {
        int databaseSizeBeforeUpdate = sprawaRepository.findAll().size();

        // Create the Sprawa

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSprawaMockMvc.perform(put("/api/sprawas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sprawa)))
            .andExpect(status().isBadRequest());

        // Validate the Sprawa in the database
        List<Sprawa> sprawaList = sprawaRepository.findAll();
        assertThat(sprawaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSprawa() throws Exception {
        // Initialize the database
        sprawaRepository.saveAndFlush(sprawa);

        int databaseSizeBeforeDelete = sprawaRepository.findAll().size();

        // Get the sprawa
        restSprawaMockMvc.perform(delete("/api/sprawas/{id}", sprawa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sprawa> sprawaList = sprawaRepository.findAll();
        assertThat(sprawaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sprawa.class);
        Sprawa sprawa1 = new Sprawa();
        sprawa1.setId(1L);
        Sprawa sprawa2 = new Sprawa();
        sprawa2.setId(sprawa1.getId());
        assertThat(sprawa1).isEqualTo(sprawa2);
        sprawa2.setId(2L);
        assertThat(sprawa1).isNotEqualTo(sprawa2);
        sprawa1.setId(null);
        assertThat(sprawa1).isNotEqualTo(sprawa2);
    }
}
