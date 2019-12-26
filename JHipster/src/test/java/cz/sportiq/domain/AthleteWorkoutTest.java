package cz.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class AthleteWorkoutTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteWorkout.class);
        AthleteWorkout athleteWorkout1 = new AthleteWorkout();
        athleteWorkout1.setId(1L);
        AthleteWorkout athleteWorkout2 = new AthleteWorkout();
        athleteWorkout2.setId(athleteWorkout1.getId());
        assertThat(athleteWorkout1).isEqualTo(athleteWorkout2);
        athleteWorkout2.setId(2L);
        assertThat(athleteWorkout1).isNotEqualTo(athleteWorkout2);
        athleteWorkout1.setId(null);
        assertThat(athleteWorkout1).isNotEqualTo(athleteWorkout2);
    }
}
