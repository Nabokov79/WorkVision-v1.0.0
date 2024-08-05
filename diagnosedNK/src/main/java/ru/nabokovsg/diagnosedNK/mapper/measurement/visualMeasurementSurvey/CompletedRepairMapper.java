package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.completedRepair.ResponseCompletedRepairDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ExaminedPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.VisualMeasuringSurvey;
import ru.nabokovsg.diagnosedNK.model.norms.ElementRepair;

@Mapper(componentModel = "spring")
public interface CompletedRepairMapper {

    @Mapping(source = "elementRepair.id", target = "repairId")
    @Mapping(source = "elementRepair.repairName", target = "repairName")
    @Mapping(target = "id", ignore = true)
    CompletedRepair mapToCompletedRepair(ElementRepair elementRepair);

    @Mapping(source = "visualMeasuringSurvey", target = "visualMeasuringSurvey")
    @Mapping(target = "id", ignore = true)
    CompletedRepair mapWithVisualMeasuringSurvey(@MappingTarget CompletedRepair repair
                                                              , VisualMeasuringSurvey visualMeasuringSurvey);

    @Mapping(source = "examinedPartElement", target = "examinedPartElement")
    @Mapping(target = "id", ignore = true)
    CompletedRepair mapWithExaminedPartElement(@MappingTarget CompletedRepair repair
                                                            , ExaminedPartElement examinedPartElement);

    ResponseCompletedRepairDto mapToResponseCompletedRepairDto(CompletedRepair repair);
}