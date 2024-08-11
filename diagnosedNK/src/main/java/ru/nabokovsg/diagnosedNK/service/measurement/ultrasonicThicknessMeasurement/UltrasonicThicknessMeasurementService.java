package ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicThicknessMeasurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.equipment.StandardSize;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurement;

public interface UltrasonicThicknessMeasurementService {

    UltrasonicThicknessMeasurement save(UltrasonicThicknessMeasurementDto measurementDto, StandardSize standardSize);

    UltrasonicThicknessMeasurement update(UltrasonicThicknessMeasurementDto measurementDto
                                        , UltrasonicThicknessMeasurement measurement
                                        , StandardSize standardSize);
}