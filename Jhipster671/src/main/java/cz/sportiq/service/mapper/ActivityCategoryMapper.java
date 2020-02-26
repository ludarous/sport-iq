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
    @Mapping(target = "removeChildActivityCategories", ignore = true)
    @Mapping(source = "parentActivityCategoryId", target = "parentActivityCategory")
    @Mapping(target = "activities", ignore = true)
    @Mapping(target = "removeActivities", ignore = true)
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
