package ru.nabokovsg.diagnosedNK.service.measurement.hardnessMeasurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.HardnessMeasurement;

public interface HardnessMeasurementService {

    HardnessMeasurement save(HardnessMeasurementDto measurementDto
                           , DiagnosticEquipmentData objectData
                           , ElementData objectElementData);

    HardnessMeasurement update(HardnessMeasurementDto measurementDto
                             , HardnessMeasurement measurement
                             , DiagnosticEquipmentData objectData
                             , ElementData objectElementData);
}