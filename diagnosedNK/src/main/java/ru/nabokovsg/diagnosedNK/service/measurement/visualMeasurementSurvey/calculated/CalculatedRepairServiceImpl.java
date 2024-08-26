package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedRepairMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.*;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.norms.ElementRepair;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedRepairRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CalculatedRepairServiceImpl implements CalculatedRepairService {

    private final CalculatedRepairRepository repository;
    private final CalculatedRepairMapper mapper;
    private final EntityManager em;
    private final CalculatedParameterService parameterService;
    private final CalculatedElementService elementService;

    @Override
    public void save(Set<CompletedRepair> repairs, CompletedRepair repair, ElementRepair elementRepair) {
        CalculatedRepair calculatedRepair = getByPredicate(repair);
        if (calculatedRepair == null) {
            CalculatedElement element = elementService.get(repair.getEquipmentId(), repair.getElementName());
            calculatedRepair = repository.save(mapper.mapToCalculatedDefect(repair, element));
            elementService.addRepair(element, calculatedRepair);
        }
        parameterService.saveForRepair(repairs, calculatedRepair, elementRepair.getCalculation());
    }

    @Override
    public void update(Set<CompletedRepair> repairs, CompletedRepair repair, ElementRepair elementRepair) {
        CalculatedRepair calculatedRepair = getByPredicate(repair);
        if (calculatedRepair == null) {
            throw new NotFoundException(
                    String.format("Calculated repair=%s not found for update", repair.getRepairName()));
        }
        parameterService.updateForRepair(repairs, calculatedRepair, elementRepair.getCalculation());
    }

    private CalculatedRepair getByPredicate(CompletedRepair completedRepair) {
        QCalculatedRepair repair = QCalculatedRepair.calculatedRepair;
        QCalculatedElement element = QCalculatedElement.calculatedElement;
        QCalculatedPartElement partElement = QCalculatedPartElement.calculatedPartElement;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(element.equipmentId.eq(completedRepair.getEquipmentId()));
        builder.and(repair.repairId.eq(completedRepair.getRepairId()));
        builder.and(element.elementName.eq(completedRepair.getElementName()));
        if (completedRepair.getPartElementName() != null) {
            builder.and(partElement.partElementName.eq(completedRepair.getPartElementName()));
        }
        return new JPAQueryFactory(em).from(repair)
                .select(repair)
                .where(builder)
                .innerJoin(repair.element, element)
                .fetchOne();
    }
}