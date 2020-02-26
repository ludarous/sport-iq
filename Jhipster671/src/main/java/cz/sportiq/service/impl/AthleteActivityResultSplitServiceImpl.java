package cz.sportiq.service.impl;

import cz.sportiq.domain.ActivityResultSplit;
import cz.sportiq.domain.AthleteActivityResult;
import cz.sportiq.domain.AthleteActivityResultSplit;
import cz.sportiq.repository.AthleteActivityResultSplitRepository;
import cz.sportiq.service.AthleteActivityResultSplitService;
import cz.sportiq.service.dto.AthleteActivityResultSplitDTO;
import cz.sportiq.service.mapper.AthleteActivityResultSplitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing {@link AthleteActivityResultSplit}.
 */
@Service
@Transactional
public class AthleteActivityResultSplitServiceImpl implements AthleteActivityResultSplitService {

    private final Logger log = LoggerFactory.getLogger(AthleteActivityResultSplitServiceImpl.class);

    private final AthleteActivityResultSplitRepository athleteActivityResultSplitRepository;

    private final AthleteActivityResultSplitMapper athleteActivityResultSplitMapper;

    public AthleteActivityResultSplitServiceImpl(AthleteActivityResultSplitRepository athleteActivityResultSplitRepository, AthleteActivityResultSplitMapper athleteActivityResultSplitMapper) {
        this.athleteActivityResultSplitRepository = athleteActivityResultSplitRepository;
        this.athleteActivityResultSplitMapper = athleteActivityResultSplitMapper;
    }

    /**
     * Save a athleteActivityResultSplit.
     *
     * @param athleteActivityResultSplitDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AthleteActivityResultSplitDTO save(AthleteActivityResultSplitDTO athleteActivityResultSplitDTO) {
        log.debug("Request to save AthleteActivityResultSplit : {}", athleteActivityResultSplitDTO);
        AthleteActivityResultSplit athleteActivityResultSplit = athleteActivityResultSplitMapper.toEntity(athleteActivityResultSplitDTO);
        athleteActivityResultSplit = athleteActivityResultSplitRepository.save(athleteActivityResultSplit);
        AthleteActivityResultSplitDTO result = athleteActivityResultSplitMapper.toDto(athleteActivityResultSplit);
        return result;
    }

    /**
     * Get all the athleteActivityResultSplits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AthleteActivityResultSplitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AthleteActivityResultSplits");
        return athleteActivityResultSplitRepository.findAll(pageable)
            .map(athleteActivityResultSplitMapper::toDto);
    }


    /**
     * Get one athleteActivityResultSplit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AthleteActivityResultSplitDTO> findOne(Long id) {
        log.debug("Request to get AthleteActivityResultSplit : {}", id);
        return athleteActivityResultSplitRepository.findById(id)
            .map(athleteActivityResultSplitMapper::toDto);
    }

    /**
     * Delete the athleteActivityResultSplit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AthleteActivityResultSplit : {}", id);
        athleteActivityResultSplitRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public AthleteActivityResultSplit findOrCreateAthleteActivityResultSplit(ActivityResultSplit activityResultSplit, AthleteActivityResult athleteActivityResult) {
        AthleteActivityResultSplit athleteActivityResultSplit;
        Optional<AthleteActivityResultSplit> athleteActivityResultSplitOptional =
            athleteActivityResultSplitRepository.findByActivityResultSplitIdAndAthleteActivityResultId(activityResultSplit.getId(), athleteActivityResult.getId());
        if (athleteActivityResultSplitOptional.isPresent()) {
            athleteActivityResultSplit = athleteActivityResultSplitOptional.get();
        } else {
            athleteActivityResultSplit = new AthleteActivityResultSplit();
            athleteActivityResultSplit.setAthleteActivityResult(athleteActivityResult);
            athleteActivityResultSplit.setActivityResultSplit(activityResultSplit);
        }
        return athleteActivityResultSplitRepository.save(athleteActivityResultSplit);
    }
}
