package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.completedRepair.ResponseCompletedRepairDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.norms.ElementRepair;

@Mapper(componentModel = "spring")
public interface CompletedRepairMapper {

    @Mapping(source = "elementRepair.id", target = "repairId")
    @Mapping(source = "elementRepair.repairName", target = "repairName")
    @Mapping(target = "id", ignore = true)
    CompletedRepair mapToCompletedRepair(ElementRepair elementRepair);

    ResponseCompletedRepairDto mapToResponseCompletedRepairDto(CompletedRepair repair);
}