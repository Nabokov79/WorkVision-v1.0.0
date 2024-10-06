package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.Map;
import java.util.Set;

public interface CalculateOneByOneParametersService {

    void calculateOneByOne(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> parameters);

    void calculateIdentifiedDefectOneByOneParameters(Set<IdentifiedDefect> defects, ParameterCalculationType calculationType, Map<String, CalculateParameterMeasurement> parameters);


    void calculateCompletedRepairOneByOneParameters(Set<CompletedRepair> repairs, ParameterCalculationType calculationType, Map<String, CalculateParameterMeasurement> parameters);

    void calculateOneByOneParameters(Map<Long, Set<CalculateParameterMeasurement>> calculatedParameters, ParameterCalculationType calculationType, Map<String, CalculateParameterMeasurement> parameters);
}