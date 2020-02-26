package cz.sportiq.service.impl;

import cz.sportiq.service.AthleteService;
import cz.sportiq.domain.Athlete;
import cz.sportiq.repository.AthleteRepository;
import cz.sportiq.service.dto.AthleteDTO;
import cz.sportiq.service.mapper.AthleteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Athlete}.
 */
@Service
@Transactional
public class AthleteServiceImpl implements AthleteService {

    private final Logger log = LoggerFactory.getLogger(AthleteServiceImpl.class);

    private final AthleteRepository athleteRepository;

    private final AthleteMapper athleteMapper;

    public AthleteServiceImpl(AthleteRepository athleteRepository, AthleteMapper athleteMapper) {
        this.athleteRepository = athleteRepository;
        this.athleteMapper = athleteMapper;
    }

    /**
     * Save a athlete.
     *
     * @param athleteDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AthleteDTO save(AthleteDTO athleteDTO) {
        log.debug("Request to save Athlete : {}", athleteDTO);
        Athlete athlete = athleteMapper.toEntity(athleteDTO);
        athlete = athleteRepository.save(athlete);
        return athleteMapper.toDto(athlete);
    }

    /**
     * Get all the athletes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AthleteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Athletes");
        return athleteRepository.findAll(pageable)
            .map(athleteMapper::toDto);
    }

    /**
     * Get one athlete by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AthleteDTO> findOne(Long id) {
        log.debug("Request to get Athlete : {}", id);
        return athleteRepository.findById(id)
            .map(athleteMapper::toDto);
    }

    /**
     * Delete the athlete by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Athlete : {}", id);
        athleteRepository.deleteById(id);
    }
}
