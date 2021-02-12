package com.sportiq.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LegalRepresentativeMapperTest {

    private LegalRepresentativeMapper legalRepresentativeMapper;

    @BeforeEach
    public void setUp() {
        legalRepresentativeMapper = new LegalRepresentativeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(legalRepresentativeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(legalRepresentativeMapper.fromId(null)).isNull();
    }
}
