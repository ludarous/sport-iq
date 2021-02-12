package com.sportiq.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class OrganisationMembershipDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganisationMembershipDTO.class);
        OrganisationMembershipDTO organisationMembershipDTO1 = new OrganisationMembershipDTO();
        organisationMembershipDTO1.setId(1L);
        OrganisationMembershipDTO organisationMembershipDTO2 = new OrganisationMembershipDTO();
        assertThat(organisationMembershipDTO1).isNotEqualTo(organisationMembershipDTO2);
        organisationMembershipDTO2.setId(organisationMembershipDTO1.getId());
        assertThat(organisationMembershipDTO1).isEqualTo(organisationMembershipDTO2);
        organisationMembershipDTO2.setId(2L);
        assertThat(organisationMembershipDTO1).isNotEqualTo(organisationMembershipDTO2);
        organisationMembershipDTO1.setId(null);
        assertThat(organisationMembershipDTO1).isNotEqualTo(organisationMembershipDTO2);
    }
}
