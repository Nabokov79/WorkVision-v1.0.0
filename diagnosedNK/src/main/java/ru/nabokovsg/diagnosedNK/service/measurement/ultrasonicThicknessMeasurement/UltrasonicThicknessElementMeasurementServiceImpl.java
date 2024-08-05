package ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicThicknessMeasurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.ResponseUltrasonicThicknessElementMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurement;
import ru.nabokovsg.diagnosedNK.repository.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurementRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.QueryDSLRequestService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UltrasonicThicknessElementMeasurementServiceImpl implements UltrasonicThicknessElementMeasurementService {

    private final UltrasonicThicknessElementMeasurementRepository repository;
    private final UltrasonicThicknessElementMeasurementMapper mapper;
    private final UltrasonicThicknessPartElementMeasurementService partElementMeasurementService;
    private final UltrasonicThicknessMeasurementService measurementService;
    private final QueryDSLRequestService requestService;

    @Override
    public ResponseUltrasonicThicknessElementMeasurementDto save(UltrasonicThicknessMeasurementDto measurementDto) {
        DiagnosticEquipmentData objectData = requestService.getDiagnosticEquipmentData(measurementDto.getElementId()
                                                                                , measurementDto.getPartElementId());
        ElementData objectElementData = objectData.getObjectStandardSizes()
                                                    .stream()
                                                    .collect(Collectors.toMap(ElementData::getElementId, o -> o))
                                                    .get(measurementDto.getElementId());
        UltrasonicThicknessElementMeasurement element = repository.findByEquipmentIdAndElementId(
                                                                                       measurementDto.getEquipmentId()
                                                                                     , measurementDto.getElementId());
       if (element == null) {
           element = mapper.mapToUltrasonicThicknessElementMeasurement(measurementDto.getEquipmentId()
                                                                     , objectData.getEquipmentId()
                                                                     , objectElementData);
           if (measurementDto.getPartElementId() == null) {
               element = mapper.mapWithUltrasonicThicknessElementMeasurement(element
                                                                          , measurementService.save(measurementDto
                                                                                                  , objectData
                                                                                                  , objectElementData));
           }
           element = repository.save(element);
           if (measurementDto.getPartElementId() != null) {
               element.getPartElementMeasurements().add(partElementMeasurementService.save(measurementDto
                                                                                         , element
                                                                                         , objectData
                                                                                         , objectElementData));
           }
           return mapper.mapToResponseUltrasonicThicknessMeasurementDto(element);
       }
        return update(measurementDto, element, objectData, objectElementData);
    }

    private ResponseUltrasonicThicknessElementMeasurementDto update(UltrasonicThicknessMeasurementDto measurementDto
                                                           , UltrasonicThicknessElementMeasurement element
                                                           , DiagnosticEquipmentData objectData
                                                           , ElementData objectElementData) {
        if (element.getMeasurement() == null) {
            element.setPartElementMeasurements(partElementMeasurementService.update(measurementDto
                                                                                 , element.getPartElementMeasurements()
                                                                                 , objectData
                                                                                 , objectElementData));
        } else {
            element.setMeasurement(measurementService.update(measurementDto
                                                           , element.getMeasurement()
                                                           , objectData
                                                           , objectElementData));
        }
        return mapper.mapToResponseUltrasonicThicknessMeasurementDto(element);
    }

    @Override
    public List<ResponseUltrasonicThicknessElementMeasurementDto> getAll(Long workJournalId) {
        return repository.findAllByEquipmentId(workJournalId)
                .stream()
                .map(mapper::mapToResponseUltrasonicThicknessMeasurementDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(
                String.format("UltrasonicThicknessElementMeasurement with id%s not found delete", id));
    }
}