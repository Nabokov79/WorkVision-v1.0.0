package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;

import java.util.List;
import java.util.Map;

public interface SearchDuplicateParameterService {

    void search(Map<String, CalculatedParameter> parametersDb, List<CalculatedParameter> calculatedParameters);
}