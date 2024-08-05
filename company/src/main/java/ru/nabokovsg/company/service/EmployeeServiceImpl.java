package ru.nabokovsg.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.company.dto.employee.EmployeeDto;
import ru.nabokovsg.company.dto.employee.ResponseEmployeeDto;
import ru.nabokovsg.company.exceptions.BadRequestException;
import ru.nabokovsg.company.exceptions.NotFoundException;
import ru.nabokovsg.company.mapper.EmployeeMapper;
import ru.nabokovsg.company.model.Employee;
import ru.nabokovsg.company.model.enums.DivisionType;
import ru.nabokovsg.company.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;
    private final BranchService branchService;
    private final DepartmentService departmentService;
    private final HeatSupplyAreaService areaService;
    private final ExploitationRegionService regionService;

    @Override
    public ResponseEmployeeDto save(EmployeeDto employeeDto) {
        Employee employee = repository.findByNameAndPatronymicAndSurname(employeeDto.getName()
                                                                       , employeeDto.getPatronymic()
                                                                       , employeeDto.getSurname());
        if (employee == null) {
            employee = repository.save(setPlaceWork(mapper.mapToEmployee(employeeDto), employeeDto));
        }
        return mapper.mapToEmployeeDto(employee);
    }

    @Override
    public ResponseEmployeeDto update(EmployeeDto employeeDto) {
        if (repository.existsById(employeeDto.getId())) {
            return mapper.mapToEmployeeDto(
                    repository.save(setPlaceWork(mapper.mapToEmployee(employeeDto), employeeDto)));
            }
        throw new NotFoundException(String.format("Employee with id=%s not found for update",employeeDto.getId()));
    }

    @Override
    public ResponseEmployeeDto get(Long id) {
        return mapper.mapToEmployeeDto(getById(id));
    }


    @Override
    public List<ResponseEmployeeDto> getAll(Long id, String divisionType, List<Long> ids) {
        if (id != null && divisionType != null) {
            return repository.findAllByPlaceWorkIdAndDivisionType(id, convert(divisionType))
                    .stream()
                    .map(mapper::mapToEmployeeDto)
                    .toList();
        }
        if (ids != null && !ids.isEmpty()) {
            return repository.findAllById(ids).stream()
                    .map(mapper::mapToEmployeeDto)
                    .toList();
        }
        return repository.findAll()
                         .stream()
                         .map(mapper::mapToEmployeeDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Employee with id = %s not found for delete",id));
    }

    private DivisionType convert(String divisionType) {
        return DivisionType.from(divisionType)
                .orElseThrow(() -> new BadRequestException(
                        String.format("Unknown data format divisionType=%s", divisionType))
                );
    }

    private Employee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Employee with id=%s was not found", id)));
    }

    private Employee setPlaceWork(Employee employee, EmployeeDto employeeDto) {
        DivisionType division = convert(employeeDto.getDivisionType());
        switch (division) {
            case BRANCH -> {
                return mapper.mapFromBranch(employee, branchService.getById(employeeDto.getDivisionId()));
            }
            case DEPARTMENT -> {
                return mapper.mapFromDepartment(employee, departmentService.getById(employeeDto.getDivisionId()));
            }
            case HEAT_SUPPLY_AREA -> {
                return mapper.mapFromHeatSupplyArea(employee, areaService.getById(employeeDto.getDivisionId()));
            }
            case EXPLOITATION_REGION -> {
                return mapper.mapFromBExploitationRegion(employee, regionService.getById(employeeDto.getDivisionId()));
            }
            default -> throw new BadRequestException(String.format("Unknown data format divisionType=%s", division));
        }
    }
}