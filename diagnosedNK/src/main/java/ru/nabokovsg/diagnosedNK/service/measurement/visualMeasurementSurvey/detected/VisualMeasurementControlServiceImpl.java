package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.visualMeasuringSurvey.ResponseCalculatedVMSurveyDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.VisualMeasurementControlMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.VisualMeasurementControl;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.VisualMeasurementControlRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentElementService;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VisualMeasurementControlServiceImpl implements VisualMeasurementControlService {

    private final VisualMeasurementControlRepository repository;
    private final VisualMeasurementControlMapper mapper;
    private final EquipmentElementService elementService;

    @Override
    public List<ResponseCalculatedVMSurveyDto> getAll(Long equipmentId) {
        return getByEquipmentId(equipmentId).stream()
                                                .map(mapper::mapToResponseVisualMeasuringSurveyDto)
                                                .toList();
    }

    @Override
    public VisualMeasurementControl get(Long equipmentId, Long elementId) {
        return Objects.requireNonNullElseGet(
                        repository.findByEquipmentIdAndElementId(equipmentId, elementId)
                , () -> repository.save(mapper.mapToVisualMeasuringSurvey(equipmentId, elementService.get(elementId))));
    }

    @Override
    public Set<VisualMeasurementControl> getByEquipmentId(Long equipmentId) {
        Set<VisualMeasurementControl> visualMeasuringSurvey = repository.findAllByEquipmentId(equipmentId);
        if (visualMeasuringSurvey == null) {
            throw new NotFoundException(
                    String.format("VisualMeasuringSurvey by equipmentId=%s not found", equipmentId));
        }
        return visualMeasuringSurvey;
    }

    @Override
    public VisualMeasurementControl addInspection(InspectionDto inspectionDto) {
        VisualMeasurementControl visualMeasuringSurvey =
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