package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.LegalRepresentativeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LegalRepresentative} and its DTO {@link LegalRepresentativeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LegalRepresentativeMapper extends EntityMapper<LegalRepresentativeDTO, LegalRepresentative> {



    default LegalRepresentative fromId(Long id) {
        if (id == null) {
            return null;
        }
        LegalRepresentative legalRepresentative = new LegalRepresentative();
        legalRepresentative.setId(id);
        return legalRepresentative;
    }
}
