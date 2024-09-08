package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.identifiedDefect.ResponseIdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected.IdentifiedDefectMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected.IdentifiedDefectRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentElementService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.CalculatedDefectService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.common.QueryDSLRequestService;
import ru.nabokovsg.diagnosedNK.service.norms.DefectService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IdentifiedDefectServiceImpl implements IdentifiedDefectService {

    private final IdentifiedDefectRepository repository;
    private final IdentifiedDefectMapper mapper;
    private final DefectService defectsService;
    private final ParameterMeasurementService parameterMeasurementService;
    private final EquipmentElementService elementService;
    private final QueryDSLRequestService requestService;
    private final CalculatedDefectService calculatedDefectService;

    @Override
    public ResponseIdentifiedDefectDto save(IdentifiedDefectDto defectDto) {
        Defect defect = defectsService.getById(defectDto.getDefectId());
        IdentifiedDefect defectDb = searchDuplicate(defectDto, defect);
        if (defectDb == null) {
            EquipmentElement element = elementService.get(defectDto.getElementId());
            defectDb = mapper.mapToIdentifiedDefect(defectDto, defect, element);
            if (defectDto.getPartElementId() != null) {
                EquipmentPartElement partElement = element.getPartsElement()
                        .stream()
                        .collect(Collectors.toMap(EquipmentPartElement::getPartElementId, p -> p))
                        .get(defectDto.getPartElementId());
                mapper.mapWithEquipmentPartElement(defectDb, partElement);
            }
            defectDb = repository.save(defectDb);
            defectDb.setParameterMeasurements(
                    parameterMeasurementService.saveForIdentifiedDefect(defectDb
                            , defect.getMeasuredParameters()
                            , defectDto.getParameterMeasurements()));
        }
        calculatedDefectService.save(requestService.getAllIdentifiedDefect(defectDto), defectDb, defect);
        return mapper.mapToResponseIdentifiedDefectDto(defectDb);
    }

    @Override
    public ResponseIdentifiedDefectDto update(IdentifiedDefectDto defectDto) {
        Map<Long, IdentifiedDefect> defects = requestService.getAllIdentifiedDefect(defectDto)
                .stream()
                .collect(Collectors.toMap(IdentifiedDefect::getId, d -> d));
        IdentifiedDefect defectDb = requestService.getIdentifiedDefect(defectDto);
        Defect defect = defectsService.getById(defectDto.getDefectId());
        if (defectDb == null) {
            defectDb = defects.get(defectDto.getId());
            defectDb.setParameterMeasurements(parameterMeasurementService.update(defectDb.getParameterMeasurements()
                    ,  defectDto.getParameterMeasurements()));
        } else {
            delete(defectDto.getId());
            defects.remove(defectDto.getId());
        }
        defects.put(defectDb.getId(), defectDb);
        calculatedDefectService.update(new HashSet<>(defects.values()), defectDb, defect);
        return mapper.mapToResponseIdentifiedDefectDto(repository.save(defectDb));
    }

    @Override
    public List<ResponseIdentifiedDefectDto> getAll(Long equipmentId) {
        return repository.findAllByEquipmentId(equipmentId)
                .stream()
                .map(mapper::mapToResponseIdentifiedDefectDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        IdentifiedDefect defect = get(id);
        parameterMeasurementService.deleteAll(defect.getParameterMeasurements());
        repository.deleteById(id);
    }

    private IdentifiedDefect get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Identified defect with id=%s not found", id)));
    }

    private IdentifiedDefect searchDuplicate(IdentifiedDefectDto defectDto, Defect defect) {
        Map<Long, IdentifiedDefect> defects = requestService.getAllIdentifiedDefect(defectDto).stream().collect(Collectors.toMap(IdentifiedDefect::getId, d -> d));
        Map<String, ParameterMeasurement> parameters = parameterMeasurementService.map(defectDto.getParameterMeasurements(), defect.getMeasuredParameters())
                .stream()
                .collect(Collectors.toMap(ParameterMeasurement::getParameterName, p -> p));
        boolean flag;
        for (IdentifiedDefect identifiedDefect : defects.values()) {
            flag = parameterMeasurementService.searchParameterDuplicate(identifiedDefect.getParameterMeasurements()
                                                , parameters);
            if (flag) {
                return identifiedDefect;
            }
        }
        return null;
    }
}