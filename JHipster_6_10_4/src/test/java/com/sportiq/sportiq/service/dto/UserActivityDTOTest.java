package com.sportiq.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class UserActivityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserActivityDTO.class);
        UserActivityDTO userActivityDTO1 = new UserActivityDTO();
        userActivityDTO1.setId(1L);
        UserActivityDTO userActivityDTO2 = new UserActivityDTO();
        assertThat(userActivityDTO1).isNotEqualTo(userActivityDTO2);
        userActivityDTO2.setId(userActivityDTO1.getId());
        assertThat(userActivityDTO1).isEqualTo(userActivityDTO2);
        userActivityDTO2.setId(2L);
        assertThat(userActivityDTO1).isNotEqualTo(userActivityDTO2);
        userActivityDTO1.setId(null);
        assertThat(userActivityDTO1).isNotEqualTo(userActivityDTO2);
    }
}
