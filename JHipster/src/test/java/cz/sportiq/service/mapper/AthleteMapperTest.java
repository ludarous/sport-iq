package cz.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

public class AthleteMapperTest {

    private AthleteMapper athleteMapper;

    @BeforeEach
    public void setUp() {
        athleteMapper = new AthleteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = UUID.randomUUID().toString();
        assertThat(athleteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(athleteMapper.fromId(null)).isNull();
    }
}
