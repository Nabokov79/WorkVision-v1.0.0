package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedDefectMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.QCalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.QCalculatedVMSurvey;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedDefectRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculatedDefectServiceImpl implements CalculatedDefectService {

    private final CalculatedDefectRepository repository;
    private final CalculatedDefectMapper mapper;
    private final EntityManager em;
    private final CalculatedParameterService parameterService;

    @Override
    public void save(IdentifiedDefect identifiedDefect, Defect defect) {
        CalculatedDefect calculatedDefect = getByPredicate(identifiedDefect);
        if (calculatedDefect == null) {
            calculatedDefect = repository.save(mapper.mapToCalculatedDefect(identifiedDefect));
        }
        switch (defect.getCalculation()) {
            case SQUARE -> ;
            case QUANTITY -> ;
            case NO_ACTION -> ;
        }

    }

    private CalculatedDefect getByPredicate(IdentifiedDefect identifiedDefect) {
        QCalculatedDefect defect = QCalculatedDefect.calculatedDefect;
        QCalculatedVMSurvey visualMeasuringSurvey = QCalculatedVMSurvey.calculatedVMSurvey;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(visualMeasuringSurvey.equipmentId.eq(identifiedDefect.getEquipmentId()));
        builder.and(defect.defectId.eq(identifiedDefect.getDefectId()));
        builder.and(defect.element.elementName.eq(identifiedDefect.getElementName()));
        if (identifiedDefect.getPartElementName() != null) {
            builder.and(defect.partElement.partElementName.eq(identifiedDefect.getPartElementName()));
        }
        return new JPAQueryFactory(em).from(defect)
                .select(defect)
                .where(builder)
                .fetchOne();
    }
}