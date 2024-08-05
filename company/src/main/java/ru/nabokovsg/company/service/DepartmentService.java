package ru.nabokovsg.company.service;

import ru.nabokovsg.company.dto.department.DepartmentDto;
import ru.nabokovsg.company.dto.department.ResponseDepartmentDto;
import ru.nabokovsg.company.dto.department.ShortResponseDepartmentDto;
import ru.nabokovsg.company.model.Department;

import java.util.List;

public interface DepartmentService {

    ShortResponseDepartmentDto save(DepartmentDto departmentDto);

    ShortResponseDepartmentDto update(DepartmentDto departmentDto);

    ResponseDepartmentDto get(Long id);

    Department getById(Long id);

    List<ShortResponseDepartmentDto> getAll(Long id);

    void delete(Long id);
}