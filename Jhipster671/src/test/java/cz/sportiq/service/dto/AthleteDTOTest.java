package cz.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class AthleteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteDTO.class);
        AthleteDTO athleteDTO1 = new AthleteDTO();
        athleteDTO1.setId(1L);
        AthleteDTO athleteDTO2 = new AthleteDTO();
        assertThat(athleteDTO1).isNotEqualTo(athleteDTO2);
        athleteDTO2.setId(athleteDTO1.getId());
        assertThat(athleteDTO1).isEqualTo(athleteDTO2);
        athleteDTO2.setId(2L);
        assertThat(athleteDTO1).isNotEqualTo(athleteDTO2);
        athleteDTO1.setId(null);
        assertThat(athleteDTO1).isNotEqualTo(athleteDTO2);
    }
}
