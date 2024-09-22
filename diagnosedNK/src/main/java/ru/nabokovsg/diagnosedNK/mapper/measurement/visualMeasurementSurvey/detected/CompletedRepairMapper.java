package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.completedRepair.ResponseCompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;
import ru.nabokovsg.diagnosedNK.model.norms.ElementRepair;

@Mapper(componentModel = "spring")
public interface CompletedRepairMapper {

    @Mapping(source = "repairDto.equipmentId", target = "equipmentId")
    @Mapping(source = "repair.id", target = "repairId")
    @Mapping(source = "repair.repairName", target = "repairName")
    @Mapping(source = "repairDto.id", target = "id")
    @Mapping(source = "element.id", target = "elementId")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(target = "parameterMeasurements", ignore = true)
    CompletedRepair mapToCompletedRepair(CompletedRepairDto repairDto
                                       , ElementRepair repair
                                       , EquipmentElement element);

    ResponseCompletedRepairDto mapToResponseCompletedRepairDto(CompletedRepair repair);



    @Mapping(source = "partElement.id", target = "partElementId")
    @Mapping(source = "partElement.partElementName", target = "partElementName")
    @Mapping(target = "id", ignore = true)
    void mapWithEquipmentPartElement(@MappingTarget CompletedRepair repair
                                                  , EquipmentPartElement partElement);
}