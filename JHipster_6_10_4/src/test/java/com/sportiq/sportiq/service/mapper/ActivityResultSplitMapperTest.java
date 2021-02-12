package com.sportiq.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ActivityResultSplitMapperTest {

    private ActivityResultSplitMapper activityResultSplitMapper;

    @BeforeEach
    public void setUp() {
        activityResultSplitMapper = new ActivityResultSplitMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(activityResultSplitMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(activityResultSplitMapper.fromId(null)).isNull();
    }
}
