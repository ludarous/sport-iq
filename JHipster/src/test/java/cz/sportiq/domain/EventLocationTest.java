package cz.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class EventLocationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventLocation.class);
        EventLocation eventLocation1 = new EventLocation();
        eventLocation1.setId(1L);
        EventLocation eventLocation2 = new EventLocation();
        eventLocation2.setId(eventLocation1.getId());
        assertThat(eventLocation1).isEqualTo(eventLocation2);
        eventLocation2.setId(2L);
        assertThat(eventLocation1).isNotEqualTo(eventLocation2);
        eventLocation1.setId(null);
        assertThat(eventLocation1).isNotEqualTo(eventLocation2);
    }
}
