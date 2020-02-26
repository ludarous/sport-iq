package cz.sportiq.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cz.sportiq.web.rest.TestUtil;

public class WorkoutCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkoutCategoryDTO.class);
        WorkoutCategoryDTO workoutCategoryDTO1 = new WorkoutCategoryDTO();
        workoutCategoryDTO1.setId(1L);
        WorkoutCategoryDTO workoutCategoryDTO2 = new WorkoutCategoryDTO();
        assertThat(workoutCategoryDTO1).isNotEqualTo(workoutCategoryDTO2);
        workoutCategoryDTO2.setId(workoutCategoryDTO1.getId());
        assertThat(workoutCategoryDTO1).isEqualTo(workoutCategoryDTO2);
        workoutCategoryDTO2.setId(2L);
        assertThat(workoutCategoryDTO1).isNotEqualTo(workoutCategoryDTO2);
        workoutCategoryDTO1.setId(null);
        assertThat(workoutCategoryDTO1).isNotEqualTo(workoutCategoryDTO2);
    }
}
