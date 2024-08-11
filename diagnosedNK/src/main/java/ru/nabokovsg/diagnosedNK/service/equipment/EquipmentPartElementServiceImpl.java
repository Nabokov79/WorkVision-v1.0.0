package ru.nabokovsg.diagnosedNK.service.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentElement.EquipmentElementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.equipment.EquipmentPartElementMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.repository.equipment.EquipmentPartElementRepository;
import ru.nabokovsg.diagnosedNK.service.equipmentType.EquipmentTypePartElementService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentPartElementServiceImpl implements EquipmentPartElementService {

    private final EquipmentPartElementRepository repository;
    private final EquipmentPartElementMapper mapper;
    private final EquipmentTypePartElementService partElementService;
    private final StandardSizeService standardSizeService;

    @Override
    public void save(EquipmentElement element, EquipmentElementDto elementDto) {
        boolean flag = false;
        if (element.getPartsElement() == null) {
            element.setPartsElement(new HashSet<>());
            flag = true;
        }
        if (!element.getPartsElement().isEmpty()) {
            EquipmentPartElement partElement = element.getPartsElement()
                                            .stream()
                                            .collect(Collectors.toMap(EquipmentPartElement::getPartElementId, p -> p))
                                            .get(elementDto.getPartElementId());
            if (partElement == null) {
                flag = true;
            }
        }
        if (flag) {
            element.getPartsElement().add(repository.save(
                    mapper.mapToEquipmentTypePartElement(element
                            , partElementService.getById(elementDto.getPartElementId())
                            , standardSizeService.save(elementDto.getStandardSize())))
            );
        }
    }

    @Override
    public EquipmentPartElement update(Set<EquipmentPartElement> partsElement, EquipmentElementDto elementDto) {
        return repository.save(
                mapper.mapToUpdateEquipmentTypePartElement(
                                            partsElement.stream()
                                                        .collect(Collectors.toMap(EquipmentPartElement::getId, p -> p))
                                                        .get(elementDto.getPartElementId())
                                          , standardSizeService.save(elementDto.getStandardSize()))
        );
    }

    @Override
    public EquipmentPartElement get(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Equipment part element with id=%s not found", id)));
    }
}