package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
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
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.QIdentifiedDefect;
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
    private final EntityManager em;

    @Override
    public ResponseIdentifiedDefectDto save(IdentifiedDefectDto defectDto) {
        Defect defect = defectsService.getById(defectDto.getDefectId());
        Set<IdentifiedDefect> defects = getAllIdByPredicate(defectDto);
        IdentifiedDefect defectDb = searchDuplicate(defectDto, defect, defects);
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
            defectDb.setParameterMeasurements(parameterService.saveForIdentifiedDefect(defectDb
                                                                            , defect.getMeasuredParameters()
                                                                            , defectDto.getParameterMeasurements()));
        }
        calculatedDefectService.save(defects, defectDb, defect);
        return mapper.mapToResponseIdentifiedDefectDto(defectDb);
    }

    @Override
    public ResponseIdentifiedDefectDto update(IdentifiedDefectDto defectDto) {
        Defect defect = defectsService.getById(defectDto.getDefectId());
        Map<Long, IdentifiedDefect> defects = getAllIdByPredicate(defectDto).stream()
                .collect(Collectors.toMap(IdentifiedDefect::getId, d -> d));
        IdentifiedDefect defectDb = searchDuplicate(defectDto, defect, new HashSet<>(defects.values()));
        if (defectDb == null) {
            EquipmentElement element = elementService.get(defectDto.getElementId());
            defectDb = defects.get(defectDto.getId());
            if (defectDto.getPartElementId() != null) {
                EquipmentPartElement partElement = element.getPartsElement()
                        .stream()
                        .collect(Collectors.toMap(EquipmentPartElement::getPartElementId, p -> p))
                        .get(defectDto.getPartElementId());
                mapper.mapWithEquipmentPartElement(defectDb, partElement);
                defectDb = repository.save(defectDb);
                defectDb.setParameterMeasurements(parameterService.update(defectDb.getParameterMeasurements()
                                                                        , defectDto.getParameterMeasurements()));
            }
            defects.put(defectDb.getId(), defectDb);
        } else {
            delete(defectDto.getId());
        }
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
        parameterService.deleteAll(defect.getParameterMeasurements());
        repository.deleteById(id);
    }

    private IdentifiedDefect get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Identified defect with id=%s not found", id)));
    }

    private IdentifiedDefect searchDuplicate(IdentifiedDefectDto defectDto, Defect defect, Set<IdentifiedDefect> defects) {
        Map<Long, IdentifiedDefect> defectsDb = defects.stream()
                                                        .collect(Collectors.toMap(IdentifiedDefect::getId, d -> d));
        Map<String, ParameterMeasurement> parameters = parameterService.map(defectDto.getParameterMeasurements()
                                                                          , defect.getMeasuredParameters())
                .stream()
                .collect(Collectors.toMap(ParameterMeasurement::getParameterName, p -> p));
        boolean flag;
        for (IdentifiedDefect identifiedDefect : defectsDb.values()) {
            flag = parameterService.searchParameterDuplicate(identifiedDefect.getParameterMeasurements()
                                                           , parameters);
            if (flag) {
                return identifiedDefect;
            }
        }
        return null;
    }

    private Set<IdentifiedDefect> getAllIdByPredicate(IdentifiedDefectDto defectDto) {
        QIdentifiedDefect defect = QIdentifiedDefect.identifiedDefect;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(defect.equipmentId.eq(defectDto.getEquipmentId()));
        builder.and(defect.defectId.eq(defectDto.getDefectId()));
        builder.and(defect.elementId.eq(defectDto.getElementId()));
        if (defectDto.getPartElementId() != null) {
            builder.and(defect.partElementId.eq(defectDto.getPartElementId()));
        }
        return new HashSet<>(new JPAQueryFactory(em).from(defect)
                                                    .select(defect)
                                                    .where(builder)
                                                    .fetch());
    }
}