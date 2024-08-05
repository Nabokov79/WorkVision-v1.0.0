package ru.nabokovsg.diagnosedNK.service.diagnosticEquipmentData;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.diagnosticEquipmentData.ElementDto;
import ru.nabokovsg.diagnosedNK.dto.diagnosticEquipmentData.PartElementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.diagnosticEquipmentData.StandardSizeMapper;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.repository.diagnosticEquipmentData.EquipmentStandardSizeRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentStandardSizeServiceImpl implements EquipmentStandardSizeService {

    private final EquipmentStandardSizeRepository repository;
    private final StandardSizeMapper mapper;

    @Override
    public void save(DiagnosticEquipmentData diagnosticObjectData, List<ElementDto> elements) {
        List<ElementData> objectStandardSizes = new ArrayList<>();
        elements.forEach(element -> {
            if (element.getPartsElement().isEmpty()) {
                objectStandardSizes.add(mapper.mapWithElementDto(element, diagnosticObjectData));
            } else {
                objectStandardSizes.addAll(createByPartElement(diagnosticObjectData, element));
            }
        });
        repository.saveAll(objectStandardSizes);
    }

    @Override
    public void update(DiagnosticEquipmentData diagnosticObjectData, List<ElementDto> elements) {
        Map<Long, ElementData> objectStandardSizes = repository.findAllByEquipmentData(diagnosticObjectData)
                                                .stream()
                                                .collect(Collectors.toMap(ElementData::getElementId, o -> o));
        Map<Long, PartElementDto> partElements = elements.stream()
                                                         .map(ElementDto::getPartsElement)
                                                         .flatMap(Collection::stream)
                                                         .collect(Collectors.toMap(PartElementDto::getId, p -> p));
        elements.forEach(element -> {
            ElementData standard = objectStandardSizes.get(element.getId());
            if (standard.getPartElementId() != null) {
                objectStandardSizes.put(standard.getElementId(), mapper.mapUpdateWithPartElementDto(standard.getId()
                                                                        , element
                                                                        , partElements.get(standard.getPartElementId())
                                                                        , diagnosticObjectData));
            } else {
                objectStandardSizes.put(standard.getElementId(), mapper.mapUpdateWithElementDto(standard.getId()
                                                                                               , element
                                                                                               , diagnosticObjectData));
            }
        });
        repository.saveAll(objectStandardSizes.values());
    }

    @Override
    public ElementData getByElementId(Long elementId) {
        return repository.findByElementId(elementId)
               .orElseThrow(() -> new NotFoundException(
                       String.format("ObjectStandardSize by elementId=%s not found", elementId)));
    }

    @Override
    public ElementData getByPartElementId(Long partElementId) {
        return repository.findByPartElementId(partElementId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("ObjectStandardSize by partElementId=%s not found", partElementId)));
    }

    private List<ElementData> createByPartElement(DiagnosticEquipmentData diagnosticObjectData, ElementDto element) {
        return element.getPartsElement()
                      .stream()
                      .map(partElement -> mapper.mapWithPartElementDto(element
                                                                     , partElement
                                                                     , diagnosticObjectData))
                      .toList();
    }
}