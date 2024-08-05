package ru.nabokovsg.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.company.dto.department.DepartmentDto;
import ru.nabokovsg.company.dto.department.ResponseDepartmentDto;
import ru.nabokovsg.company.dto.department.ShortResponseDepartmentDto;
import ru.nabokovsg.company.model.Address;
import ru.nabokovsg.company.model.Branch;
import ru.nabokovsg.company.model.Department;
import ru.nabokovsg.company.model.enums.DivisionType;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @Mapping(source = "departmentDto.fullName", target = "fullName")
    @Mapping(source = "departmentDto.shortName", target = "shortName")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "branch", target = "branch")
    @Mapping(source = "divisionType", target = "divisionType")
    @Mapping(source = "departmentDto.id", target = "id")
    Department mapToDepartment(DepartmentDto departmentDto, Address address, Branch branch, DivisionType divisionType);

    ResponseDepartmentDto mapToFullDepartmentDto(Department department);

    ShortResponseDepartmentDto mapToShortDepartmentDto(Department department);
}