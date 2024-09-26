package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.identifiedDefect.ResponseIdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected.IdentifiedDefectMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.enums.TypeVMSData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.builders.ParameterMeasurementBuilder;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected.IdentifiedDefectRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentElementService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.CalculatedDefectService;
import ru.nabokovsg.diagnosedNK.service.norms.DefectService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IdentifiedDefectServiceImpl implements IdentifiedDefectService {

    private final IdentifiedDefectRepository repository;
    private final IdentifiedDefectMapper mapper;
    private final DefectService defectsService;
    private final ParameterMeasurementService parameterService;
    private final EquipmentElementService elementService;
    private final CalculatedDefectService calculatedDefectService;

    @Override
    public ResponseIdentifiedDefectDto save(IdentifiedDefectDto defectDto) {
        Defect defect = defectsService.getById(defectDto.getDefectId());
        IdentifiedDefect identifiedDefect = repository.save(createIdentifiedDefect(defectDto, defect));
        Map<Long, IdentifiedDefect> defects = getAllByPredicate(identifiedDefect);

        identifiedDefect.setParameterMeasurements(
                parameterService.save(new ParameterMeasurementBuilder.Builder()
                        .typeData(TypeVMSData.DEFECT)
                        .defect(identifiedDefect)
                        .measuredParameter(defect.getMeasuredParameters())
                        .parameterMeasurements(defectDto.getParameterMeasurements())
                        .build()));
        defects.put(identifiedDefect.getId(), identifiedDefect);
        calculatedDefectService.save(new HashSet<>(defects.values()), identifiedDefect, defect);
        return mapper.mapToResponseIdentifiedDefectDto(identifiedDefect);
    }

    @Override
    public ResponseIdentifiedDefectDto update(IdentifiedDefectDto defectDto) {
        Defect defect = defectsService.getById(defectDto.getDefectId());
        Map<Long, IdentifiedDefect> defects = getAllByPredicate(createIdentifiedDefect(defectDto, defect));
        IdentifiedDefect identifiedDefect = searchDuplicate(defectDto, defects);
        if (identifiedDefect != null) {
            defects.remove(defectDto.getId());
            delete(defectDto.getId());
            identifiedDefect.setParameterMeasurements(
                    parameterService.save(new ParameterMeasurementBuilder.Builder()
                            .typeData(TypeVMSData.DEFECT)
                            .defect(identifiedDefect)
                            .measuredParameter(defect.getMeasuredParameters())
                            .parameterMeasurements(defectDto.getParameterMeasurements())
                            .build()));
        } else {
            identifiedDefect = defects.get(defectDto.getId());
            if (identifiedDefect == null) {
                throw new NotFoundException(String.format("Identified defect with id=%s not found for update", defectDto.getDefectId()));
            }
            identifiedDefect.setParameterMeasurements(parameterService.update(identifiedDefect.getParameterMeasurements()
                    , defectDto.getParameterMeasurements()));
            defects.put(identifiedDefect.getId(), identifiedDefect);
        }
        calculatedDefectService.update(new HashSet<>(defects.values()), identifiedDefect, defect);
        return mapper.mapToResponseIdentifiedDefectDto(identifiedDefect);
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
        parameterService.deleteAll(defect.getParameterMeasurements());
        repository.deleteById(defect.getId());
        calculatedDefectService.update(new HashSet<>(getAllByPredicate(defect).values()), defect, defectsService.getById(defect.getDefectId()));
    }

    private IdentifiedDefect get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Identified defect with id=%s not found", id)));
    }

    private IdentifiedDefect searchDuplicate(IdentifiedDefectDto defectDto, Map<Long, IdentifiedDefect> defects) {
        Map<Long, ParameterMeasurementDto> parametersDto = defectDto.getParameterMeasurements()
                                            .stream()
                                            .collect(Collectors.toMap(ParameterMeasurementDto::getParameterId, p -> p));
        boolean flag;
        for (IdentifiedDefect defect : defects.values()) {
            flag = parameterService.searchParameterDuplicate(defect.getParameterMeasurements(), parametersDto);
            if (flag) {
                return defect;
            }
        }
        return null;
    }

    private Map<Long, IdentifiedDefect> getAllByPredicate(IdentifiedDefect defectDto) {
        Set<IdentifiedDefect> defects;
        if (defectDto.getPartElementId() != null) {
            defects = repository.findAllByEquipmentIdAndElementIdAndPartElementIdAndDefectId(defectDto.getEquipmentId(),defectDto.getElementId(), defectDto.getPartElementId(), defectDto.getDefectId());
        } else {
            defects = repository.findAllByEquipmentIdAndElementIdAndDefectId(defectDto.getEquipmentId(), defectDto.getElementId(), defectDto.getDefectId());
        }
        return defects.stream()
                      .collect(Collectors.toMap(IdentifiedDefect::getId, d -> d));
    }

    private IdentifiedDefect createIdentifiedDefect(IdentifiedDefectDto defectDto, Defect defect) {
        EquipmentElement element = elementService.get(defectDto.getElementId());
        IdentifiedDefect identifiedDefect = mapper.mapToIdentifiedDefect(defectDto, defect, element);
        if (defectDto.getPartElementId() != null) {
            EquipmentPartElement partElement = element.getPartsElement()
                    .stream()
                    .collect(Collectors.toMap(EquipmentPartElement::getPartElementId, p -> p))
                    .get(defectDto.getPartElementId());
            mapper.mapWithEquipmentPartElement(identifiedDefect, partElement);
        }
        return identifiedDefect;
    }
}