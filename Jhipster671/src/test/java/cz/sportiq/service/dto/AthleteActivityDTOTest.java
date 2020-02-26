package cz.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class AthleteActivityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteActivityDTO.class);
        AthleteActivityDTO athleteActivityDTO1 = new AthleteActivityDTO();
        athleteActivityDTO1.setId(1L);
        AthleteActivityDTO athleteActivityDTO2 = new AthleteActivityDTO();
        assertThat(athleteActivityDTO1).isNotEqualTo(athleteActivityDTO2);
        athleteActivityDTO2.setId(athleteActivityDTO1.getId());
        assertThat(athleteActivityDTO1).isEqualTo(athleteActivityDTO2);
        athleteActivityDTO2.setId(2L);
        assertThat(athleteActivityDTO1).isNotEqualTo(athleteActivityDTO2);
        athleteActivityDTO1.setId(null);
        assertThat(athleteActivityDTO1).isNotEqualTo(athleteActivityDTO2);
    }
}
