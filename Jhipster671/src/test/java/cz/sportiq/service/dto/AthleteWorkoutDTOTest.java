package cz.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class AthleteWorkoutDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteWorkoutDTO.class);
        AthleteWorkoutDTO athleteWorkoutDTO1 = new AthleteWorkoutDTO();
        athleteWorkoutDTO1.setId(1L);
        AthleteWorkoutDTO athleteWorkoutDTO2 = new AthleteWorkoutDTO();
        assertThat(athleteWorkoutDTO1).isNotEqualTo(athleteWorkoutDTO2);
        athleteWorkoutDTO2.setId(athleteWorkoutDTO1.getId());
        assertThat(athleteWorkoutDTO1).isEqualTo(athleteWorkoutDTO2);
        athleteWorkoutDTO2.setId(2L);
        assertThat(athleteWorkoutDTO1).isNotEqualTo(athleteWorkoutDTO2);
        athleteWorkoutDTO1.setId(null);
        assertThat(athleteWorkoutDTO1).isNotEqualTo(athleteWorkoutDTO2);
    }
}
