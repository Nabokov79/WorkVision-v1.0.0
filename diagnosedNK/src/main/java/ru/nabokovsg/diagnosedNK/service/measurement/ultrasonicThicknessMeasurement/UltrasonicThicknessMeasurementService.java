package ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicThicknessMeasurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UTPredicateData;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurement;

public interface UltrasonicThicknessMeasurementService {

    UltrasonicThicknessMeasurement save(UltrasonicThicknessMeasurementDto measurementDto
                                      , DiagnosticEquipmentData objectData
                                      , ElementData objectElementData);

    UltrasonicThicknessMeasurement update(UltrasonicThicknessMeasurementDto measurementDto
                                        , UltrasonicThicknessMeasurement measurement
                                        , DiagnosticEquipmentData objectData
                                        , ElementData objectElementData);

    UltrasonicThicknessMeasurement getPredicateData(UTPredicateData predicateData);
}