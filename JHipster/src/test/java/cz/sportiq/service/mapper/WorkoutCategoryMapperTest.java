package cz.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class WorkoutCategoryMapperTest {

    private WorkoutCategoryMapper workoutCategoryMapper;

    @BeforeEach
    public void setUp() {
        workoutCategoryMapper = new WorkoutCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(workoutCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(workoutCategoryMapper.fromId(null)).isNull();
    }
}
