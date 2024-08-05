package ru.nabokovsg.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.company.dto.branch.BranchDto;
import ru.nabokovsg.company.dto.branch.ResponseBranchDto;
import ru.nabokovsg.company.dto.branch.ShortResponseBranchDto;
import ru.nabokovsg.company.exceptions.BadRequestException;
import ru.nabokovsg.company.exceptions.NotFoundException;
import ru.nabokovsg.company.mapper.BranchMapper;
import ru.nabokovsg.company.model.Branch;
import ru.nabokovsg.company.model.enums.DivisionType;
import ru.nabokovsg.company.repository.BranchRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository repository;
    private final BranchMapper mapper;
    private final OrganizationService organizationService;
    private final AddressService addressService;

    @Override
    public ShortResponseBranchDto save(BranchDto branchDto) {
        if (repository.existsByFullName(branchDto.getFullName())) {
            throw new BadRequestException(String.format("Branch with full name=%s is found", branchDto.getFullName()));
        }
        return mapper.mapToShortBranchDto(
                repository.save(mapper.mapToBranch(branchDto
                                                 , addressService.get(branchDto.getAddressId())
                                                 , organizationService.getById(branchDto.getOrganizationId())
                                                 , DivisionType.BRANCH))
        );
    }

    @Override
    public ShortResponseBranchDto update(BranchDto branchDto) {
        if (repository.existsById(branchDto.getId())) {
            return mapper.mapToShortBranchDto(
                    repository.save(mapper.mapToBranch(branchDto
                                                     , addressService.get(branchDto.getAddressId())
                                                     , organizationService.getById(branchDto.getOrganizationId())
                                                     , DivisionType.BRANCH)
                    )
            );
        }
        throw new NotFoundException(
                               String.format("Branch wth name=%s not found for update", branchDto.getFullName()));
    }

    @Override
    public ResponseBranchDto get(Long id) {
        return mapper.mapToBranchDto(getById(id));
    }

    @Override
    public List<ShortResponseBranchDto> getAll(Long id) {
        return repository.findAllByOrganizationId(id)
                         .stream()
                         .map(mapper::mapToShortBranchDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Branch wth id=%s not found for delete", id));
    }

    @Override
    public Branch getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Branch wth id=%s not found", id)));

    }
}