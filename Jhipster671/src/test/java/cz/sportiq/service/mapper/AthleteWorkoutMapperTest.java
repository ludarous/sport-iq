package cz.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AthleteWorkoutMapperTest {

    private AthleteWorkoutMapper athleteWorkoutMapper;

    @BeforeEach
    public void setUp() {
        athleteWorkoutMapper = new AthleteWorkoutMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(athleteWorkoutMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(athleteWorkoutMapper.fromId(null)).isNull();
    }
}
