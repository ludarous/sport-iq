package com.sportiq.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class LegalRepresentativeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LegalRepresentative.class);
        LegalRepresentative legalRepresentative1 = new LegalRepresentative();
        legalRepresentative1.setId(1L);
        LegalRepresentative legalRepresentative2 = new LegalRepresentative();
        legalRepresentative2.setId(legalRepresentative1.getId());
        assertThat(legalRepresentative1).isEqualTo(legalRepresentative2);
        legalRepresentative2.setId(2L);
        assertThat(legalRepresentative1).isNotEqualTo(legalRepresentative2);
        legalRepresentative1.setId(null);
        assertThat(legalRepresentative1).isNotEqualTo(legalRepresentative2);
    }
}
