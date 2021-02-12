package com.sportiq.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class BodyCharacteristicsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BodyCharacteristics.class);
        BodyCharacteristics bodyCharacteristics1 = new BodyCharacteristics();
        bodyCharacteristics1.setId(1L);
        BodyCharacteristics bodyCharacteristics2 = new BodyCharacteristics();
        bodyCharacteristics2.setId(bodyCharacteristics1.getId());
        assertThat(bodyCharacteristics1).isEqualTo(bodyCharacteristics2);
        bodyCharacteristics2.setId(2L);
        assertThat(bodyCharacteristics1).isNotEqualTo(bodyCharacteristics2);
        bodyCharacteristics1.setId(null);
        assertThat(bodyCharacteristics1).isNotEqualTo(bodyCharacteristics2);
    }
}
