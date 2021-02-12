package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.AgreementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Agreement} and its DTO {@link AgreementDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgreementMapper extends EntityMapper<AgreementDTO, Agreement> {



    default Agreement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Agreement agreement = new Agreement();
        agreement.setId(id);
        return agreement;
    }
}
