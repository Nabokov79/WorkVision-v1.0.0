package ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicThicknessMeasurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessPartElementMeasurement;

import java.util.Set;

public interface UltrasonicThicknessPartElementMeasurementService {

    UltrasonicThicknessPartElementMeasurement save(UltrasonicThicknessMeasurementDto measurementDto
                                                 , UltrasonicThicknessElementMeasurement elementMeasurement
                                                 , Set<EquipmentPartElement> partsElements);

    Set<UltrasonicThicknessPartElementMeasurement> update(UltrasonicThicknessMeasurementDto measurementDto
                                              , Set<UltrasonicThicknessPartElementMeasurement> partElementMeasurements
                                              , Set<EquipmentPartElement> partsElements);
}