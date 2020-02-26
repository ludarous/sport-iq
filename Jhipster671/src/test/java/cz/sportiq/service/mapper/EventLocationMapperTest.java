package cz.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EventLocationMapperTest {

    private EventLocationMapper eventLocationMapper;

    @BeforeEach
    public void setUp() {
        eventLocationMapper = new EventLocationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(eventLocationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eventLocationMapper.fromId(null)).isNull();
    }
}
