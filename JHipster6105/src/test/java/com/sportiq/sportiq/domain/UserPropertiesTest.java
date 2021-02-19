package com.sportiq.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class UserPropertiesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserProperties.class);
        UserProperties userProperties1 = new UserProperties();
        userProperties1.setId(1L);
        UserProperties userProperties2 = new UserProperties();
        userProperties2.setId(userProperties1.getId());
        assertThat(userProperties1).isEqualTo(userProperties2);
        userProperties2.setId(2L);
        assertThat(userProperties1).isNotEqualTo(userProperties2);
        userProperties1.setId(null);
        assertThat(userProperties1).isNotEqualTo(userProperties2);
    }
}
