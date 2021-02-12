package com.sportiq.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserActivityResultSplitMapperTest {

    private UserActivityResultSplitMapper userActivityResultSplitMapper;

    @BeforeEach
    public void setUp() {
        userActivityResultSplitMapper = new UserActivityResultSplitMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userActivityResultSplitMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userActivityResultSplitMapper.fromId(null)).isNull();
    }
}
