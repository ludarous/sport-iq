package com.sportiq.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrganisationMembershipMapperTest {

    private OrganisationMembershipMapper organisationMembershipMapper;

    @BeforeEach
    public void setUp() {
        organisationMembershipMapper = new OrganisationMembershipMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(organisationMembershipMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(organisationMembershipMapper.fromId(null)).isNull();
    }
}
