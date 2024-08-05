package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.inspection.ResponseInspectionDto;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.InspectionMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectionServiceImpl implements InspectionService {

    private final InspectionMapper mapper;
    private final VisualMeasuringSurveyService vmSurveyService;
    private final ExaminedPartElementService partElementService;

    @Override
    public ResponseInspectionDto save(InspectionDto inspectionDto) {
        if (inspectionDto.getPartElementId() != null) {
            return mapper.mapToResponseInspectionDto(inspectionDto
                                                     , partElementService.addInspection(inspectionDto).getInspection());
        }
        return mapper.mapToResponseInspectionDto(inspectionDto
                                              , vmSurveyService.addInspection(inspectionDto).getInspection());
    }

    @Override
    public List<ResponseInspectionDto> getAll(Long equipmentId) {
        List<ResponseInspectionDto> inspections = new ArrayList<>();
        vmSurveyService.getByEquipmentId(equipmentId)
                .forEach(vm -> {
                    if (vm.getExaminedPartElements().isEmpty()) {
                         inspections.add(mapper.mapToResponseInspectionDto(vm));
                    }
                    inspections.addAll(vm.getExaminedPartElements().stream()
                                                                   .map(p -> mapper.mapToResponseInspectionDto(vm, p))
                                                                   .toList());
                });
        return inspections;
    }
}