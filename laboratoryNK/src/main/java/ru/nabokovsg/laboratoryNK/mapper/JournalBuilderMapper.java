package ru.nabokovsg.laboratoryNK.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.laboratoryNK.dto.client.EquipmentDto;
import ru.nabokovsg.laboratoryNK.dto.workJournal.WorkJournalDto;
import ru.nabokovsg.laboratoryNK.model.LaboratoryEmployee;
import ru.nabokovsg.laboratoryNK.model.WorkJournal;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface JournalBuilderMapper {

    WorkJournal mapWorkJournal(WorkJournalDto journalDto);
    @Mapping(source = "equipment.equipmentTypeId", target = "equipmentTypeId")
    @Mapping(source = "equipment.id", target = "equipmentId")
    void mapWithEquipment(@MappingTarget WorkJournal journal, EquipmentDto equipment);

    @Mapping(source = "equipmentName", target = "equipmentDiagnosed")
    void mapWithEquipmentName(@MappingTarget WorkJournal journal, String equipmentName);

    @Mapping(source = "branch", target = "branch")
    @Mapping(source = "placeWork", target = "placeWork")
    void mapWithBranch(@MappingTarget WorkJournal journal, String branch, String placeWork);

    @Mapping(source = "address", target = "address")
    void mapWithAddress(@MappingTarget WorkJournal journal, String address);

    @Mapping(source = "chief", target = "chief")
    @Mapping(source = "employees", target = "employees")
    void mapWithEmployee(@MappingTarget WorkJournal journal
                                      , LaboratoryEmployee chief
                                      , Set<LaboratoryEmployee> employees);
}
