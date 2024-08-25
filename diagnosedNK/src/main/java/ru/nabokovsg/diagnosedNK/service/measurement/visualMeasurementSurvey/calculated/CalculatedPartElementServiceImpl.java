package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedPartElementMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedPartElementRepository;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculatedPartElementServiceImpl implements CalculatedPartElementService {

    private final CalculatedPartElementRepository repository;
    private final CalculatedPartElementMapper mapper;
    private final CalculatedElementService elementService;

    @Override
    public void addDefect(IdentifiedDefect defect, CalculatedDefect calculatedDefect) {
        CalculatedElement element = elementService.get(defect.getEquipmentId(), defect.getElementName());
        CalculatedPartElement partElement = get(element, defect.getPartElementName());
        if (partElement.getDefects() == null) {
            partElement.setDefects(new HashSet<>());
        }
        partElement.getDefects().add(calculatedDefect);
        save(partElement);
    }

    @Override
    public void addRepair(CompletedRepair repair, CalculatedRepair calculatedRepair) {
        CalculatedElement element = elementService.get(repair.getEquipmentId(), repair.getElementName());
        CalculatedPartElement partElement = get(element, repair.getPartElementName());
        if (partElement.getRepairs() == null) {
            partElement.setRepairs(new HashSet<>());
        }
        partElement.getRepairs().add(calculatedRepair);
        save(partElement);
    }

    private CalculatedPartElement get(CalculatedElement element, String partElementName) {
        CalculatedPartElement partElement = element.getPartElements()
                .stream()
                .collect(Collectors.toMap(CalculatedPartElement::getPartElementName, p -> p))
                .get(partElementName);
        if (partElement == null) {
            return create(element, partElementName);
        }
        return partElement;
    }

    private void save(CalculatedPartElement partElement) {
        repository.save(partElement);
    }

    private CalculatedPartElement create(CalculatedElement element, String partElementName) {
        return mapper.mapToCalculatedPartElement(element, partElementName);
    }
}