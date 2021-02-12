package com.sportiq.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class UserActivityResultDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserActivityResultDTO.class);
        UserActivityResultDTO userActivityResultDTO1 = new UserActivityResultDTO();
        userActivityResultDTO1.setId(1L);
        UserActivityResultDTO userActivityResultDTO2 = new UserActivityResultDTO();
        assertThat(userActivityResultDTO1).isNotEqualTo(userActivityResultDTO2);
        userActivityResultDTO2.setId(userActivityResultDTO1.getId());
        assertThat(userActivityResultDTO1).isEqualTo(userActivityResultDTO2);
        userActivityResultDTO2.setId(2L);
        assertThat(userActivityResultDTO1).isNotEqualTo(userActivityResultDTO2);
        userActivityResultDTO1.setId(null);
        assertThat(userActivityResultDTO1).isNotEqualTo(userActivityResultDTO2);
    }
}
