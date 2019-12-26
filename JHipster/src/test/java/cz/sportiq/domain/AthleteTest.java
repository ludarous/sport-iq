package cz.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class AthleteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Athlete.class);
        Athlete athlete1 = new Athlete();
        athlete1.setId(1L);
        Athlete athlete2 = new Athlete();
        athlete2.setId(athlete1.getId());
        assertThat(athlete1).isEqualTo(athlete2);
        athlete2.setId(2L);
        assertThat(athlete1).isNotEqualTo(athlete2);
        athlete1.setId(null);
        assertThat(athlete1).isNotEqualTo(athlete2);
    }
}
