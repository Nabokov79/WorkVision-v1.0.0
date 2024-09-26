package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.builders.ParameterMeasurementBuilder;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ParameterMeasurementService {

    Set<ParameterMeasurement> save(ParameterMeasurementBuilder builder);

    Set<ParameterMeasurement> update(Set<ParameterMeasurement> parameters, List<ParameterMeasurementDto> parametersDto);

    void deleteAll(Set<ParameterMeasurement> parameters);

    boolean searchParameterDuplicate(Set<ParameterMeasurement> parameterMeasurements, Map<Long, ParameterMeasurementDto> parameters);
}