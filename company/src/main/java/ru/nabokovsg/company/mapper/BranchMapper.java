package ru.nabokovsg.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.company.dto.branch.BranchDto;
import ru.nabokovsg.company.dto.branch.ResponseBranchDto;
import ru.nabokovsg.company.dto.branch.ShortResponseBranchDto;
import ru.nabokovsg.company.model.Address;
import ru.nabokovsg.company.model.Branch;
import ru.nabokovsg.company.model.Organization;
import ru.nabokovsg.company.model.enums.DivisionType;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    @Mapping(source = "branchDto.fullName", target = "fullName")
    @Mapping(source = "branchDto.shortName", target = "shortName")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "organization", target = "organization")
    @Mapping(source = "divisionType", target = "divisionType")
    @Mapping(source = "branchDto.id", target = "id")
    Branch mapToBranch(BranchDto branchDto, Address address, Organization organization, DivisionType divisionType);

    ResponseBranchDto mapToBranchDto(Branch branch);

    ShortResponseBranchDto mapToShortBranchDto(Branch branch);
}