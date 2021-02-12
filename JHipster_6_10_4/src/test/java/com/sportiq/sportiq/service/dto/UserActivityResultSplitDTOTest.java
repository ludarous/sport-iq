package com.sportiq.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class UserActivityResultSplitDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserActivityResultSplitDTO.class);
        UserActivityResultSplitDTO userActivityResultSplitDTO1 = new UserActivityResultSplitDTO();
        userActivityResultSplitDTO1.setId(1L);
        UserActivityResultSplitDTO userActivityResultSplitDTO2 = new UserActivityResultSplitDTO();
        assertThat(userActivityResultSplitDTO1).isNotEqualTo(userActivityResultSplitDTO2);
        userActivityResultSplitDTO2.setId(userActivityResultSplitDTO1.getId());
        assertThat(userActivityResultSplitDTO1).isEqualTo(userActivityResultSplitDTO2);
        userActivityResultSplitDTO2.setId(2L);
        assertThat(userActivityResultSplitDTO1).isNotEqualTo(userActivityResultSplitDTO2);
        userActivityResultSplitDTO1.setId(null);
        assertThat(userActivityResultSplitDTO1).isNotEqualTo(userActivityResultSplitDTO2);
    }
}
