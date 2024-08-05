package ru.nabokovsg.laboratoryNK.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.laboratoryNK.client.LaboratoryClient;
import ru.nabokovsg.laboratoryNK.dto.client.EmployeeDto;
import ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employees.ResponseLaboratoryEmployeeDto;
import ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employees.ShortResponseLaboratoryEmployeeDto;
import ru.nabokovsg.laboratoryNK.exceptions.NotFoundException;
import ru.nabokovsg.laboratoryNK.mapper.LaboratoryEmployeeMapper;
import ru.nabokovsg.laboratoryNK.model.LaboratoryEmployee;
import ru.nabokovsg.laboratoryNK.repository.LaboratoryEmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LaboratoryEmployeeServiceImpl implements LaboratoryEmployeeService {

    private final LaboratoryEmployeeRepository repository;
    private final LaboratoryEmployeeMapper mapper;
    private final LaboratoryClient client;
    private final StringBuilderService builderService;

    @Override
    public List<ShortResponseLaboratoryEmployeeDto> copy(Long id, String divisionType) {
        List<EmployeeDto> employeesDto = client.getAllEmployees(id, divisionType);
        Set<Long> employeeIds = repository.findAllByEmployeeId(employeesDto.stream().map(EmployeeDto::getId).toList());
        employeesDto = employeesDto.stream()
                .filter(e -> !employeeIds.contains(e.getId()))
                .toList();
        if (!employeesDto.isEmpty()) {
            return repository.saveAll(employeesDto
                              .stream()
                              .map(e -> mapper.mapToLaboratoryEmployee(e.getId()
                                                                     , e.getPost()
                                                                     , builderService.buildInitials(e)))
                              .toList())
                              .stream()
                              .map(mapper::mapToShortResponseLaboratoryEmployeeDto)
                              .toList();
        }
        return new ArrayList<>();
    }

    @Override
    public ResponseLaboratoryEmployeeDto get(Long id) {
        return mapper.mapToResponseLaboratoryEmployeeDto(getById(id));
    }

    @Override
    public List<ShortResponseLaboratoryEmployeeDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::mapToShortResponseLaboratoryEmployeeDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("LaboratoryEmployee with id=%s not found for delete", id));
    }

    @Override
    public LaboratoryEmployee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("LaboratoryEmployee with id=%s not found", id)));
    }

    @Override
    public List<LaboratoryEmployee> getAllById(List<Long> ids) {
        List<LaboratoryEmployee> employees = repository.findAllById(ids);
        if (ids.size() != employees.size()) {
            throw new NotFoundException(String.format("Laboratory Employee with ids=%s not found", ids));
        }
        return employees;
    }
}