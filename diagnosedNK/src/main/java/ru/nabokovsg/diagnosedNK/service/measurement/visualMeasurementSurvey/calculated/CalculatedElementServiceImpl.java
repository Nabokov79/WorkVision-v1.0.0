package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedElementMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedElementRepository;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class CalculatedElementServiceImpl implements CalculatedElementService {

    private final CalculatedElementRepository repository;
    private final CalculatedElementMapper mapper;

    @Override
    public void addDefect(IdentifiedDefect defect, CalculatedDefect calculatedDefect) {
        CalculatedElement element = get(defect.getEquipmentId(), defect.getElementName());
        if (element.getDefects() == null) {
            element.setDefects(new HashSet<>());
        }
        element.getDefects().add(calculatedDefect);
        save(element);
    }

    @Override
    public void addRepair(CompletedRepair repair, CalculatedRepair calculatedRepair) {
        CalculatedElement element = get(repair.getEquipmentId(), repair.getElementName());
        if (element.getRepairs() == null) {
            element.setRepairs(new HashSet<>());
        }
        element.getRepairs().add(calculatedRepair);
        save(element);
    }

    @Override
    public CalculatedElement get(Long equipmentId, String elementName) {
        CalculatedElement element = repository.findByEquipmentIdAndElementName(equipmentId, elementName);
        if (element == null) {
            element = create(equipmentId, elementName);
        }
        return element;
    }

    private void save(CalculatedElement element) {
        repository.save(element);
    }

    private CalculatedElement create(Long equipmentId, String elementName) {
        return mapper.mapToCalculatedElement(equipmentId, elementName);
    }
}