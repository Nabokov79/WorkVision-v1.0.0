package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;

@Mapper(componentModel = "spring")
public interface CalculatedRepairMapper {

    @Mapping(source = "repair.repairId", target = "repairId")
    @Mapping(source = "repair.repairName", target = "repairName")
    @Mapping(source = "element", target = "element")
    @Mapping(target = "parameters", ignore = true)
    @Mapping(target = "partElement", ignore = true)
    @Mapping(target = "id", ignore = true)
    CalculatedRepair mapWithCalculatedElement(CompletedRepair repair, CalculatedElement element);

    @Mapping(source = "repair.repairId", target = "repairId")
    @Mapping(source = "repair.repairName", target = "repairName")
    @Mapping(source = "element", target = "element")
    @Mapping(source = "partElement", target = "partElement")
    @Mapping(target = "parameters", ignore = true)
    @Mapping(target = "id", ignore = true)
    CalculatedRepair mapWithCalculatedPartElement(CompletedRepair repair, CalculatedElement element, CalculatedPartElement partElement);
}