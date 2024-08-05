package ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentElement.EquipmentElementDto;
import ru.nabokovsg.diagnosedEquipment.mapper.diagnosedEquipment.EquipmentPartElementMapper;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentElement;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedEquipment.repository.diagnosedEquipment.EquipmentPartElementRepository;
import ru.nabokovsg.diagnosedEquipment.service.equipmentType.EquipmentTypePartElementService;

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
    public EquipmentPartElement save(EquipmentElement element, EquipmentElementDto elementDto) {
        return repository.save(
                mapper.mapToEquipmentTypePartElement(element
                                                   , partElementService.getById(elementDto.getPartElementId())
                                                   , standardSizeService.save(elementDto.getStandardSize()))
        );
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
}