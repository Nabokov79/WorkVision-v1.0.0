package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.*;
import java.util.stream.Collectors;

public interface CalculateAllParametersService {

    void calculateAll(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> parameters);

    void calculateAllParameters(Set<CalculateParameterMeasurement> calculatedParameters, ParameterCalculationType calculationType, Map<String, CalculateParameterMeasurement> parameters);

    void calculateCompletedRepairAllParameter(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> parameters);
}