package com.sportiq.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class UserActivityResultSplitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserActivityResultSplit.class);
        UserActivityResultSplit userActivityResultSplit1 = new UserActivityResultSplit();
        userActivityResultSplit1.setId(1L);
        UserActivityResultSplit userActivityResultSplit2 = new UserActivityResultSplit();
        userActivityResultSplit2.setId(userActivityResultSplit1.getId());
        assertThat(userActivityResultSplit1).isEqualTo(userActivityResultSplit2);
        userActivityResultSplit2.setId(2L);
        assertThat(userActivityResultSplit1).isNotEqualTo(userActivityResultSplit2);
        userActivityResultSplit1.setId(null);
        assertThat(userActivityResultSplit1).isNotEqualTo(userActivityResultSplit2);
    }
}
