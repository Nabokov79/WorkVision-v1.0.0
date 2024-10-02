package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CalculateParameterMeasurementFactory {

    List<CalculateParameterMeasurement> calculateOneByOne(Map<Long, Set<CalculateParameterMeasurement>> parameterMeasurements, ParameterCalculationType calculationType);

    List<CalculateParameterMeasurement> calculateAll(Map<Long, Set<CalculateParameterMeasurement>> parameterMeasurements, ParameterCalculationType calculationType);
}