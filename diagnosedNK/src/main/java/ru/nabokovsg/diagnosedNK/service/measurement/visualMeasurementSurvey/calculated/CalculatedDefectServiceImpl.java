package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedDefectMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.*;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedDefectRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatedDefectServiceImpl implements CalculatedDefectService {

    private final CalculatedDefectRepository repository;
    private final CalculatedDefectMapper mapper;
    private final EntityManager em;
    private final CalculatedParameterService parameterService;
    private final CalculatedElementService elementService;

    @Override
    public void save(Set<IdentifiedDefect> defects, IdentifiedDefect identifiedDefect, Defect defect) {
        log.info(" ");
        log.info("-------START Calculated  Defect ----------");
        CalculatedDefect calculatedDefect = getByPredicate(identifiedDefect);
        log.info(" ");
        log.info("CalculatedDefect calculatedDefect = {}", calculatedDefect);
        if (calculatedDefect == null) {
            CalculatedElement element = elementService.get(identifiedDefect.getEquipmentId(), identifiedDefect.getElementName());
            calculatedDefect = repository.save(mapper.mapToCalculatedDefect(identifiedDefect, element));
            elementService.addDefect(element, calculatedDefect);
            log.info("CalculatedDefect calculatedDefect before save = {}", calculatedDefect);
            log.info("CalculatedElement element = {}", calculatedDefect);
        }
        log.info(" ");
        parameterService.save(new CalculatedParameterData.Builder()
                                                         .defects(defects)
                                                         .defect(calculatedDefect)
                                                         .calculationType(defect.getCalculation())
                                                         .build());
        log.info(" ");
        log.info("-------START Calculated  Defect ----------");
    }

    @Override
    public void update(Set<IdentifiedDefect> defects, IdentifiedDefect identifiedDefect, Defect defect) {
        CalculatedDefect calculatedDefect = getByPredicate(identifiedDefect);
        if (calculatedDefect == null) {
            throw new NotFoundException(
                    String.format("Calculated defect=%s not found for update", identifiedDefect.getDefectName()));
        }
        parameterService.save(new CalculatedParameterData.Builder()
                                                         .defects(defects)
                                                         .defect(calculatedDefect)
                                                         .calculationType(defect.getCalculation())
                                                         .build());
    }

    private CalculatedDefect getByPredicate(IdentifiedDefect identifiedDefect) {
        QCalculatedDefect defect = QCalculatedDefect.calculatedDefect;
        QCalculatedElement element = QCalculatedElement.calculatedElement;
        QCalculatedPartElement partElement = QCalculatedPartElement.calculatedPartElement;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(element.equipmentId.eq(identifiedDefect.getEquipmentId()));
        builder.and(defect.defectId.eq(identifiedDefect.getDefectId()));
        builder.and(element.elementName.eq(identifiedDefect.getElementName()));
        if (identifiedDefect.getPartElementName() != null) {
            builder.and(partElement.partElementName.eq(identifiedDefect.getPartElementName()));
        }
        return new JPAQueryFactory(em).from(defect)
                .select(defect)
                .where(builder)
                .innerJoin(defect.element, element)
                .fetchOne();
    }
}