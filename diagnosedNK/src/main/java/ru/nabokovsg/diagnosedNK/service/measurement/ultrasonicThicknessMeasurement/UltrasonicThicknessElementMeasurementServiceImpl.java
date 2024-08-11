package ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicThicknessMeasurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.ResponseUltrasonicThicknessElementMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurement;
import ru.nabokovsg.diagnosedNK.repository.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurementRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentElementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UltrasonicThicknessElementMeasurementServiceImpl implements UltrasonicThicknessElementMeasurementService {

    private final UltrasonicThicknessElementMeasurementRepository repository;
    private final UltrasonicThicknessElementMeasurementMapper mapper;
    private final UltrasonicThicknessPartElementMeasurementService partElementMeasurementService;
    private final UltrasonicThicknessMeasurementService measurementService;
    private final EquipmentElementService elementService;

    @Override
    public ResponseUltrasonicThicknessElementMeasurementDto save(UltrasonicThicknessMeasurementDto measurementDto) {
        UltrasonicThicknessElementMeasurement measurement = repository.findByEquipmentIdAndElementId(
                                                                                       measurementDto.getEquipmentId()
                                                                                     , measurementDto.getElementId());
        EquipmentElement element = elementService.get(measurementDto.getElementId());
       if (measurement == null) {
           measurement = mapper.mapToUltrasonicThicknessElementMeasurement(measurementDto.getEquipmentId(), element);
           if (measurementDto.getPartElementId() == null) {
               measurement = mapper.mapWithUltrasonicThicknessElementMeasurement(measurement
                                                                          , measurementService.save(measurementDto
                                                                          , element.getStandardSize()));
           }
           measurement = repository.save(measurement);
           if (measurementDto.getPartElementId() != null) {
               measurement.getPartElementMeasurements().add(partElementMeasurementService.save(measurementDto
                                                                                         , measurement
                                                                                         , element.getPartsElement()));
           }
           return mapper.mapToResponseUltrasonicThicknessMeasurementDto(measurement);
       }
        return update(measurementDto, measurement, element);
    }

    private ResponseUltrasonicThicknessElementMeasurementDto update(UltrasonicThicknessMeasurementDto measurementDto
                                                                 , UltrasonicThicknessElementMeasurement measurement
                                                                 , EquipmentElement element) {
        if (measurement.getMeasurement() == null) {
            measurement.setPartElementMeasurements(partElementMeasurementService.update(measurementDto
                                                                           , measurement.getPartElementMeasurements()
            , element.getPartsElement()));
        } else {
            measurement.setMeasurement(measurementService.update(measurementDto
                                                               , measurement.getMeasurement()
                                                               , element.getStandardSize()));
        }
        return mapper.mapToResponseUltrasonicThicknessMeasurementDto(measurement);
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