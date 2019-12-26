package cz.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class ActivityResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityResult.class);
        ActivityResult activityResult1 = new ActivityResult();
        activityResult1.setId(1L);
        ActivityResult activityResult2 = new ActivityResult();
        activityResult2.setId(activityResult1.getId());
        assertThat(activityResult1).isEqualTo(activityResult2);
        activityResult2.setId(2L);
        assertThat(activityResult1).isNotEqualTo(activityResult2);
        activityResult1.setId(null);
        assertThat(activityResult1).isNotEqualTo(activityResult2);
    }
}
