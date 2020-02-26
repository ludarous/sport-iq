package cz.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AthleteActivityMapperTest {

    private AthleteActivityMapper athleteActivityMapper;

    @BeforeEach
    public void setUp() {
        athleteActivityMapper = new AthleteActivityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(athleteActivityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(athleteActivityMapper.fromId(null)).isNull();
    }
}
