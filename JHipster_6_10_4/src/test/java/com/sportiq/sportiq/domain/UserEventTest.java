package com.sportiq.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class UserEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserEvent.class);
        UserEvent userEvent1 = new UserEvent();
        userEvent1.setId(1L);
        UserEvent userEvent2 = new UserEvent();
        userEvent2.setId(userEvent1.getId());
        assertThat(userEvent1).isEqualTo(userEvent2);
        userEvent2.setId(2L);
        assertThat(userEvent1).isNotEqualTo(userEvent2);
        userEvent1.setId(null);
        assertThat(userEvent1).isNotEqualTo(userEvent2);
    }
}
