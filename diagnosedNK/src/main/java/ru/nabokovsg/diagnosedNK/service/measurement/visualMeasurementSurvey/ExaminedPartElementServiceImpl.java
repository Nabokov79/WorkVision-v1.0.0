package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.ExaminedPartElementMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ExaminedPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.QExaminedPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.QVisualMeasuringSurvey;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.ExaminedPartElementRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentPartElementService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExaminedPartElementServiceImpl implements ExaminedPartElementService {

    private final ExaminedPartElementRepository repository;
    private final ExaminedPartElementMapper mapper;
    private final EquipmentPartElementService partElementService;
    private final EntityManager em;

    @Override
    public ExaminedPartElement get(Long equipmentId, Long partElementId) {
        return Objects.requireNonNullElseGet(getByPredicate(equipmentId, partElementId)
                                     , () -> repository.save(mapper.mapToExaminedPartElement(
                                                            partElementService.get(partElementId))));
    }

    @Override
    public ExaminedPartElement addInspection(InspectionDto inspectionDto) {
        ExaminedPartElement partElement = getByPredicate(inspectionDto.getEquipmentId()
                                                       , inspectionDto.getPartElementId());
        if (partElement == null) {
            throw new NotFoundException(
                    String.format("ExaminedPartElement by equipmentId=%s, partElementId=%s not found"
                                                                                    , inspectionDto.getEquipmentId()
                                                                                    , inspectionDto.getPartElementId())
            );
        }
        return repository.save(mapper.mapWithInspection(partElement, inspectionDto));
    }

    private ExaminedPartElement getByPredicate(Long equipmentId, Long partElementId) {
        QVisualMeasuringSurvey vms = QVisualMeasuringSurvey.visualMeasuringSurvey;
        QExaminedPartElement part = QExaminedPartElement.examinedPartElement;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(vms.equipmentId.eq(equipmentId));
        booleanBuilder.and(part.partElementId.eq(partElementId));
        return new JPAQueryFactory(em).from(part)
                .select(part)
                .where(booleanBuilder)
                .fetchOne();
    }
}