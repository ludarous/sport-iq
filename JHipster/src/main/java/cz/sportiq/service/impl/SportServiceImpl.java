package cz.sportiq.service.impl;

import cz.sportiq.service.SportService;
import cz.sportiq.domain.Sport;
import cz.sportiq.repository.SportRepository;
import cz.sportiq.repository.search.SportSearchRepository;
import cz.sportiq.service.dto.SportDTO;
import cz.sportiq.service.mapper.SportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Sport}.
 */
@Service
@Transactional
public class SportServiceImpl implements SportService {

    private final Logger log = LoggerFactory.getLogger(SportServiceImpl.class);

    private final SportRepository sportRepository;

    private final SportMapper sportMapper;

    private final SportSearchRepository sportSearchRepository;

    public SportServiceImpl(SportRepository sportRepository, SportMapper sportMapper, SportSearchRepository sportSearchRepository) {
        this.sportRepository = sportRepository;
        this.sportMapper = sportMapper;
        this.sportSearchRepository = sportSearchRepository;
    }

    /**
     * Save a sport.
     *
     * @param sportDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SportDTO save(SportDTO sportDTO) {
        log.debug("Request to save Sport : {}", sportDTO);
        Sport sport = sportMapper.toEntity(sportDTO);
        sport = sportRepository.save(sport);
        SportDTO result = sportMapper.toDto(sport);
        sportSearchRepository.save(sport);
        return result;
    }

    /**
     * Get all the sports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sports");
        return sportRepository.findAll(pageable)
            .map(sportMapper::toDto);
    }


    /**
     * Get one sport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SportDTO> findOne(Long id) {
        log.debug("Request to get Sport : {}", id);
        return sportRepository.findById(id)
            .map(sportMapper::toDto);
    }

    /**
     * Delete the sport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sport : {}", id);
        sportRepository.deleteById(id);
        sportSearchRepository.deleteById(id);
    }

    /**
     * Search for the sport corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SportDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sports for query {}", query);
        return sportSearchRepository.search(queryStringQuery(query), pageable)
            .map(sportMapper::toDto);
    }
}
