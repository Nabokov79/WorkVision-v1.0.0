package ru.nabokovsg.diagnosedNK.service.measurement.hardnessMeasurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.equipment.StandardSize;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.HardnessMeasurement;

public interface HardnessMeasurementService {

    HardnessMeasurement save(HardnessMeasurementDto measurementDto, StandardSize standardSize);

    HardnessMeasurement update(HardnessMeasurementDto measurementDto
                             , HardnessMeasurement measurement
                             , StandardSize standardSize);
}