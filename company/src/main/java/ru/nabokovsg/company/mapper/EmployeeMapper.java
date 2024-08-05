package ru.nabokovsg.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.company.dto.employee.EmployeeDto;
import ru.nabokovsg.company.dto.employee.ResponseEmployeeDto;
import ru.nabokovsg.company.model.*;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee mapToEmployee(EmployeeDto employeeDto);

    @Mapping(source = "branch.id", target = "placeWorkId")
    @Mapping(source = "branch.fullName", target = "placeWork")
    @Mapping(source = "branch.divisionType", target = "divisionType")
    @Mapping(target = "id", ignore = true)
    Employee mapFromBranch(@MappingTarget Employee employee, Branch branch);

    @Mapping(source = "department.id", target = "placeWorkId")
    @Mapping(source = "department.fullName", target = "placeWork")
    @Mapping(source = "department.divisionType", target = "divisionType")
    @Mapping(target = "id", ignore = true)
    Employee mapFromDepartment(@MappingTarget Employee employee, Department department);

    @Mapping(source = "area.id", target = "placeWorkId")
    @Mapping(source = "area.fullName", target = "placeWork")
    @Mapping(source = "area.divisionType", target = "divisionType")
    @Mapping(target = "id", ignore = true)
    Employee mapFromHeatSupplyArea(@MappingTarget Employee employee, HeatSupplyArea area);

    @Mapping(source = "region.id", target = "placeWorkId")
    @Mapping(source = "region.fullName", target = "placeWork")
    @Mapping(source = "region.divisionType", target = "divisionType")
    @Mapping(target = "id", ignore = true)
    Employee mapFromBExploitationRegion(@MappingTarget Employee employee, ExploitationRegion region);

    ResponseEmployeeDto mapToEmployeeDto(Employee employee);
}