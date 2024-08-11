package ru.nabokovsg.diagnosedNK.service.measurement.hardnessMeasurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.ResponseElementHardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.hardnessMeasurement.ElementHardnessMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.ElementHardnessMeasurement;
import ru.nabokovsg.diagnosedNK.repository.measurement.hardnessMeasurement.ElementHardnessMeasurementRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentElementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElementHardnessMeasurementServiceImpl implements ElementHardnessMeasurementService {

    private final ElementHardnessMeasurementRepository repository;
    private final ElementHardnessMeasurementMapper mapper;
    private final HardnessMeasurementService measurementService;
    private final PartElementHardnessMeasurementService partElementHardnessMeasurementService;
    private final EquipmentElementService elementService;

    @Override
    public ResponseElementHardnessMeasurementDto save(HardnessMeasurementDto measurementDto) {
        EquipmentElement element = elementService.get(measurementDto.getElementId());
        ElementHardnessMeasurement measurement = repository.findByEquipmentIdAndElementId(
                                                                                      measurementDto.getEquipmentId()
                                                                                    , measurementDto.getElementId());
        if (measurement == null) {
            measurement = mapper.mapToElementHardnessMeasurement(measurementDto.getEquipmentId(), element);
            if (measurementDto.getPartElementId() == null) {
                measurement = mapper.mapWithHardnessMeasurement(measurement, measurementService.save(measurementDto
                                                                                          , element.getStandardSize()));
            }
            measurement = repository.save(measurement);
            if (measurementDto.getPartElementId() != null) {
                measurement.getPartElementMeasurements().add(partElementHardnessMeasurementService.save(measurementDto
                        , measurement
                        , element.getPartsElement()));
            }
            return mapper.mapToResponseElementHardnessMeasurementDto(measurement);
        }
        return update(measurementDto, measurement, element);
    }

    private ResponseElementHardnessMeasurementDto update(HardnessMeasurementDto measurementDto
            , ElementHardnessMeasurement measurement
            , EquipmentElement element ) {
        if (measurement.getMeasurement() == null) {
            measurement.setPartElementMeasurements(partElementHardnessMeasurementService.update(measurementDto
                    , measurement.getPartElementMeasurements()
                    , element.getPartsElement()));
        } else {
            measurement.setMeasurement(measurementService.update(measurementDto
                    , measurement.getMeasurement()
                    , element.getStandardSize()));
        }
        return mapper.mapToResponseElementHardnessMeasurementDto(measurement);
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