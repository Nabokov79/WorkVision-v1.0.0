package ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicThicknessMeasurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.ResponseUltrasonicThicknessElementMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;

import java.util.List;

public interface UltrasonicThicknessElementMeasurementService {

    ResponseUltrasonicThicknessElementMeasurementDto save(UltrasonicThicknessMeasurementDto measurementDto);

    List<ResponseUltrasonicThicknessElementMeasurementDto> getAll(Long equipmentId);

    void delete(Long id);
}