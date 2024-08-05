package ru.nabokovsg.laboratoryNK.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.laboratoryNK.client.LaboratoryClient;
import ru.nabokovsg.laboratoryNK.dto.client.*;
import ru.nabokovsg.laboratoryNK.dto.workJournal.WorkJournalDto;
import ru.nabokovsg.laboratoryNK.mapper.JournalBuilderMapper;
import ru.nabokovsg.laboratoryNK.model.LaboratoryEmployee;
import ru.nabokovsg.laboratoryNK.model.WorkJournal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class JournalBuilderServiceImpl implements JournalBuilderService {

    private final LaboratoryClient client;
    private final StringBuilderService builderService;
    private final JournalBuilderMapper mapper;
    private final LaboratoryEmployeeService employeeService;

    @Override
    public WorkJournal build(WorkJournalDto journalDto) {
        WorkJournal journal = mapper.mapWorkJournal(journalDto);
        BranchDto branch = client.getBranch(journalDto.getBranchId());
        getEquipment(journal, journalDto);
        getPlaceWork(journal, journalDto, branch);
        getAddress(journal, journalDto, branch);
        getEmployee(journal, journalDto);
        return journal;
    }

    private void getAddress(WorkJournal journal, WorkJournalDto journalDto, BranchDto branch) {
        String address = journalDto.getAddress();
        if (address == null) {
            address = builderService.buildBuilding(branch.getExploitationRegions()
                    .stream()
                    .map(ExploitationRegionDto::getBuildings)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toMap(b -> b.getAddress().getId(), b -> b))
                    .get(journalDto.getAddressId()));
        }
        mapper.mapWithAddress(journal, address);
    }

    private void getEquipment(WorkJournal journal, WorkJournalDto journalDto) {
        if (journalDto.getEquipmentName() != null) {
            mapper.mapWithEquipmentName(journal, journalDto.getEquipmentName());
        }
        EquipmentDto equipment = client.getEquipment(journalDto.getEquipmentId());
        mapper.mapWithEquipment(journal, equipment);
        client.addEquipmentDataForDiagnosed(equipment);
    }

    public void getPlaceWork(WorkJournal journal, WorkJournalDto journalDto, BranchDto branch) {
        String placeWork;
        if (!branch.getExploitationRegions().isEmpty()) {
            placeWork = branch.getExploitationRegions()
                    .stream()
                    .collect(Collectors.toMap(ExploitationRegionDto::getId, e -> e))
                    .get(journalDto.getExploitationRegionId())
                    .getFullName();
        } else {
            placeWork = branch.getHeatSupplyAreas()
                    .stream()
                    .collect(Collectors.toMap(HeatSupplyAreaDto::getId, e -> e))
                    .get(journalDto.getHeatSupplyAreaId())
                    .getFullName();
        }
        mapper.mapWithBranch(journal, branch.getFullName(), placeWork);
    }

    private void getEmployee(WorkJournal journal, WorkJournalDto journalDto) {
        Map<Long, LaboratoryEmployee> employees = employeeService.getAllById(
                                                                Stream.of(journalDto.getLaboratoryEmployeesIds()
                                                                                , List.of(journalDto.getChiefId()))
                                                                        .flatMap(Collection::stream)
                                                                        .toList())
                                                        .stream()
                                                        .collect(Collectors.toMap(LaboratoryEmployee::getId, l -> l));
        LaboratoryEmployee chief = employees.get(journalDto.getChiefId());
        employees.remove(journalDto.getChiefId());
        mapper.mapWithEmployee(journal, chief, new HashSet<>(employees.values()));
    }
}