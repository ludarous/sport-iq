package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.MembershipRoleService;
import com.sportiq.sportiq.domain.MembershipRole;
import com.sportiq.sportiq.repository.MembershipRoleRepository;
import com.sportiq.sportiq.service.dto.MembershipRoleDTO;
import com.sportiq.sportiq.service.mapper.MembershipRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MembershipRole}.
 */
@Service
@Transactional
public class MembershipRoleServiceImpl implements MembershipRoleService {

    private final Logger log = LoggerFactory.getLogger(MembershipRoleServiceImpl.class);

    private final MembershipRoleRepository membershipRoleRepository;

    private final MembershipRoleMapper membershipRoleMapper;

    public MembershipRoleServiceImpl(MembershipRoleRepository membershipRoleRepository, MembershipRoleMapper membershipRoleMapper) {
        this.membershipRoleRepository = membershipRoleRepository;
        this.membershipRoleMapper = membershipRoleMapper;
    }

    @Override
    public MembershipRoleDTO save(MembershipRoleDTO membershipRoleDTO) {
        log.debug("Request to save MembershipRole : {}", membershipRoleDTO);
        MembershipRole membershipRole = membershipRoleMapper.toEntity(membershipRoleDTO);
        membershipRole = membershipRoleRepository.save(membershipRole);
        return membershipRoleMapper.toDto(membershipRole);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MembershipRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MembershipRoles");
        return membershipRoleRepository.findAll(pageable)
            .map(membershipRoleMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MembershipRoleDTO> findOne(Long id) {
        log.debug("Request to get MembershipRole : {}", id);
        return membershipRoleRepository.findById(id)
            .map(membershipRoleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MembershipRole : {}", id);
        membershipRoleRepository.deleteById(id);
    }
}
