package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedPartElement;

public interface CalculatedPartElementService {

    CalculatedPartElement get(CalculatedElement element, String partElementName);
    void save(CalculatedElement element, String partElementName);
}