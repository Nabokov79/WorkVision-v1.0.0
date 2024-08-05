package ru.nabokovsg.diagnosedEquipment.service.equipmentType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentTypePartElement.EquipmentTypePartElementDto;
import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentTypePartElement.ResponseEquipmentTypePartElementDto;
import ru.nabokovsg.diagnosedEquipment.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedEquipment.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedEquipment.mapper.equipmentType.EquipmentTypePartElementMapper;
import ru.nabokovsg.diagnosedEquipment.model.equipmentType.EquipmentTypePartElement;
import ru.nabokovsg.diagnosedEquipment.repository.equipmentType.EquipmentTypePartElementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentTypePartElementServiceImpl implements EquipmentTypePartElementService {

    private final EquipmentTypePartElementRepository repository;
    private final EquipmentTypePartElementMapper mapper;
    private final EquipmentTypeElementService elementService;

    @Override
    public ResponseEquipmentTypePartElementDto save(EquipmentTypePartElementDto partElementDto) {
        if (repository.existsByElementIdAndPartElementName(partElementDto.getElementId(), partElementDto.getPartElementName())) {
            throw new BadRequestException(String.format("Element by elementId=%s and partElementName=%s is found"
                    , partElementDto.getElementId()
                    , partElementDto.getPartElementName()));
        }
        return mapper.mapToResponseEquipmentTypePartElementDto(
                repository.save(mapper.mapToEquipmentTypePartElement(partElementDto
                                                               , elementService.getById(partElementDto.getElementId())))
        );
    }

    @Override
    public ResponseEquipmentTypePartElementDto update(EquipmentTypePartElementDto partElementDto) {
        if (repository.existsById(partElementDto.getId())) {
            return mapper.mapToResponseEquipmentTypePartElementDto(
                    repository.save(mapper.mapToEquipmentTypePartElement(partElementDto
                            , elementService.getById(partElementDto.getElementId()))));
        }
        throw new NotFoundException(
                String.format("PartElement with id=%s not found for update", partElementDto.getId())
        );
    }

    @Override
    public List<ResponseEquipmentTypePartElementDto> getAll(Long id) {
        return repository.findAllByElementId(id)
                         .stream()
                         .map(mapper::mapToResponseEquipmentTypePartElementDto)
                         .toList();
    }

    @Override
    public EquipmentTypePartElement getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("PartElement with id=%s not found for update", id)));
    }
}