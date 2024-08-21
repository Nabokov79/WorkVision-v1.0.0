package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementControl.ResponseVisualMeasurementControlDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementControl.VisualMeasurementControlDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.VisualMeasurementControlMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementControl.VisualMeasurementControl;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.VisualMeasurementControlRepository;
import ru.nabokovsg.diagnosedNK.service.norms.DefectService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisualMeasurementControlServiceImpl implements VisualMeasurementControlService {

    private final VisualMeasurementControlRepository repository;
    private final VisualMeasurementControlMapper mapper;
    private final ParameterMeasurementService parameterMeasurementService;
    private final DefectService defectsService;
    private final static String SUITABLE = "Удовл.";
    private final static String NOT_SUITABLE = "Не удовл.";

    @Override
    public ResponseVisualMeasurementControlDto save(VisualMeasurementControlDto defectDto) {
        Defect defect = defectsService.getById(defectDto.getDefectId());
        VisualMeasurementControl vmControl = mapper.mapToVisualMeasurementControl(defectDto, defect, getEstimation(defectDto.isEstimation()));
        vmControl.setParameterMeasurements(parameterMeasurementService.save(defect.getMeasuredParameters()
                , defectDto.getParameterMeasurements()));
        return mapper.mapToResponseVisualMeasuringSurveyDto(vmControl);
    }

    @Override
    public ResponseVisualMeasurementControlDto update(VisualMeasurementControlDto defectDto) {
        Defect defect = defectsService.getById(defectDto.getDefectId());
        VisualMeasurementControl vmControl = mapper.mapToVisualMeasurementControl(defectDto
                                                                            , defect
                                                                            , getEstimation(defectDto.isEstimation()));
        vmControl.setParameterMeasurements(parameterMeasurementService.save(defect.getMeasuredParameters()
                                                                          , defectDto.getParameterMeasurements()));
        return mapper.mapToResponseVisualMeasuringSurveyDto(vmControl);
    }

    @Override
    public List<ResponseVisualMeasurementControlDto> getAll(Long workJournalId) {
        return repository.findAllByWorkJournalId(workJournalId).stream()
                                            .map(mapper::mapToResponseVisualMeasuringSurveyDto)
                                            .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("VisualMeasurementControl with id=%s not found for delete", id));
    }

    private String getEstimation(boolean estimation) {
        if (estimation) {
            return SUITABLE;
        }
        return NOT_SUITABLE;
    }
}