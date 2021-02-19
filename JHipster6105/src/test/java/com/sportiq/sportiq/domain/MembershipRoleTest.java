package com.sportiq.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class MembershipRoleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembershipRole.class);
        MembershipRole membershipRole1 = new MembershipRole();
        membershipRole1.setId(1L);
        MembershipRole membershipRole2 = new MembershipRole();
        membershipRole2.setId(membershipRole1.getId());
        assertThat(membershipRole1).isEqualTo(membershipRole2);
        membershipRole2.setId(2L);
        assertThat(membershipRole1).isNotEqualTo(membershipRole2);
        membershipRole1.setId(null);
        assertThat(membershipRole1).isNotEqualTo(membershipRole2);
    }
}
