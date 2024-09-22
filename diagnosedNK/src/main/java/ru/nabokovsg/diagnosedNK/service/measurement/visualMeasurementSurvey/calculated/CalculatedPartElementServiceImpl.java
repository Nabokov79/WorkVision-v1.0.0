package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedPartElementMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedPartElement;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedPartElementRepository;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculatedPartElementServiceImpl implements CalculatedPartElementService {

    private final CalculatedPartElementRepository repository;
    private final CalculatedPartElementMapper mapper;

    @Override
    public CalculatedPartElement get(CalculatedElement element, String partElementName) {
        if (element.getPartElements() == null) {
            element.setPartElements(new HashSet<>());
        }
        return element.getPartElements()
                .stream()
                .collect(Collectors.toMap(CalculatedPartElement::getPartElementName, p -> p))
                .get(partElementName);
    }

    @Override
    public void save(CalculatedElement element, String partElementName) {
        CalculatedPartElement partElement = get(element, partElementName);
        if (partElement == null) {
            element.getPartElements().add(repository.save(mapper.mapToCalculatedPartElement(element, partElementName)));
        }
    }
}