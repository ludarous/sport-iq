package com.sportiq.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ActivityResultMapperTest {

    private ActivityResultMapper activityResultMapper;

    @BeforeEach
    public void setUp() {
        activityResultMapper = new ActivityResultMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(activityResultMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(activityResultMapper.fromId(null)).isNull();
    }
}
