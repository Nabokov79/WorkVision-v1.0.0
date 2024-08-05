package ru.nabokovsg.company.service;

import ru.nabokovsg.company.dto.employee.EmployeeDto;
import ru.nabokovsg.company.dto.employee.ResponseEmployeeDto;

import java.util.List;

public interface EmployeeService {

    ResponseEmployeeDto save(EmployeeDto employeeDto);

    ResponseEmployeeDto update(EmployeeDto employeeDto);

    ResponseEmployeeDto get(Long id);

    List<ResponseEmployeeDto> getAll(Long id, String divisionType, List<Long> ids);

    void delete(Long id);
}
