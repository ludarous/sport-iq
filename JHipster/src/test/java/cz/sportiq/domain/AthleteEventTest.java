package cz.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class AthleteEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AthleteEvent.class);
        AthleteEvent athleteEvent1 = new AthleteEvent();
        athleteEvent1.setId(1L);
        AthleteEvent athleteEvent2 = new AthleteEvent();
        athleteEvent2.setId(athleteEvent1.getId());
        assertThat(athleteEvent1).isEqualTo(athleteEvent2);
        athleteEvent2.setId(2L);
        assertThat(athleteEvent1).isNotEqualTo(athleteEvent2);
        athleteEvent1.setId(null);
        assertThat(athleteEvent1).isNotEqualTo(athleteEvent2);
    }
}
