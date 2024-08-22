package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.completedRepair.ResponseCompletedRepairDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.norms.ElementRepair;

@Mapper(componentModel = "spring")
public interface CompletedRepairMapper {

    @Mapping(source = "elementRepair.id", target = "repairId")
    @Mapping(source = "elementRepair.repairName", target = "repairName")
    @Mapping(target = "id", ignore = true)
    CompletedRepair mapToCompletedRepair(ElementRepair elementRepair);

    ResponseCompletedRepairDto mapToResponseCompletedRepairDto(CompletedRepair repair);



    @Mapping(source = "partElement.id", target = "partElementId")
    @Mapping(source = "partElement.partElementName", target = "partElementName")
    void mapWithEquipmentPartElement(@MappingTarget CompletedRepair repair
            , EquipmentPartElement partElement);

    @Mapping(source = "quantity", target = "quantity")
    CompletedRepair mapToWithQuantity(@MappingTarget CompletedRepair repair, Integer quantity);
}