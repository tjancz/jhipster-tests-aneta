package pl.pio.crm.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.pio.crm.domain.Sprawa;
import pl.pio.crm.repository.SprawaRepository;
import pl.pio.crm.web.rest.errors.BadRequestAlertException;
import pl.pio.crm.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Sprawa.
 */
@RestController
@RequestMapping("/api")
public class SprawaResource {

    private final Logger log = LoggerFactory.getLogger(SprawaResource.class);

    private static final String ENTITY_NAME = "sprawa";

    private final SprawaRepository sprawaRepository;

    public SprawaResource(SprawaRepository sprawaRepository) {
        this.sprawaRepository = sprawaRepository;
    }

    /**
     * POST  /sprawas : Create a new sprawa.
     *
     * @param sprawa the sprawa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sprawa, or with status 400 (Bad Request) if the sprawa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sprawas")
    @Timed
    public ResponseEntity<Sprawa> createSprawa(@RequestBody Sprawa sprawa) throws URISyntaxException {
        log.debug("REST request to save Sprawa : {}", sprawa);
        if (sprawa.getId() != null) {
            throw new BadRequestAlertException("A new sprawa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sprawa result = sprawaRepository.save(sprawa);
        return ResponseEntity.created(new URI("/api/sprawas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sprawas : Updates an existing sprawa.
     *
     * @param sprawa the sprawa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sprawa,
     * or with status 400 (Bad Request) if the sprawa is not valid,
     * or with status 500 (Internal Server Error) if the sprawa couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sprawas")
    @Timed
    public ResponseEntity<Sprawa> updateSprawa(@RequestBody Sprawa sprawa) throws URISyntaxException {
        log.debug("REST request to update Sprawa : {}", sprawa);
        if (sprawa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sprawa result = sprawaRepository.save(sprawa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sprawa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sprawas : get all the sprawas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sprawas in body
     */
    @GetMapping("/sprawas")
    @Timed
    public List<Sprawa> getAllSprawas() {
        log.debug("REST request to get all Sprawas");
        return sprawaRepository.findAll();
    }

    /**
     * GET  /sprawas/:id : get the "id" sprawa.
     *
     * @param id the id of the sprawa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sprawa, or with status 404 (Not Found)
     */
    @GetMapping("/sprawas/{id}")
    @Timed
    public ResponseEntity<Sprawa> getSprawa(@PathVariable Long id) {
        log.debug("REST request to get Sprawa : {}", id);
        Optional<Sprawa> sprawa = sprawaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sprawa);
    }

    /**
     * DELETE  /sprawas/:id : delete the "id" sprawa.
     *
     * @param id the id of the sprawa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sprawas/{id}")
    @Timed
    public ResponseEntity<Void> deleteSprawa(@PathVariable Long id) {
        log.debug("REST request to delete Sprawa : {}", id);

        sprawaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
