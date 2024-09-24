package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import ru.nabokovsg.diagnosedNK.dto.measurement.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.identifiedDefect.ResponseIdentifiedDefectDto;

import java.util.List;

public interface IdentifiedDefectService {

    ResponseIdentifiedDefectDto save(IdentifiedDefectDto defectDto);

    ResponseIdentifiedDefectDto update(IdentifiedDefectDto defectDto);

    List<ResponseIdentifiedDefectDto> getAll(Long equipmentId);

    void delete(Long id, Integer quantity);
}