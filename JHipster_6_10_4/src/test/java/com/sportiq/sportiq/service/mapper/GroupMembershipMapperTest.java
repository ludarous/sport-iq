package com.sportiq.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupMembershipMapperTest {

    private GroupMembershipMapper groupMembershipMapper;

    @BeforeEach
    public void setUp() {
        groupMembershipMapper = new GroupMembershipMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(groupMembershipMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(groupMembershipMapper.fromId(null)).isNull();
    }
}
