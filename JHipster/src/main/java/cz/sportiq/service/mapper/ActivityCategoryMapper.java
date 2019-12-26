package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.ActivityCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ActivityCategory} and its DTO {@link ActivityCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ActivityCategoryMapper extends EntityMapper<ActivityCategoryDTO, ActivityCategory> {

    @Mapping(source = "parentActivityCategory.id", target = "parentActivityCategoryId")
    ActivityCategoryDTO toDto(ActivityCategory activityCategory);

    @Mapping(source = "childActivityCategories", target = "childActivityCategories")
    @Mapping(source = "parentActivityCategoryId", target = "parentActivityCategory")
    ActivityCategory toEntity(ActivityCategoryDTO activityCategoryDTO);

    default ActivityCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActivityCategory activityCategory = new ActivityCategory();
        activityCategory.setId(id);
        return activityCategory;
    }
}
