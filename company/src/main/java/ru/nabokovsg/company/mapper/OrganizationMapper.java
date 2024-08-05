package ru.nabokovsg.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.company.dto.organization.OrganizationDto;
import ru.nabokovsg.company.dto.organization.ResponseOrganizationDto;
import ru.nabokovsg.company.dto.organization.ShortResponseOrganizationDto;
import ru.nabokovsg.company.model.Address;
import ru.nabokovsg.company.model.Organization;
import ru.nabokovsg.company.model.enums.DivisionType;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    @Mapping(source = "address", target = "address")
    @Mapping(source = "divisionType", target = "divisionType")
    @Mapping(source = "organizationDto.id", target = "id")
    Organization mapToOrganization(OrganizationDto organizationDto, Address address, DivisionType divisionType);

    ResponseOrganizationDto mapToOrganizationDto(Organization organization);

    ShortResponseOrganizationDto mapToShortOrganizationDto(Organization organization);
}