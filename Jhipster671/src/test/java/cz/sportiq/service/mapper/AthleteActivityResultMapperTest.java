package cz.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AthleteActivityResultMapperTest {

    private AthleteActivityResultMapper athleteActivityResultMapper;

    @BeforeEach
    public void setUp() {
        athleteActivityResultMapper = new AthleteActivityResultMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(athleteActivityResultMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(athleteActivityResultMapper.fromId(null)).isNull();
    }
}
