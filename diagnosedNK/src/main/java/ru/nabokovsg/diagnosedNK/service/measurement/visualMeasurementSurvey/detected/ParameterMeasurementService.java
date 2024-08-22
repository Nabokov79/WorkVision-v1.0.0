package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementControl.VisualMeasurementControl;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;

import java.util.List;
import java.util.Set;

public interface ParameterMeasurementService {

    Set<ParameterMeasurement> saveForIdentifiedDefect(IdentifiedDefect identifiedDefect
                                                    , Set<MeasuredParameter> parameters
                                                    , List<ParameterMeasurementDto> parametersDto);

    Set<ParameterMeasurement> saveForCompletedRepair(CompletedRepair repair
                                                   , Set<MeasuredParameter> parameters
                                                   , List<ParameterMeasurementDto> parametersDto);

    Set<ParameterMeasurement> saveForVMControl(VisualMeasurementControl vmControl
                                            , Set<MeasuredParameter> parameters
                                            , List<ParameterMeasurementDto> parametersDto);

    Set<ParameterMeasurement> update(Set<ParameterMeasurement> parameters, List<ParameterMeasurementDto> parametersDto);

    void deleteAll(Set<ParameterMeasurement> parameters);
}