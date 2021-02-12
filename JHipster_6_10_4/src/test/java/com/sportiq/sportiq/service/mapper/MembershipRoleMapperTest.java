package com.sportiq.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MembershipRoleMapperTest {

    private MembershipRoleMapper membershipRoleMapper;

    @BeforeEach
    public void setUp() {
        membershipRoleMapper = new MembershipRoleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(membershipRoleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(membershipRoleMapper.fromId(null)).isNull();
    }
}
