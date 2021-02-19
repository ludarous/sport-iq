package com.sportiq.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class OrganisationMembershipTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganisationMembership.class);
        OrganisationMembership organisationMembership1 = new OrganisationMembership();
        organisationMembership1.setId(1L);
        OrganisationMembership organisationMembership2 = new OrganisationMembership();
        organisationMembership2.setId(organisationMembership1.getId());
        assertThat(organisationMembership1).isEqualTo(organisationMembership2);
        organisationMembership2.setId(2L);
        assertThat(organisationMembership1).isNotEqualTo(organisationMembership2);
        organisationMembership1.setId(null);
        assertThat(organisationMembership1).isNotEqualTo(organisationMembership2);
    }
}
