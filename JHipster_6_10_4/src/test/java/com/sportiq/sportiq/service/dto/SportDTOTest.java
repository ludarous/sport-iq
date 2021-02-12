package com.sportiq.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class SportDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SportDTO.class);
        SportDTO sportDTO1 = new SportDTO();
        sportDTO1.setId(1L);
        SportDTO sportDTO2 = new SportDTO();
        assertThat(sportDTO1).isNotEqualTo(sportDTO2);
        sportDTO2.setId(sportDTO1.getId());
        assertThat(sportDTO1).isEqualTo(sportDTO2);
        sportDTO2.setId(2L);
        assertThat(sportDTO1).isNotEqualTo(sportDTO2);
        sportDTO1.setId(null);
        assertThat(sportDTO1).isNotEqualTo(sportDTO2);
    }
}
