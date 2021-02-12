package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.OrganisationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organisation} and its DTO {@link OrganisationDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface OrganisationMapper extends EntityMapper<OrganisationDTO, Organisation> {

    @Mapping(source = "owner.id", target = "ownerId")
    OrganisationDTO toDto(Organisation organisation);

    @Mapping(target = "memberships", ignore = true)
    @Mapping(target = "removeMemberships", ignore = true)
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(target = "visibleActivities", ignore = true)
    @Mapping(target = "removeVisibleActivities", ignore = true)
    Organisation toEntity(OrganisationDTO organisationDTO);

    default Organisation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Organisation organisation = new Organisation();
        organisation.setId(id);
        return organisation;
    }
}
