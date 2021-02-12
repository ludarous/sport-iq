package com.sportiq.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class MembershipRoleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembershipRoleDTO.class);
        MembershipRoleDTO membershipRoleDTO1 = new MembershipRoleDTO();
        membershipRoleDTO1.setId(1L);
        MembershipRoleDTO membershipRoleDTO2 = new MembershipRoleDTO();
        assertThat(membershipRoleDTO1).isNotEqualTo(membershipRoleDTO2);
        membershipRoleDTO2.setId(membershipRoleDTO1.getId());
        assertThat(membershipRoleDTO1).isEqualTo(membershipRoleDTO2);
        membershipRoleDTO2.setId(2L);
        assertThat(membershipRoleDTO1).isNotEqualTo(membershipRoleDTO2);
        membershipRoleDTO1.setId(null);
        assertThat(membershipRoleDTO1).isNotEqualTo(membershipRoleDTO2);
    }
}
