package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.identifiedDefect.ResponseIdentifiedDefectDto;

import java.util.List;

public interface IdentifiedDefectService {

    ResponseIdentifiedDefectDto save(IdentifiedDefectDto defectDto);

    List<ResponseIdentifiedDefectDto> getAll(Long equipmentId);

   void delete(Long id);
    Double getMaxCorrosionValueByPredicate(UltrasonicThicknessMeasurementDto measurementDto, Long equipmentId);
}