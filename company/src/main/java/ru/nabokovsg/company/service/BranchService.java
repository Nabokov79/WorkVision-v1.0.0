package ru.nabokovsg.company.service;

import ru.nabokovsg.company.dto.branch.BranchDto;
import ru.nabokovsg.company.dto.branch.ResponseBranchDto;
import ru.nabokovsg.company.dto.branch.ShortResponseBranchDto;
import ru.nabokovsg.company.model.Branch;

import java.util.List;

public interface BranchService {

    ShortResponseBranchDto save(BranchDto branchDto);

    ShortResponseBranchDto update(BranchDto branchDto);

    ResponseBranchDto get(Long id);

    Branch getById(Long id);

    List<ShortResponseBranchDto> getAll(Long id);

    void  delete(Long id);
}