package ru.nabokovsg.diagnosedNK.service.measurement.hardnessMeasurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.ResponseElementHardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.hardnessMeasurement.ElementHardnessMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.ElementHardnessMeasurement;
import ru.nabokovsg.diagnosedNK.repository.measurement.hardnessMeasurement.ElementHardnessMeasurementRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.QueryDSLRequestService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElementHardnessMeasurementServiceImpl implements ElementHardnessMeasurementService {

    private final ElementHardnessMeasurementRepository repository;
    private final ElementHardnessMeasurementMapper mapper;
    private final QueryDSLRequestService requestService;
    private final HardnessMeasurementService measurementService;
    private final PartElementHardnessMeasurementService partElementHardnessMeasurementService;

    @Override
    public ResponseElementHardnessMeasurementDto save(HardnessMeasurementDto measurementDto) {
        DiagnosticEquipmentData objectData = requestService.getDiagnosticEquipmentData(measurementDto.getElementId()
                                                                                , measurementDto.getPartElementId());
        ElementData objectElementData = objectData.getObjectStandardSizes()
                                                    .stream()
                                                    .collect(Collectors.toMap(ElementData::getElementId, o -> o))
                                                    .get(measurementDto.getElementId());
        ElementHardnessMeasurement element = repository.findByEquipmentIdAndElementId(
                                                                                      measurementDto.getEquipmentId()
                                                                                    , measurementDto.getElementId());
        if (element == null) {
            element = mapper.mapToElementHardnessMeasurement(measurementDto.getEquipmentId()
                    , objectData.getEquipmentId()
                    , objectElementData);
            if (measurementDto.getPartElementId() == null) {
                element = mapper.mapWithHardnessMeasurement(element, measurementService.save(measurementDto
                                                                                           , objectData
                                                                                           , objectElementData));
            }
            element = repository.save(element);
            if (measurementDto.getPartElementId() != null) {
                element.getPartElementMeasurements().add(partElementHardnessMeasurementService.save(measurementDto
                        , element
                        , objectData
                        , objectElementData));
            }
            return mapper.mapToResponseElementHardnessMeasurementDto(element);
        }
        return update(measurementDto, element, objectData, objectElementData);
    }

    private ResponseElementHardnessMeasurementDto update(HardnessMeasurementDto measurementDto
            , ElementHardnessMeasurement element
            , DiagnosticEquipmentData objectData
            , ElementData objectElementData) {
        if (element.getMeasurement() == null) {
            element.setPartElementMeasurements(partElementHardnessMeasurementService.update(measurementDto
                    , element.getPartElementMeasurements()
                    , objectData
                    , objectElementData));
        } else {
            element.setMeasurement(measurementService.update(measurementDto
                    , element.getMeasurement()
                    , objectData
                    , objectElementData));
        }
        return mapper.mapToResponseElementHardnessMeasurementDto(element);
    }

    @Override
    public List<ResponseElementHardnessMeasurementDto> getAll(Long equipmentId) {
        return repository.findAllByEquipmentId(equipmentId)
                .stream()
                .map(mapper::mapToResponseElementHardnessMeasurementDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("ElementHardnessMeasurement with id%s not found delete", id));
    }
}