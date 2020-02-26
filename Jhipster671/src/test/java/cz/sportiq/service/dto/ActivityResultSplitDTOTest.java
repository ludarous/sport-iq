package cz.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class ActivityResultSplitDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityResultSplitDTO.class);
        ActivityResultSplitDTO activityResultSplitDTO1 = new ActivityResultSplitDTO();
        activityResultSplitDTO1.setId(1L);
        ActivityResultSplitDTO activityResultSplitDTO2 = new ActivityResultSplitDTO();
        assertThat(activityResultSplitDTO1).isNotEqualTo(activityResultSplitDTO2);
        activityResultSplitDTO2.setId(activityResultSplitDTO1.getId());
        assertThat(activityResultSplitDTO1).isEqualTo(activityResultSplitDTO2);
        activityResultSplitDTO2.setId(2L);
        assertThat(activityResultSplitDTO1).isNotEqualTo(activityResultSplitDTO2);
        activityResultSplitDTO1.setId(null);
        assertThat(activityResultSplitDTO1).isNotEqualTo(activityResultSplitDTO2);
    }
}
