package com.sportiq.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class UserActivityResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserActivityResult.class);
        UserActivityResult userActivityResult1 = new UserActivityResult();
        userActivityResult1.setId(1L);
        UserActivityResult userActivityResult2 = new UserActivityResult();
        userActivityResult2.setId(userActivityResult1.getId());
        assertThat(userActivityResult1).isEqualTo(userActivityResult2);
        userActivityResult2.setId(2L);
        assertThat(userActivityResult1).isNotEqualTo(userActivityResult2);
        userActivityResult1.setId(null);
        assertThat(userActivityResult1).isNotEqualTo(userActivityResult2);
    }
}
