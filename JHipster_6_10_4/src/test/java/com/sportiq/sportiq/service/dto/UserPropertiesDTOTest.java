package com.sportiq.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sportiq.sportiq.web.rest.TestUtil;

public class UserPropertiesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPropertiesDTO.class);
        UserPropertiesDTO userPropertiesDTO1 = new UserPropertiesDTO();
        userPropertiesDTO1.setId(1L);
        UserPropertiesDTO userPropertiesDTO2 = new UserPropertiesDTO();
        assertThat(userPropertiesDTO1).isNotEqualTo(userPropertiesDTO2);
        userPropertiesDTO2.setId(userPropertiesDTO1.getId());
        assertThat(userPropertiesDTO1).isEqualTo(userPropertiesDTO2);
        userPropertiesDTO2.setId(2L);
        assertThat(userPropertiesDTO1).isNotEqualTo(userPropertiesDTO2);
        userPropertiesDTO1.setId(null);
        assertThat(userPropertiesDTO1).isNotEqualTo(userPropertiesDTO2);
    }
}
