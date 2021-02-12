package com.sportiq.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class BodyCharacteristicsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BodyCharacteristicsDTO.class);
        BodyCharacteristicsDTO bodyCharacteristicsDTO1 = new BodyCharacteristicsDTO();
        bodyCharacteristicsDTO1.setId(1L);
        BodyCharacteristicsDTO bodyCharacteristicsDTO2 = new BodyCharacteristicsDTO();
        assertThat(bodyCharacteristicsDTO1).isNotEqualTo(bodyCharacteristicsDTO2);
        bodyCharacteristicsDTO2.setId(bodyCharacteristicsDTO1.getId());
        assertThat(bodyCharacteristicsDTO1).isEqualTo(bodyCharacteristicsDTO2);
        bodyCharacteristicsDTO2.setId(2L);
        assertThat(bodyCharacteristicsDTO1).isNotEqualTo(bodyCharacteristicsDTO2);
        bodyCharacteristicsDTO1.setId(null);
        assertThat(bodyCharacteristicsDTO1).isNotEqualTo(bodyCharacteristicsDTO2);
    }
}
