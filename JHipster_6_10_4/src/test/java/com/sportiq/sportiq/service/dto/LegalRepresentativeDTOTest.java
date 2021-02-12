package com.sportiq.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class LegalRepresentativeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LegalRepresentativeDTO.class);
        LegalRepresentativeDTO legalRepresentativeDTO1 = new LegalRepresentativeDTO();
        legalRepresentativeDTO1.setId(1L);
        LegalRepresentativeDTO legalRepresentativeDTO2 = new LegalRepresentativeDTO();
        assertThat(legalRepresentativeDTO1).isNotEqualTo(legalRepresentativeDTO2);
        legalRepresentativeDTO2.setId(legalRepresentativeDTO1.getId());
        assertThat(legalRepresentativeDTO1).isEqualTo(legalRepresentativeDTO2);
        legalRepresentativeDTO2.setId(2L);
        assertThat(legalRepresentativeDTO1).isNotEqualTo(legalRepresentativeDTO2);
        legalRepresentativeDTO1.setId(null);
        assertThat(legalRepresentativeDTO1).isNotEqualTo(legalRepresentativeDTO2);
    }
}
