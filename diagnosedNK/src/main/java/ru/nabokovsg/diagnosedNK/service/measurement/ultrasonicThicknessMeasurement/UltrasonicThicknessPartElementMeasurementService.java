package ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicThicknessMeasurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessPartElementMeasurement;

import java.util.Set;

public interface UltrasonicThicknessPartElementMeasurementService {

    UltrasonicThicknessPartElementMeasurement save(UltrasonicThicknessMeasurementDto measurementDto
                                                 , UltrasonicThicknessElementMeasurement elementMeasurement
                                                 , DiagnosticEquipmentData objectData
                                                 , ElementData objectElementData);

    Set<UltrasonicThicknessPartElementMeasurement> update(UltrasonicThicknessMeasurementDto measurementDto
                                                , Set<UltrasonicThicknessPartElementMeasurement> partElementMeasurements
                                                , DiagnosticEquipmentData objectData
                                                , ElementData objectElementData);
}