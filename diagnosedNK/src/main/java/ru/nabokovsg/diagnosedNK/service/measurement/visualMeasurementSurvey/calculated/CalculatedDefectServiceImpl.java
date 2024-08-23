package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedDefectMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.*;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedDefectRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculatedDefectServiceImpl implements CalculatedDefectService {

    private final CalculatedDefectRepository repository;
    private final CalculatedDefectMapper mapper;
    private final EntityManager em;
    private final CalculatedParameterService parameterService;

    @Override
    public void save(Set<IdentifiedDefect> defects
            , IdentifiedDefect identifiedDefect
            , Defect defect
            , Set<MeasuredParameter> measuredParameters) {
        CalculatedDefect calculatedDefect = getByPredicate(identifiedDefect);
        if (calculatedDefect == null) {
            calculatedDefect = repository.save(mapper.mapToCalculatedDefect(identifiedDefect));
        }
        parameterService.save();
    }
}

    @Override
    public void update(List<IdentifiedDefect> defects
            , IdentifiedDefect defect
            , CalculationDefectOrRepair calculation
            , Set<MeasuredParameter> measuredParameters) {

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