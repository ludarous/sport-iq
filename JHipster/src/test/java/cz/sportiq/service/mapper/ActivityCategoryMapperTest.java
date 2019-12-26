package cz.sportiq.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ActivityCategoryMapperTest {

    private ActivityCategoryMapper activityCategoryMapper;

    @BeforeEach
    public void setUp() {
        activityCategoryMapper = new ActivityCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(activityCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(activityCategoryMapper.fromId(null)).isNull();
    }
}
