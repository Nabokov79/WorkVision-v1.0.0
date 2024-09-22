package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedElementMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedElement;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedElementRepository;

@Service
@RequiredArgsConstructor
public class CalculatedElementServiceImpl implements CalculatedElementService {

    private final CalculatedElementRepository repository;
    private final CalculatedElementMapper mapper;
    private final CalculatedPartElementService partElementService;

    @Override
    public CalculatedElement get(Long equipmentId, String elementName, String partElementName) {
        CalculatedElement element = repository.findByEquipmentIdAndElementName(equipmentId, elementName);
        if (element == null) {
            element = save(create(equipmentId, elementName));
        }
        if (partElementName != null) {
            partElementService.save(element, partElementName);
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