package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.visualMeasuringSurvey.ResponseCalculatedVMSurveyDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedVMSurveyMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedVMSurvey;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedVMSurveyRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentElementService;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CalculatedVMSurveyServiceImpl implements CalculatedVMSurveyService {

    private final CalculatedVMSurveyRepository repository;
    private final CalculatedVMSurveyMapper mapper;
    private final EquipmentElementService elementService;

    @Override
    public List<ResponseCalculatedVMSurveyDto> getAll(Long equipmentId) {
        return getByEquipmentId(equipmentId).stream()
                                                .map(mapper::mapToResponseVisualMeasuringSurveyDto)
                                                .toList();
    }

    @Override
    public CalculatedVMSurvey get(Long equipmentId, Long elementId) {
        return Objects.requireNonNullElseGet(
                        repository.findByEquipmentIdAndElementId(equipmentId, elementId)
                , () -> repository.save(mapper.mapToVisualMeasuringSurvey(equipmentId, elementService.get(elementId))));
    }

    @Override
    public Set<CalculatedVMSurvey> getByEquipmentId(Long equipmentId) {
        Set<CalculatedVMSurvey> visualMeasuringSurvey = repository.findAllByEquipmentId(equipmentId);
        if (visualMeasuringSurvey == null) {
            throw new NotFoundException(
                    String.format("VisualMeasuringSurvey by equipmentId=%s not found", equipmentId));
        }
        return visualMeasuringSurvey;
    }

    @Override
    public CalculatedVMSurvey addInspection(InspectionDto inspectionDto) {
        CalculatedVMSurvey visualMeasuringSurvey =
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