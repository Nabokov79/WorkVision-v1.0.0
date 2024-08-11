package ru.nabokovsg.diagnosedNK.service.equipmentType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypeElement.EquipmentTypeElementDto;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.equipmentType.EquipmentTypeElementMapper;
import ru.nabokovsg.diagnosedNK.repository.equipmentType.EquipmentTypeElementRepository;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypeElement.ResponseEquipmentTypeElementDto;
import ru.nabokovsg.diagnosedNK.model.equipmentType.EquipmentTypeElement;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentTypeElementServiceImpl implements EquipmentTypeElementService {

    private final EquipmentTypeElementRepository repository;
    private final EquipmentTypeElementMapper mapper;
    private final EquipmentTypeService equipmentTypeService;

    @Override
    public ResponseEquipmentTypeElementDto save(EquipmentTypeElementDto elementDto) {
        if (repository.existsByEquipmentTypeIdAndElementName(elementDto.getEquipmentTypeId()
                                                           , elementDto.getElementName())) {
            throw new BadRequestException(String.format("Element by equipmentTypeId=%s and elementName=%s is found"
                                                                                    , elementDto.getEquipmentTypeId()
                                                                                    , elementDto.getElementName()));
        }
        return mapper.mapToResponseEquipmentTypeElementDto(
                repository.save(mapper.mapToElement(elementDto
                                                   , equipmentTypeService.getById(elementDto.getEquipmentTypeId()))));
    }

    @Override
    public ResponseEquipmentTypeElementDto update(EquipmentTypeElementDto elementDto) {
        if (repository.existsById(elementDto.getId())) {
            return mapper.mapToResponseEquipmentTypeElementDto(
                    repository.save(mapper.mapToElement(elementDto
                                                    , equipmentTypeService.getById(elementDto.getEquipmentTypeId())))
            );
        }
        throw new NotFoundException(String.format("Element with id=%s not found for update", elementDto.getId()));
    }

    @Override
    public List<ResponseEquipmentTypeElementDto> getAll(Long id) {
        return repository.findAllByEquipmentTypeId(id)
                         .stream()
                         .map(mapper::mapToResponseEquipmentTypeElementDto)
                         .toList();
    }

    @Override
    public EquipmentTypeElement getById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Element with id=%s not found", id)));
    }
}