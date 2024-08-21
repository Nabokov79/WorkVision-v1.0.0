package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementControl.ResponseVisualMeasurementControlDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementControl.VisualMeasurementControlDto;

import java.util.List;
public interface VisualMeasurementControlService {

    ResponseVisualMeasurementControlDto save(VisualMeasurementControlDto defectDto);

    ResponseVisualMeasurementControlDto update(VisualMeasurementControlDto defectDto);

    List<ResponseVisualMeasurementControlDto> getAll(Long workJournalId);

    void delete(Long id);
}