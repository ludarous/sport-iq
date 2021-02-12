package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.GroupService;
import com.sportiq.sportiq.domain.Group;
import com.sportiq.sportiq.repository.GroupRepository;
import com.sportiq.sportiq.service.dto.GroupDTO;
import com.sportiq.sportiq.service.mapper.GroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Group}.
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final GroupRepository groupRepository;

    private final GroupMapper groupMapper;

    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @Override
    public GroupDTO save(GroupDTO groupDTO) {
        log.debug("Request to save Group : {}", groupDTO);
        Group group = groupMapper.toEntity(groupDTO);
        group = groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Groups");
        return groupRepository.findAll(pageable)
            .map(groupMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GroupDTO> findOne(Long id) {
        log.debug("Request to get Group : {}", id);
        return groupRepository.findById(id)
            .map(groupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Group : {}", id);
        groupRepository.deleteById(id);
    }
}
