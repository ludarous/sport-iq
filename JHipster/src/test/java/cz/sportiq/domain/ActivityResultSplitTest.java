package cz.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class ActivityResultSplitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityResultSplit.class);
        ActivityResultSplit activityResultSplit1 = new ActivityResultSplit();
        activityResultSplit1.setId(1L);
        ActivityResultSplit activityResultSplit2 = new ActivityResultSplit();
        activityResultSplit2.setId(activityResultSplit1.getId());
        assertThat(activityResultSplit1).isEqualTo(activityResultSplit2);
        activityResultSplit2.setId(2L);
        assertThat(activityResultSplit1).isNotEqualTo(activityResultSplit2);
        activityResultSplit1.setId(null);
        assertThat(activityResultSplit1).isNotEqualTo(activityResultSplit2);
    }
}
