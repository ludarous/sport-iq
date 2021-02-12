package com.sportiq.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class GroupMembershipDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupMembershipDTO.class);
        GroupMembershipDTO groupMembershipDTO1 = new GroupMembershipDTO();
        groupMembershipDTO1.setId(1L);
        GroupMembershipDTO groupMembershipDTO2 = new GroupMembershipDTO();
        assertThat(groupMembershipDTO1).isNotEqualTo(groupMembershipDTO2);
        groupMembershipDTO2.setId(groupMembershipDTO1.getId());
        assertThat(groupMembershipDTO1).isEqualTo(groupMembershipDTO2);
        groupMembershipDTO2.setId(2L);
        assertThat(groupMembershipDTO1).isNotEqualTo(groupMembershipDTO2);
        groupMembershipDTO1.setId(null);
        assertThat(groupMembershipDTO1).isNotEqualTo(groupMembershipDTO2);
    }
}
