package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.BodyCharacteristicsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BodyCharacteristics} and its DTO {@link BodyCharacteristicsDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnitMapper.class})
public interface BodyCharacteristicsMapper extends EntityMapper<BodyCharacteristicsDTO, BodyCharacteristics> {

    @Mapping(source = "heightUnit.id", target = "heightUnitId")
    @Mapping(source = "widthUnit.id", target = "widthUnitId")
    BodyCharacteristicsDTO toDto(BodyCharacteristics bodyCharacteristics);

    @Mapping(source = "heightUnitId", target = "heightUnit")
    @Mapping(source = "widthUnitId", target = "widthUnit")
    BodyCharacteristics toEntity(BodyCharacteristicsDTO bodyCharacteristicsDTO);

    default BodyCharacteristics fromId(Long id) {
        if (id == null) {
            return null;
        }
        BodyCharacteristics bodyCharacteristics = new BodyCharacteristics();
        bodyCharacteristics.setId(id);
        return bodyCharacteristics;
    }
}
