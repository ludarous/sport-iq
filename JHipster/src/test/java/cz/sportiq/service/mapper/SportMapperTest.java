package cz.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SportMapperTest {

    private SportMapper sportMapper;

    @BeforeEach
    public void setUp() {
        sportMapper = new SportMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(sportMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sportMapper.fromId(null)).isNull();
    }
}
