package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.remarkByEquipment.RemarkByEquipmentDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.remarkByEquipment.ResponseRemarkByEquipmentDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected.RemarkByEquipmentMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.RemarkByEquipment;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected.RemarkByEquipmentRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentElementService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RemarkByEquipmentServiceImpl implements RemarkByEquipmentService {

    private final RemarkByEquipmentRepository repository;
    private final RemarkByEquipmentMapper mapper;
    private final EquipmentElementService elementService;

    @Override
    public ResponseRemarkByEquipmentDto save(RemarkByEquipmentDto remarkDto) {
        EquipmentElement element = elementService.get(remarkDto.getElementId());
        RemarkByEquipment remark = mapper.mapWithEquipmentElement(remarkDto, element);
        if (remarkDto.getPartElementId() != null) {
            EquipmentPartElement partElement = element.getPartsElement()
                    .stream()
                    .collect(Collectors.toMap(EquipmentPartElement::getPartElementId, p -> p))
                    .get(remarkDto.getPartElementId());
            mapper.mapWithEquipmentPartElement(remark, partElement);
        }
        return mapper.mapToResponseRemarkByEquipmentDto(remark);
    }

    @Override
    public ResponseRemarkByEquipmentDto update(RemarkByEquipmentDto remarkDto) {
        return mapper.mapToResponseRemarkByEquipmentDto(repository.save(
                            mapper.mapToUpdateRemarkByEquipment(getById(remarkDto.getId()), remarkDto.getInspection()))
        );
    }

    @Override
    public List<ResponseRemarkByEquipmentDto> getAll(Long equipmentId) {
        return repository.findAllByEquipmentId(equipmentId)
                         .stream()
                         .map(mapper::mapToResponseRemarkByEquipmentDto)
                         .toList();
    }

    private RemarkByEquipment getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("RemarkByEquipment by id=%s not found", id)));
    }
}