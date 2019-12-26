package cz.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class AthleteActivityResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteActivityResult.class);
        AthleteActivityResult athleteActivityResult1 = new AthleteActivityResult();
        athleteActivityResult1.setId(1L);
        AthleteActivityResult athleteActivityResult2 = new AthleteActivityResult();
        athleteActivityResult2.setId(athleteActivityResult1.getId());
        assertThat(athleteActivityResult1).isEqualTo(athleteActivityResult2);
        athleteActivityResult2.setId(2L);
        assertThat(athleteActivityResult1).isNotEqualTo(athleteActivityResult2);
        athleteActivityResult1.setId(null);
        assertThat(athleteActivityResult1).isNotEqualTo(athleteActivityResult2);
    }
}
