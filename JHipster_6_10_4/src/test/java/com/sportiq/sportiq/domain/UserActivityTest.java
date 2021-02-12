package com.sportiq.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class UserActivityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserActivity.class);
        UserActivity userActivity1 = new UserActivity();
        userActivity1.setId(1L);
        UserActivity userActivity2 = new UserActivity();
        userActivity2.setId(userActivity1.getId());
        assertThat(userActivity1).isEqualTo(userActivity2);
        userActivity2.setId(2L);
        assertThat(userActivity1).isNotEqualTo(userActivity2);
        userActivity1.setId(null);
        assertThat(userActivity1).isNotEqualTo(userActivity2);
    }
}
