package com.sportiq.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserPropertiesMapperTest {

    private UserPropertiesMapper userPropertiesMapper;

    @BeforeEach
    public void setUp() {
        userPropertiesMapper = new UserPropertiesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userPropertiesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userPropertiesMapper.fromId(null)).isNull();
    }
}
