package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.UserPropertiesService;
import com.sportiq.sportiq.domain.UserProperties;
import com.sportiq.sportiq.repository.UserPropertiesRepository;
import com.sportiq.sportiq.service.dto.UserPropertiesDTO;
import com.sportiq.sportiq.service.mapper.UserPropertiesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserProperties}.
 */
@Service
@Transactional
public class UserPropertiesServiceImpl implements UserPropertiesService {

    private final Logger log = LoggerFactory.getLogger(UserPropertiesServiceImpl.class);

    private final UserPropertiesRepository userPropertiesRepository;

    private final UserPropertiesMapper userPropertiesMapper;

    public UserPropertiesServiceImpl(UserPropertiesRepository userPropertiesRepository, UserPropertiesMapper userPropertiesMapper) {
        this.userPropertiesRepository = userPropertiesRepository;
        this.userPropertiesMapper = userPropertiesMapper;
    }

    @Override
    public UserPropertiesDTO save(UserPropertiesDTO userPropertiesDTO) {
        log.debug("Request to save UserProperties : {}", userPropertiesDTO);
        UserProperties userProperties = userPropertiesMapper.toEntity(userPropertiesDTO);
        userProperties = userPropertiesRepository.save(userProperties);
        return userPropertiesMapper.toDto(userProperties);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserPropertiesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserProperties");
        return userPropertiesRepository.findAll(pageable)
            .map(userPropertiesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserPropertiesDTO> findOne(Long id) {
        log.debug("Request to get UserProperties : {}", id);
        return userPropertiesRepository.findById(id)
            .map(userPropertiesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserProperties : {}", id);
        userPropertiesRepository.deleteById(id);
    }
}
