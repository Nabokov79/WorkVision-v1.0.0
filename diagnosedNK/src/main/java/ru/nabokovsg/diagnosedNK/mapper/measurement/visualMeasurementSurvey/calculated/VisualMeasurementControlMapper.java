package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementControl.ResponseVisualMeasurementControlDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementControl.VisualMeasurementControlDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementControl.VisualMeasurementControl;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;

@Mapper(componentModel = "spring")
public interface VisualMeasurementControlMapper {

    @Mapping(source = "defectDto.workJournalId", target = "workJournalId")
    @Mapping(source = "defect.id", target = "defectId")
    @Mapping(source = "defect.defectName", target = "defectName")
    @Mapping(source = "defectDto.standardSize", target = "standardSize")
    @Mapping(source = "defectDto.weldedJointNumber", target = "weldedJointNumber")
    @Mapping(source = "defectDto.coordinates", target = "coordinates")
    @Mapping(source = "estimation", target = "estimation")
    @Mapping(source = "defectDto.id", target = "id")
    VisualMeasurementControl mapToVisualMeasurementControl(VisualMeasurementControlDto defectDto
                                                        , Defect defect
                                                        , String estimation);

    ResponseVisualMeasurementControlDto mapToResponseVisualMeasuringSurveyDto(VisualMeasurementControl vmControl);
}