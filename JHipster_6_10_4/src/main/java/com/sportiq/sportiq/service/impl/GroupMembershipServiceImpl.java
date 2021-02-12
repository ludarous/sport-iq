package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.GroupMembershipService;
import com.sportiq.sportiq.domain.GroupMembership;
import com.sportiq.sportiq.repository.GroupMembershipRepository;
import com.sportiq.sportiq.service.dto.GroupMembershipDTO;
import com.sportiq.sportiq.service.mapper.GroupMembershipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupMembership}.
 */
@Service
@Transactional
public class GroupMembershipServiceImpl implements GroupMembershipService {

    private final Logger log = LoggerFactory.getLogger(GroupMembershipServiceImpl.class);

    private final GroupMembershipRepository groupMembershipRepository;

    private final GroupMembershipMapper groupMembershipMapper;

    public GroupMembershipServiceImpl(GroupMembershipRepository groupMembershipRepository, GroupMembershipMapper groupMembershipMapper) {
        this.groupMembershipRepository = groupMembershipRepository;
        this.groupMembershipMapper = groupMembershipMapper;
    }

    @Override
    public GroupMembershipDTO save(GroupMembershipDTO groupMembershipDTO) {
        log.debug("Request to save GroupMembership : {}", groupMembershipDTO);
        GroupMembership groupMembership = groupMembershipMapper.toEntity(groupMembershipDTO);
        groupMembership = groupMembershipRepository.save(groupMembership);
        return groupMembershipMapper.toDto(groupMembership);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupMembershipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupMemberships");
        return groupMembershipRepository.findAll(pageable)
            .map(groupMembershipMapper::toDto);
    }


    public Page<GroupMembershipDTO> findAllWithEagerRelationships(Pageable pageable) {
        return groupMembershipRepository.findAllWithEagerRelationships(pageable).map(groupMembershipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GroupMembershipDTO> findOne(Long id) {
        log.debug("Request to get GroupMembership : {}", id);
        return groupMembershipRepository.findOneWithEagerRelationships(id)
            .map(groupMembershipMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupMembership : {}", id);
        groupMembershipRepository.deleteById(id);
    }
}
