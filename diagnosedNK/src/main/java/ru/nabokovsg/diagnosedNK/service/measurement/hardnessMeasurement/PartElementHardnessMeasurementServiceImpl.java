package ru.nabokovsg.diagnosedNK.service.measurement.hardnessMeasurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.mapper.measurement.hardnessMeasurement.PartElementHardnessMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.ElementHardnessMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.PartElementHardnessMeasurement;
import ru.nabokovsg.diagnosedNK.repository.measurement.hardnessMeasurement.PartElementHardnessMeasurementRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartElementHardnessMeasurementServiceImpl implements PartElementHardnessMeasurementService {

    private final PartElementHardnessMeasurementRepository repository;
    private final PartElementHardnessMeasurementMapper mapper;
    private final HardnessMeasurementService measurementService;

    @Override
    public PartElementHardnessMeasurement save(HardnessMeasurementDto measurementDto
                                             , ElementHardnessMeasurement elementMeasurement
                                             , Set<EquipmentPartElement> partsElement) {
        PartElementHardnessMeasurement measurement = repository.findByPartElementId(measurementDto.getPartElementId());
        EquipmentPartElement partElement = partsElement.stream()
                .collect(Collectors.toMap(EquipmentPartElement::getPartElementId, p -> p))
                .get(measurementDto.getPartElementId());
        if (measurement == null) {
            measurement = repository.save(mapper.mapToPartElementHardnessMeasurement(elementMeasurement
                                                                        , partElement
                                                                        , measurementService.save(measurementDto
                                                                                     , partElement.getStandardSize())));
        }
        return measurement;
    }

    @Override
    public Set<PartElementHardnessMeasurement> update(HardnessMeasurementDto measurementDto
                                                    , Set<PartElementHardnessMeasurement> partElementMeasurements
                                                    , Set<EquipmentPartElement> partsElement) {
        Map<Long, PartElementHardnessMeasurement> partElements = partElementMeasurements
                .stream()
                .collect(Collectors.toMap(PartElementHardnessMeasurement::getPartElementId, p -> p));
        EquipmentPartElement partElement = partsElement.stream()
                .collect(Collectors.toMap(EquipmentPartElement::getPartElementId, p -> p))
                .get(measurementDto.getPartElementId());
        PartElementHardnessMeasurement partElementMeasurement = partElements.get(measurementDto.getPartElementId());
        partElements.put(partElementMeasurement.getPartElementId()
                , repository.save(mapper.mapWithUltrasonicThicknessMeasurement(partElementMeasurement
                                                  , measurementService.update(measurementDto
                                                  , partElementMeasurement.getMeasurement()
                                                  , partElement.getStandardSize()))));
        return new HashSet<>(partElements.values());
    }
}