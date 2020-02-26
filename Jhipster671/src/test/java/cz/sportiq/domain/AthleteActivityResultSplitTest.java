package cz.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class AthleteActivityResultSplitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteActivityResultSplit.class);
        AthleteActivityResultSplit athleteActivityResultSplit1 = new AthleteActivityResultSplit();
        athleteActivityResultSplit1.setId(1L);
        AthleteActivityResultSplit athleteActivityResultSplit2 = new AthleteActivityResultSplit();
        athleteActivityResultSplit2.setId(athleteActivityResultSplit1.getId());
        assertThat(athleteActivityResultSplit1).isEqualTo(athleteActivityResultSplit2);
        athleteActivityResultSplit2.setId(2L);
        assertThat(athleteActivityResultSplit1).isNotEqualTo(athleteActivityResultSplit2);
        athleteActivityResultSplit1.setId(null);
        assertThat(athleteActivityResultSplit1).isNotEqualTo(athleteActivityResultSplit2);
    }
}
