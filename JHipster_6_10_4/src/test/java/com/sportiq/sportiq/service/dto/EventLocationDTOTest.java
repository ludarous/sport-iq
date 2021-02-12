package com.sportiq.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class EventLocationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventLocationDTO.class);
        EventLocationDTO eventLocationDTO1 = new EventLocationDTO();
        eventLocationDTO1.setId(1L);
        EventLocationDTO eventLocationDTO2 = new EventLocationDTO();
        assertThat(eventLocationDTO1).isNotEqualTo(eventLocationDTO2);
        eventLocationDTO2.setId(eventLocationDTO1.getId());
        assertThat(eventLocationDTO1).isEqualTo(eventLocationDTO2);
        eventLocationDTO2.setId(2L);
        assertThat(eventLocationDTO1).isNotEqualTo(eventLocationDTO2);
        eventLocationDTO1.setId(null);
        assertThat(eventLocationDTO1).isNotEqualTo(eventLocationDTO2);
    }
}
