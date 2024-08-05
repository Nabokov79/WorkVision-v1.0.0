package ru.nabokovsg.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.company.dto.department.DepartmentDto;
import ru.nabokovsg.company.dto.department.ResponseDepartmentDto;
import ru.nabokovsg.company.dto.department.ShortResponseDepartmentDto;
import ru.nabokovsg.company.exceptions.BadRequestException;
import ru.nabokovsg.company.exceptions.NotFoundException;
import ru.nabokovsg.company.mapper.DepartmentMapper;
import ru.nabokovsg.company.model.Department;
import ru.nabokovsg.company.model.enums.DivisionType;
import ru.nabokovsg.company.repository.DepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;
    private final AddressService addressService;
    private final BranchService branchService;

    @Override
    public ShortResponseDepartmentDto save(DepartmentDto departmentDto) {
        if (repository.existsByFullName(departmentDto.getFullName())) {
            throw new BadRequestException(
                    String.format("Department with full name=%s is found", departmentDto.getFullName())
            );
        }
        return mapper.mapToShortDepartmentDto(
                repository.save(mapper.mapToDepartment(departmentDto
                                                     , addressService.get(departmentDto.getAddressId())
                                                     , branchService.getById(departmentDto.getBranchId())
                                                     , DivisionType.DEPARTMENT))
        );
    }

    @Override
    public ShortResponseDepartmentDto update(DepartmentDto departmentDto) {
        if (repository.existsById(departmentDto.getId())) {
            return mapper.mapToShortDepartmentDto(
                    repository.save(
                            mapper.mapToDepartment(departmentDto
                                                 , addressService.get(departmentDto.getAddressId())
                                                 , branchService.getById(departmentDto.getBranchId())
                                                 , DivisionType.DEPARTMENT))
            );
        }
        throw new NotFoundException(
                String.format("Department with name=%s not found for update.", departmentDto.getFullName()));
    }

    @Override
    public ResponseDepartmentDto get(Long id) {
        return mapper.mapToFullDepartmentDto(getById(id));
    }

    @Override
    public Department getById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Department with id=%s not found", id)));
    }

    @Override
    public List<ShortResponseDepartmentDto> getAll(Long branchId) {
        return repository.findByBranch(branchId)
                         .stream()
                         .map(mapper::mapToShortDepartmentDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Department with id=%s not found for delete.", id));
    }
}