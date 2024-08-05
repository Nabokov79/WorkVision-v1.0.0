package ru.nabokovsg.company.mapper;

import org.mapstruct.Mapper;
import ru.nabokovsg.company.dto.address.AddressDto;
import ru.nabokovsg.company.dto.address.ResponseAddressDto;
import ru.nabokovsg.company.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address mapToAddress(AddressDto addressDto);

    ResponseAddressDto mapToFullAddressDto(Address address);
}