package ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicControl;

import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicControl.ResponseUltrasonicMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicControl.UltrasonicMeasurementDto;

import java.util.List;

public interface UltrasonicControlService {

   ResponseUltrasonicMeasurementDto save(UltrasonicMeasurementDto measurementDto);

   ResponseUltrasonicMeasurementDto update(UltrasonicMeasurementDto measurementDto);

   List<ResponseUltrasonicMeasurementDto> getAll(Long workJournalId);

   void delete(Long id);
}