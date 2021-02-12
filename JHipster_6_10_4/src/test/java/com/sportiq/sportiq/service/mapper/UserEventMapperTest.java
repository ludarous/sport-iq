package com.sportiq.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserEventMapperTest {

    private UserEventMapper userEventMapper;

    @BeforeEach
    public void setUp() {
        userEventMapper = new UserEventMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userEventMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userEventMapper.fromId(null)).isNull();
    }
}
