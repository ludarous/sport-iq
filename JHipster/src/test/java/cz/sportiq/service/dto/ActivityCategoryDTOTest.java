package cz.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class ActivityCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityCategoryDTO.class);
        ActivityCategoryDTO activityCategoryDTO1 = new ActivityCategoryDTO();
        activityCategoryDTO1.setId(1L);
        ActivityCategoryDTO activityCategoryDTO2 = new ActivityCategoryDTO();
        assertThat(activityCategoryDTO1).isNotEqualTo(activityCategoryDTO2);
        activityCategoryDTO2.setId(activityCategoryDTO1.getId());
        assertThat(activityCategoryDTO1).isEqualTo(activityCategoryDTO2);
        activityCategoryDTO2.setId(2L);
        assertThat(activityCategoryDTO1).isNotEqualTo(activityCategoryDTO2);
        activityCategoryDTO1.setId(null);
        assertThat(activityCategoryDTO1).isNotEqualTo(activityCategoryDTO2);
    }
}
