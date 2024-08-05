package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.visualMeasuringSurvey.ResponseVisualMeasuringSurveyDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.VisualMeasuringSurveyMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.VisualMeasuringSurvey;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.VisualMeasuringSurveyRepository;
import ru.nabokovsg.diagnosedNK.service.diagnosticEquipmentData.EquipmentStandardSizeService;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VisualMeasuringSurveyServiceImpl implements VisualMeasuringSurveyService {

    private final VisualMeasuringSurveyRepository repository;
    private final VisualMeasuringSurveyMapper mapper;
    private final EquipmentStandardSizeService standardSizeService;

    @Override
    public List<ResponseVisualMeasuringSurveyDto> getAll(Long equipmentId) {
        return getByEquipmentId(equipmentId).stream()
                                                .map(mapper::mapToResponseVisualMeasuringSurveyDto)
                                                .toList();
    }

    @Override
    public VisualMeasuringSurvey get(Long equipmentId, Long elementId) {
        return Objects.requireNonNullElseGet(
                        repository.findByEquipmentIdAndElementId(equipmentId, elementId)
                , () -> repository.save(mapper.mapToVisualMeasuringSurvey(equipmentId
                                                                    , standardSizeService.getByElementId(elementId))));
    }

    @Override
    public Set<VisualMeasuringSurvey> getByEquipmentId(Long equipmentId) {
        Set<VisualMeasuringSurvey> visualMeasuringSurvey = repository.findAllByEquipmentId(equipmentId);
        if (visualMeasuringSurvey == null) {
            throw new NotFoundException(
                    String.format("VisualMeasuringSurvey by equipmentId=%s not found", equipmentId));
        }
        return visualMeasuringSurvey;
    }

    @Override
    public VisualMeasuringSurvey addInspection(InspectionDto inspectionDto) {
        VisualMeasuringSurvey visualMeasuringSurvey =
                repository.findByEquipmentIdAndElementId(inspectionDto.getEquipmentId(), inspectionDto.getElementId());
        if (visualMeasuringSurvey == null) {
            throw new NotFoundException(
                   String.format("VisualMeasuringSurvey by equipmentId=%s, partElementId=%s not found"
                                                                                    , inspectionDto.getEquipmentId()
                                                                                    , inspectionDto.getPartElementId())
            );
        }
        return repository.save(mapper.mapWithInspection(visualMeasuringSurvey, inspectionDto));
    }
}