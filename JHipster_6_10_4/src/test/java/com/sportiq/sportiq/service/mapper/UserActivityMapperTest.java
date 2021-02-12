package com.sportiq.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserActivityMapperTest {

    private UserActivityMapper userActivityMapper;

    @BeforeEach
    public void setUp() {
        userActivityMapper = new UserActivityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userActivityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userActivityMapper.fromId(null)).isNull();
    }
}
