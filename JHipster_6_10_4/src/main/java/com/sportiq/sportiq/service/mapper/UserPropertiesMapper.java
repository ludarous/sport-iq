package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.UserPropertiesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserProperties} and its DTO {@link UserPropertiesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserPropertiesMapper extends EntityMapper<UserPropertiesDTO, UserProperties> {



    default UserProperties fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserProperties userProperties = new UserProperties();
        userProperties.setId(id);
        return userProperties;
    }
}
