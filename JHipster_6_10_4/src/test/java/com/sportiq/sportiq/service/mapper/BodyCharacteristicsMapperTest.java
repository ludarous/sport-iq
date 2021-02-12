package com.sportiq.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BodyCharacteristicsMapperTest {

    private BodyCharacteristicsMapper bodyCharacteristicsMapper;

    @BeforeEach
    public void setUp() {
        bodyCharacteristicsMapper = new BodyCharacteristicsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bodyCharacteristicsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bodyCharacteristicsMapper.fromId(null)).isNull();
    }
}
