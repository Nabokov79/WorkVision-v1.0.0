package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedElement;

public interface CalculatedElementService {

    CalculatedElement get(Long equipmentId, String elementName, String partElementName);
}