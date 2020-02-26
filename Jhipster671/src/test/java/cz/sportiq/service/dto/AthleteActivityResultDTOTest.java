package cz.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class AthleteActivityResultDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteActivityResultDTO.class);
        AthleteActivityResultDTO athleteActivityResultDTO1 = new AthleteActivityResultDTO();
        athleteActivityResultDTO1.setId(1L);
        AthleteActivityResultDTO athleteActivityResultDTO2 = new AthleteActivityResultDTO();
        assertThat(athleteActivityResultDTO1).isNotEqualTo(athleteActivityResultDTO2);
        athleteActivityResultDTO2.setId(athleteActivityResultDTO1.getId());
        assertThat(athleteActivityResultDTO1).isEqualTo(athleteActivityResultDTO2);
        athleteActivityResultDTO2.setId(2L);
        assertThat(athleteActivityResultDTO1).isNotEqualTo(athleteActivityResultDTO2);
        athleteActivityResultDTO1.setId(null);
        assertThat(athleteActivityResultDTO1).isNotEqualTo(athleteActivityResultDTO2);
    }
}
