package cz.sportiq.service.impl;

import cz.sportiq.service.AthleteEventService;
import cz.sportiq.domain.AthleteEvent;
import cz.sportiq.repository.AthleteEventRepository;
import cz.sportiq.service.dto.AthleteEventDTO;
import cz.sportiq.service.mapper.AthleteEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AthleteEvent}.
 */
@Service
@Transactional
public class AthleteEventServiceImpl implements AthleteEventService {

    private final Logger log = LoggerFactory.getLogger(AthleteEventServiceImpl.class);

    private final AthleteEventRepository athleteEventRepository;

    private final AthleteEventMapper athleteEventMapper;

    public AthleteEventServiceImpl(AthleteEventRepository athleteEventRepository, AthleteEventMapper athleteEventMapper) {
        this.athleteEventRepository = athleteEventRepository;
        this.athleteEventMapper = athleteEventMapper;
    }

    /**
     * Save a athleteEvent.
     *
     * @param athleteEventDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AthleteEventDTO save(AthleteEventDTO athleteEventDTO) {
        log.debug("Request to save AthleteEvent : {}", athleteEventDTO);
        AthleteEvent athleteEvent = athleteEventMapper.toEntity(athleteEventDTO);
        athleteEvent = athleteEventRepository.save(athleteEvent);
        return athleteEventMapper.toDto(athleteEvent);
    }

    /**
     * Get all the athleteEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AthleteEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AthleteEvents");
        return athleteEventRepository.findAll(pageable)
            .map(athleteEventMapper::toDto);
    }

    /**
     * Get one athleteEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AthleteEventDTO> findOne(Long id) {
        log.debug("Request to get AthleteEvent : {}", id);
        return athleteEventRepository.findById(id)
            .map(athleteEventMapper::toDto);
    }

    /**
     * Delete the athleteEvent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AthleteEvent : {}", id);
        athleteEventRepository.deleteById(id);
    }
}
