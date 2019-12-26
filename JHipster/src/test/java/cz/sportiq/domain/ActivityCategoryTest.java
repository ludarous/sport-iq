package cz.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class ActivityCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityCategory.class);
        ActivityCategory activityCategory1 = new ActivityCategory();
        activityCategory1.setId(1L);
        ActivityCategory activityCategory2 = new ActivityCategory();
        activityCategory2.setId(activityCategory1.getId());
        assertThat(activityCategory1).isEqualTo(activityCategory2);
        activityCategory2.setId(2L);
        assertThat(activityCategory1).isNotEqualTo(activityCategory2);
        activityCategory1.setId(null);
        assertThat(activityCategory1).isNotEqualTo(activityCategory2);
    }
}
