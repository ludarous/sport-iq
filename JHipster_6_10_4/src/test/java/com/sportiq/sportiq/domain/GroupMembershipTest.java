package com.sportiq.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class GroupMembershipTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupMembership.class);
        GroupMembership groupMembership1 = new GroupMembership();
        groupMembership1.setId(1L);
        GroupMembership groupMembership2 = new GroupMembership();
        groupMembership2.setId(groupMembership1.getId());
        assertThat(groupMembership1).isEqualTo(groupMembership2);
        groupMembership2.setId(2L);
        assertThat(groupMembership1).isNotEqualTo(groupMembership2);
        groupMembership1.setId(null);
        assertThat(groupMembership1).isNotEqualTo(groupMembership2);
    }
}
