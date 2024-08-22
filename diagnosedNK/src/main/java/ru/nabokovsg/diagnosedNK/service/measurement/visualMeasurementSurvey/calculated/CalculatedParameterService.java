package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;


import java.util.Set;

public interface CalculatedParameterService {

    void save(CalculatedDefect calculatedDefect
            , Set<CalculatedParameter> parameters
            , Defect defect
            , Set<ParameterMeasurement> parameterMeasurements);
}