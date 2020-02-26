package cz.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class AthleteEventDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteEventDTO.class);
        AthleteEventDTO athleteEventDTO1 = new AthleteEventDTO();
        athleteEventDTO1.setId(1L);
        AthleteEventDTO athleteEventDTO2 = new AthleteEventDTO();
        assertThat(athleteEventDTO1).isNotEqualTo(athleteEventDTO2);
        athleteEventDTO2.setId(athleteEventDTO1.getId());
        assertThat(athleteEventDTO1).isEqualTo(athleteEventDTO2);
        athleteEventDTO2.setId(2L);
        assertThat(athleteEventDTO1).isNotEqualTo(athleteEventDTO2);
        athleteEventDTO1.setId(null);
        assertThat(athleteEventDTO1).isNotEqualTo(athleteEventDTO2);
    }
}
