package ru.nabokovsg.diagnosedNK.service.measurement.hardnessMeasurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.ElementHardnessMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.PartElementHardnessMeasurement;

import java.util.Set;

public interface PartElementHardnessMeasurementService {

    PartElementHardnessMeasurement save(HardnessMeasurementDto measurementDto
                                      , ElementHardnessMeasurement elementMeasurement
                                      , Set<EquipmentPartElement> partsElement);

    Set<PartElementHardnessMeasurement> update(HardnessMeasurementDto measurementDto
                                             , Set<PartElementHardnessMeasurement> partElementMeasurements
                                             , Set<EquipmentPartElement> partsElement);
}