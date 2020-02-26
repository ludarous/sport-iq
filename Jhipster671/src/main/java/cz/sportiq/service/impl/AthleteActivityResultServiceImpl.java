package cz.sportiq.service.impl;

import cz.sportiq.domain.*;
import cz.sportiq.repository.AthleteActivityResultRepository;
import cz.sportiq.service.AthleteActivityResultService;
import cz.sportiq.service.AthleteActivityResultSplitService;
import cz.sportiq.service.dto.AthleteActivityResultDTO;
import cz.sportiq.service.mapper.AthleteActivityResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing {@link AthleteActivityResult}.
 */
@Service
@Transactional
public class AthleteActivityResultServiceImpl implements AthleteActivityResultService {

    private final Logger log = LoggerFactory.getLogger(AthleteActivityResultServiceImpl.class);

    private final AthleteActivityResultRepository athleteActivityResultRepository;

    private final AthleteActivityResultMapper athleteActivityResultMapper;

    private final AthleteActivityResultSplitService athleteActivityResultSplitService;

    public AthleteActivityResultServiceImpl(AthleteActivityResultRepository athleteActivityResultRepository, AthleteActivityResultMapper athleteActivityResultMapper, AthleteActivityResultSplitService athleteActivityResultSplitService) {
        this.athleteActivityResultRepository = athleteActivityResultRepository;
        this.athleteActivityResultMapper = athleteActivityResultMapper;
        this.athleteActivityResultSplitService = athleteActivityResultSplitService;
    }

    /**
     * Save a athleteActivityResult.
     *
     * @param athleteActivityResultDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AthleteActivityResultDTO save(AthleteActivityResultDTO athleteActivityResultDTO) {
        log.debug("Request to save AthleteActivityResult : {}", athleteActivityResultDTO);
        AthleteActivityResult athleteActivityResult = athleteActivityResultMapper.toEntity(athleteActivityResultDTO);
        athleteActivityResult = athleteActivityResultRepository.save(athleteActivityResult);
        AthleteActivityResultDTO result = athleteActivityResultMapper.toDto(athleteActivityResult);
        return result;
    }

    /**
     * Get all the athleteActivityResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AthleteActivityResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AthleteActivityResults");
        return athleteActivityResultRepository.findAll(pageable)
            .map(athleteActivityResultMapper::toDto);
    }


    /**
     * Get one athleteActivityResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AthleteActivityResultDTO> findOne(Long id) {
        log.debug("Request to get AthleteActivityResult : {}", id);
        return athleteActivityResultRepository.findById(id)
            .map(athleteActivityResultMapper::toDto);
    }

    /**
     * Delete the athleteActivityResult by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AthleteActivityResult : {}", id);
        athleteActivityResultRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public AthleteActivityResult findOrCreateAthleteActivityResult(ActivityResult activityResult, AthleteActivity athleteActivity) {

        AthleteActivityResult athleteActivityResult;
        Optional<AthleteActivityResult> athleteActivityResultOptional =
            athleteActivityResultRepository.findByActivityResultIdAndAthleteActivityId(activityResult.getId(), athleteActivity.getId());
        if (athleteActivityResultOptional.isPresent()) {
            athleteActivityResult = athleteActivityResultOptional.get();
        } else {
            athleteActivityResult = new AthleteActivityResult();
            athleteActivityResult.setActivityResult(activityResult);
            athleteActivityResult.setAthleteActivity(athleteActivity);
        }

        athleteActivityResult = athleteActivityResultRepository.saveAndFlush(athleteActivityResult);

        for(ActivityResultSplit activityResultSplit : activityResult.getResultSplits()) {
            AthleteActivityResultSplit athleteActivityResultSplit =
                athleteActivityResultSplitService.findOrCreateAthleteActivityResultSplit(activityResultSplit, athleteActivityResult);
            athleteActivityResult.addAthleteActivityResultSplits(athleteActivityResultSplit);
        }
        return athleteActivityResultRepository.save(athleteActivityResult);
    }
}
