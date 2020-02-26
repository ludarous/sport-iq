package cz.sportiq.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class WorkoutCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkoutCategory.class);
        WorkoutCategory workoutCategory1 = new WorkoutCategory();
        workoutCategory1.setId(1L);
        WorkoutCategory workoutCategory2 = new WorkoutCategory();
        workoutCategory2.setId(workoutCategory1.getId());
        assertThat(workoutCategory1).isEqualTo(workoutCategory2);
        workoutCategory2.setId(2L);
        assertThat(workoutCategory1).isNotEqualTo(workoutCategory2);
        workoutCategory1.setId(null);
        assertThat(workoutCategory1).isNotEqualTo(workoutCategory2);
    }
}
