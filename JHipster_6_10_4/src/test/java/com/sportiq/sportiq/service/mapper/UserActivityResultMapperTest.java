package com.sportiq.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserActivityResultMapperTest {

    private UserActivityResultMapper userActivityResultMapper;

    @BeforeEach
    public void setUp() {
        userActivityResultMapper = new UserActivityResultMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userActivityResultMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userActivityResultMapper.fromId(null)).isNull();
    }
}
