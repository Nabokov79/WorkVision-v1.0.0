package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedElementMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedRepair;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedElementRepository;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class CalculatedElementServiceImpl implements CalculatedElementService {

    private final CalculatedElementRepository repository;
    private final CalculatedElementMapper mapper;

    @Override
    public void addDefect(CalculatedElement element, CalculatedDefect calculatedDefect) {
        if (element.getDefects() == null) {
            element.setDefects(new HashSet<>());
        }
        element.getDefects().add(calculatedDefect);
        save(element);
    }

    @Override
    public void addRepair(CalculatedElement element, CalculatedRepair calculatedRepair) {
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
            return save(create(equipmentId, elementName));
        }
        return element;
    }

    private CalculatedElement save(CalculatedElement element) {
        return repository.save(element);
    }

    private CalculatedElement create(Long equipmentId, String elementName) {
        return mapper.mapToCalculatedElement(equipmentId, elementName);
    }
}