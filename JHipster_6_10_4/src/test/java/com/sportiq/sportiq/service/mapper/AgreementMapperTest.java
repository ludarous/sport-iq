package com.sportiq.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AgreementMapperTest {

    private AgreementMapper agreementMapper;

    @BeforeEach
    public void setUp() {
        agreementMapper = new AgreementMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(agreementMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(agreementMapper.fromId(null)).isNull();
    }
}
