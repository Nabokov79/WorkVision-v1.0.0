package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.identifiedDefect.ResponseIdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected.IdentifiedDefectMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.QIdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.QParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected.IdentifiedDefectRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentElementService;
import ru.nabokovsg.diagnosedNK.service.measurement.CalculationMeasurementService;
import ru.nabokovsg.diagnosedNK.service.measurement.QueryDSLRequestService;
import ru.nabokovsg.diagnosedNK.service.norms.DefectService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IdentifiedDefectServiceImpl implements IdentifiedDefectService {

    private final IdentifiedDefectRepository repository;
    private final IdentifiedDefectMapper mapper;
    private final EntityManager em;
    private final DefectService defectsService;
    private final ParameterMeasurementService parameterMeasurementService;
    private final EquipmentElementService elementService;
    private final QueryDSLRequestService requestService;
    private final CalculationMeasurementService calculationService;

    @Override
    public ResponseIdentifiedDefectDto save(IdentifiedDefectDto defectDto) {
        IdentifiedDefect identifiedDefect = requestService.getIdentifiedDefect(defectDto);
        if (identifiedDefect == null) {
            Defect defect = defectsService.getById(defectDto.getDefectId());
            EquipmentElement element = elementService.get(defectDto.getElementId());
            identifiedDefect = mapper.mapToIdentifiedDefect(defectDto, defect, element);
            if (defectDto.getPartElementId() != null) {
                EquipmentPartElement partElement = element.getPartsElement()
                        .stream()
                        .collect(Collectors.toMap(EquipmentPartElement::getPartElementId, p -> p))
                        .get(defectDto.getPartElementId());
                mapper.mapWithEquipmentPartElement(identifiedDefect, partElement);
            }
            setQuantity(identifiedDefect, defectDto);
            identifiedDefect = repository.save(identifiedDefect);
            identifiedDefect.setParameterMeasurements(parameterMeasurementService.save(defect.getMeasuredParameters()
                                                                             , defectDto.getParameterMeasurements()));
        } else {
            setQuantity(identifiedDefect, defectDto);
            identifiedDefect = repository.save(identifiedDefect);
        }
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
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Identified defect with id=%s not found for delete", id));
    }

    @Override
    public Double getMaxCorrosionValueByPredicate(UltrasonicThicknessMeasurementDto measurementDto, Long equipmentId) {
        QIdentifiedDefect defect = QIdentifiedDefect.identifiedDefect;
        QParameterMeasurement parameter = QParameterMeasurement.parameterMeasurement;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(defect.equipmentId.eq(equipmentId));
        booleanBuilder.and(defect.elementId.eq(measurementDto.getElementId()));
        booleanBuilder.and(defect.useCalculateThickness.eq(true));
        if (measurementDto.getPartElementId() != null) {
            booleanBuilder.and(defect.partElementId.eq(measurementDto.getPartElementId()));
        }
        booleanBuilder.and(parameter.identifiedDefect.defectId.eq(defect.id));
        Double corrosion = new JPAQueryFactory(em).from(parameter)
                .select(parameter.value)
                .where(booleanBuilder)
                .fetchFirst();
        if (corrosion == null) {
            throw new NotFoundException(String.format("Max corrosion value not found corrosion=%s", corrosion));
        }
        return corrosion;
    }

    private void setQuantity(IdentifiedDefect identifiedDefect, IdentifiedDefectDto defectDto) {
        if (identifiedDefect.getParameterMeasurements() == null) {
            mapper.mapToWithQuantity(identifiedDefect, calculationService.getQuantity(null
                    , defectDto.getParameterMeasurements()));
            return;
        }
        mapper.mapToWithQuantity(identifiedDefect
                , calculationService.getQuantity(identifiedDefect.getParameterMeasurements()
                        , defectDto.getParameterMeasurements()));
    }
}