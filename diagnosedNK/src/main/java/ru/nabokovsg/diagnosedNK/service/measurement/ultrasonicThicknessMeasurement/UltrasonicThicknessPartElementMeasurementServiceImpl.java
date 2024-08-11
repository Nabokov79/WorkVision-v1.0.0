package ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicThicknessMeasurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.mapper.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessPartElementMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.equipment.StandardSize;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessPartElementMeasurement;
import ru.nabokovsg.diagnosedNK.repository.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessPartElementMeasurementRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UltrasonicThicknessPartElementMeasurementServiceImpl
        implements UltrasonicThicknessPartElementMeasurementService {

    private final UltrasonicThicknessPartElementMeasurementRepository repository;
    private final UltrasonicThicknessPartElementMeasurementMapper mapper;
    private final UltrasonicThicknessMeasurementService measurementService;

    @Override
    public UltrasonicThicknessPartElementMeasurement save(UltrasonicThicknessMeasurementDto measurementDto
                                                        , UltrasonicThicknessElementMeasurement elementMeasurement
                                                        , Set<EquipmentPartElement> partsElement) {
        EquipmentPartElement partElement = partsElement.stream()
                                            .collect(Collectors.toMap(EquipmentPartElement::getPartElementId, p -> p))
                                            .get(measurementDto.getPartElementId());
        UltrasonicThicknessPartElementMeasurement measurement = repository.findByPartElementId(measurementDto.getPartElementId());
        if (measurement == null) {
            measurement = repository.save(mapper.mapToUltrasonicThicknessPartElementMeasurement(elementMeasurement
                                                                            , partElement
                                                                            , measurementService.save(measurementDto
                            , partElement.getStandardSize())));
        }
        return measurement;
    }

    @Override
    public Set<UltrasonicThicknessPartElementMeasurement> update(UltrasonicThicknessMeasurementDto measurementDto
                                                , Set<UltrasonicThicknessPartElementMeasurement> partElementMeasurements
                                                , Set<EquipmentPartElement> partsElement) {
        Map<Long, UltrasonicThicknessPartElementMeasurement> partElements = partElementMeasurements
                .stream()
                .collect(Collectors.toMap(UltrasonicThicknessPartElementMeasurement::getPartElementId, p -> p));
        StandardSize standardSize = partsElement.stream()
                .collect(Collectors.toMap(EquipmentPartElement::getPartElementId, p -> p))
                .get(measurementDto.getPartElementId())
                .getStandardSize();
        UltrasonicThicknessPartElementMeasurement partElementMeasurement = partElements.get(measurementDto.getPartElementId());
        partElements.put(partElementMeasurement.getPartElementId()
                , repository.save(mapper.mapWithUltrasonicThicknessMeasurement(partElementMeasurement
                                                                          , measurementService.update(measurementDto
                                                                          , partElementMeasurement.getMeasurement()
                                                                          , standardSize))));
        return new HashSet<>(partElements.values());
    }
}