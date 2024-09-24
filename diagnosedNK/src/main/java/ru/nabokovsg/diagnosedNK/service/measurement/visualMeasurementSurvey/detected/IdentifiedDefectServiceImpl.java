package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.identifiedDefect.ResponseIdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected.IdentifiedDefectMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.builders.ParameterMeasurementBuilder;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.QIdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected.IdentifiedDefectRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentElementService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.CalculatedDefectService;
import ru.nabokovsg.diagnosedNK.service.norms.DefectService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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
        Map<Long, IdentifiedDefect> defects = getAllByPredicate(defectDto);
        IdentifiedDefect defectDb = searchDuplicate(defectDto, defects);
        if (defectDb == null) {
            defectDb = repository.save(createIdentifiedDefect(defectDto, defect));
            defectDb.setParameterMeasurements(
                    parameterService.save(new ParameterMeasurementBuilder.Builder()
                            .defect(defectDb)
                            .measuredParameter(defect.getMeasuredParameters())
                            .parameterMeasurements(defectDto.getParameterMeasurements())
                            .build()));
            defects.put(defectDb.getId(), defectDb);
        }
        calculatedDefectService.save(new HashSet<>(defects.values()), defectDb, defect);
        return mapper.mapToResponseIdentifiedDefectDto(defectDb);
    }

    @Override
    public ResponseIdentifiedDefectDto update(IdentifiedDefectDto defectDto) {
        Defect defect = defectsService.getById(defectDto.getDefectId());
        Map<Long, IdentifiedDefect> defects = getAllByPredicate(defectDto);
        IdentifiedDefect identifiedDefect = getIdentifiedDefect(defectDto, defects);
        identifiedDefect.setParameterMeasurements(parameterService.update(identifiedDefect.getParameterMeasurements()
                , defectDto.getParameterMeasurements()));
        defects.put(identifiedDefect.getId(), identifiedDefect);
        calculatedDefectService.update(new HashSet<>(defects.values()), identifiedDefect, defect);
        return mapper.mapToResponseIdentifiedDefectDto(identifiedDefect);
    }

    private IdentifiedDefect getIdentifiedDefect(IdentifiedDefectDto defectDto, Map<Long, IdentifiedDefect> defects) {
        if (defects.size() < 1) {
            throw new NotFoundException(String.format("IdentifiedDefect with id=%s not found for update", defectDto.getId()));
        }
        if (defects.size() > 1) {
            IdentifiedDefect identifiedDefect = searchDuplicate(defectDto, defects);
            if (identifiedDefect != null) {
                deleteById(defects.get(defectDto.getId()));
            }
            return identifiedDefect;
        } else {
            return defects.get(defectDto.getId());
        }
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
        deleteById(get(id));
    }

    private void deleteById(IdentifiedDefect defect) {
        parameterService.deleteAll(defect.getParameterMeasurements());
        repository.deleteById(defect.getId());
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

    private Map<Long, IdentifiedDefect> getAllByPredicate(IdentifiedDefectDto defectDto) {
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
                .fetch())
                .stream()
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