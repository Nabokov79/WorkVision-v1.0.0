package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.identifiedDefect.ResponseIdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.IdentifiedDefectMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.QIdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.QParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.QVisualMeasuringSurvey;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.IdentifiedDefectRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.QueryDSLRequestService;
import ru.nabokovsg.diagnosedNK.service.norms.DefectService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IdentifiedDefectServiceImpl implements IdentifiedDefectService {

    private final IdentifiedDefectRepository repository;
    private final IdentifiedDefectMapper mapper;
    private final EntityManager em;
    private final DefectService defectsService;
    private final ParameterMeasurementService parameterMeasurementService;
    private final VisualMeasuringSurveyService visualMeasuringSurveyService;
    private final ExaminedPartElementService examinedPartElementService;
    private final QueryDSLRequestService requestService;

    @Override
    public ResponseIdentifiedDefectDto save(IdentifiedDefectDto defectDto) {
        IdentifiedDefect identifiedDefect = requestService.getIdentifiedDefect(defectDto);
        Defect defect = defectsService.getById(defectDto.getDefectId());
        if (identifiedDefect == null) {
            identifiedDefect = mapper.mapToIdentifiedDefect(defectDto, defect);
            if (defectDto.getPartElementId() != null) {
                identifiedDefect = mapper.mapWithExaminedPartElement(identifiedDefect
                                                        , examinedPartElementService.get(defectDto.getEquipmentId()
                                                                                       , defectDto.getElementId()));
            } else {
                identifiedDefect = mapper.mapWithVisualMeasuringSurvey(identifiedDefect
                                                 , visualMeasuringSurveyService.get(defectDto.getEquipmentId()
                                                                                  , defectDto.getElementId()));
            }
            identifiedDefect = repository.save(identifiedDefect);
        }
        identifiedDefect.setParameterMeasurements(parameterMeasurementService.save(defect.getCalculation()
                                                                           , defect.getMeasuredParameters()
                                                                           , identifiedDefect.getParameterMeasurements()
                                                                           , defectDto.getParameterMeasurements()));
        return mapper.mapToResponseIdentifiedDefectDto(identifiedDefect);
    }

    @Override
    public List<ResponseIdentifiedDefectDto> getAll(Long equipmentId) {
        QIdentifiedDefect defect = QIdentifiedDefect.identifiedDefect;
        QVisualMeasuringSurvey vms = QVisualMeasuringSurvey.visualMeasuringSurvey;
        return new JPAQueryFactory(em).from(defect)
                                      .select(defect)
                                      .where(vms.equipmentId.eq(equipmentId))
                                      .fetch()
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
        QVisualMeasuringSurvey vms = QVisualMeasuringSurvey.visualMeasuringSurvey;
        QParameterMeasurement parameter = QParameterMeasurement.parameterMeasurement;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(vms.equipmentId.eq(equipmentId));
        booleanBuilder.and(vms.elementId.eq(measurementDto.getElementId()));
        booleanBuilder.and(defect.useCalculateThickness.eq(true));
        if (measurementDto.getPartElementId() != null) {
            booleanBuilder.and(defect.examinedPartElement.partElementId.eq(measurementDto.getPartElementId()));
        }
        booleanBuilder.and(parameter.identifiedDefect.defectId.eq(defect.id));
        Double corrosion = new JPAQueryFactory(em).from(parameter)
                .select(parameter.minValue)
                .where(booleanBuilder)
                .fetchFirst();
        if (corrosion == null) {
            throw new NotFoundException(String.format("Max corrosion value not found corrosion=%s", corrosion));
        }
        return corrosion;
    }
}