package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;

import java.util.List;

public interface CalculatedParameterService {

    void save(CalculatedParameterData parameterData);

   void update(List<CalculatedParameter> parameters);
}