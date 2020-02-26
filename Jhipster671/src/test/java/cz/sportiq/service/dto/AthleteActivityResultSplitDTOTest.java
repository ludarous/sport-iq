package cz.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class AthleteActivityResultSplitDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteActivityResultSplitDTO.class);
        AthleteActivityResultSplitDTO athleteActivityResultSplitDTO1 = new AthleteActivityResultSplitDTO();
        athleteActivityResultSplitDTO1.setId(1L);
        AthleteActivityResultSplitDTO athleteActivityResultSplitDTO2 = new AthleteActivityResultSplitDTO();
        assertThat(athleteActivityResultSplitDTO1).isNotEqualTo(athleteActivityResultSplitDTO2);
        athleteActivityResultSplitDTO2.setId(athleteActivityResultSplitDTO1.getId());
        assertThat(athleteActivityResultSplitDTO1).isEqualTo(athleteActivityResultSplitDTO2);
        athleteActivityResultSplitDTO2.setId(2L);
        assertThat(athleteActivityResultSplitDTO1).isNotEqualTo(athleteActivityResultSplitDTO2);
        athleteActivityResultSplitDTO1.setId(null);
        assertThat(athleteActivityResultSplitDTO1).isNotEqualTo(athleteActivityResultSplitDTO2);
    }
}
