package cz.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AthleteActivityResultSplitMapperTest {

    private AthleteActivityResultSplitMapper athleteActivityResultSplitMapper;

    @BeforeEach
    public void setUp() {
        athleteActivityResultSplitMapper = new AthleteActivityResultSplitMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(athleteActivityResultSplitMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(athleteActivityResultSplitMapper.fromId(null)).isNull();
    }
}
