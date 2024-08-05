package ru.nabokovsg.diagnosedNK.service.measurement.hardnessMeasurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.ResponseElementHardnessMeasurementDto;

import java.util.List;

public interface ElementHardnessMeasurementService {

    ResponseElementHardnessMeasurementDto save(HardnessMeasurementDto measurementDto);

    List<ResponseElementHardnessMeasurementDto> getAll(Long equipmentId);

    void delete(Long id);
}