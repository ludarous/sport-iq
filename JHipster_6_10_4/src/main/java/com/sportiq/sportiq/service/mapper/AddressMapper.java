package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mapping(source = "country.id", target = "countryId")
    AddressDTO toDto(Address address);

    @Mapping(source = "countryId", target = "country")
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
