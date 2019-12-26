package cz.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AthleteEventMapperTest {

    private AthleteEventMapper athleteEventMapper;

    @BeforeEach
    public void setUp() {
        athleteEventMapper = new AthleteEventMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(athleteEventMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(athleteEventMapper.fromId(null)).isNull();
    }
}
