package ru.nabokovsg.diagnosedNK.service.measurement.hardnessMeasurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.ElementHardnessMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.PartElementHardnessMeasurement;

import java.util.Set;

public interface PartElementHardnessMeasurementService {

    PartElementHardnessMeasurement save(HardnessMeasurementDto measurementDto
            , ElementHardnessMeasurement elementMeasurement
            , DiagnosticEquipmentData objectData
            , ElementData objectElementData);

    Set<PartElementHardnessMeasurement> update(HardnessMeasurementDto measurementDto
            , Set<PartElementHardnessMeasurement> partElementMeasurements
            , DiagnosticEquipmentData objectData
            , ElementData objectElementData);
}