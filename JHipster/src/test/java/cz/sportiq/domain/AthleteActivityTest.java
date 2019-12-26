package cz.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class AthleteActivityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteActivity.class);
        AthleteActivity athleteActivity1 = new AthleteActivity();
        athleteActivity1.setId(1L);
        AthleteActivity athleteActivity2 = new AthleteActivity();
        athleteActivity2.setId(athleteActivity1.getId());
        assertThat(athleteActivity1).isEqualTo(athleteActivity2);
        athleteActivity2.setId(2L);
        assertThat(athleteActivity1).isNotEqualTo(athleteActivity2);
        athleteActivity1.setId(null);
        assertThat(athleteActivity1).isNotEqualTo(athleteActivity2);
    }
}
