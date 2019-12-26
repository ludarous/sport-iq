package cz.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class ActivityResultDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityResultDTO.class);
        ActivityResultDTO activityResultDTO1 = new ActivityResultDTO();
        activityResultDTO1.setId(1L);
        ActivityResultDTO activityResultDTO2 = new ActivityResultDTO();
        assertThat(activityResultDTO1).isNotEqualTo(activityResultDTO2);
        activityResultDTO2.setId(activityResultDTO1.getId());
        assertThat(activityResultDTO1).isEqualTo(activityResultDTO2);
        activityResultDTO2.setId(2L);
        assertThat(activityResultDTO1).isNotEqualTo(activityResultDTO2);
        activityResultDTO1.setId(null);
        assertThat(activityResultDTO1).isNotEqualTo(activityResultDTO2);
    }
}
