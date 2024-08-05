package ru.nabokovsg.diagnosedNK.service.measurement.hardnessMeasurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.mapper.measurement.hardnessMeasurement.PartElementHardnessMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
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
                                             , DiagnosticEquipmentData objectData
                                             , ElementData objectElementData) {
        PartElementHardnessMeasurement measurement = repository.findByPartElementId(measurementDto.getPartElementId());
        if (measurement == null) {
            measurement = repository.save(mapper.mapToPartElementHardnessMeasurement(elementMeasurement
                                                                        , objectElementData
                                                                        , measurementService.save(measurementDto
                                                                                                , objectData
                                                                                                , objectElementData)));
        }
        return measurement;
    }

    @Override
    public Set<PartElementHardnessMeasurement> update(HardnessMeasurementDto measurementDto
                                                    , Set<PartElementHardnessMeasurement> partElementMeasurements
                                                    , DiagnosticEquipmentData objectData
                                                    , ElementData objectElementData) {
        Map<Long, PartElementHardnessMeasurement> partElements = partElementMeasurements
                .stream()
                .collect(Collectors.toMap(PartElementHardnessMeasurement::getPartElementId, p -> p));
        PartElementHardnessMeasurement partElementMeasurement = partElements.get(measurementDto.getPartElementId());
        partElements.put(partElementMeasurement.getPartElementId()
                , repository.save(mapper.mapWithUltrasonicThicknessMeasurement(partElementMeasurement
                        , measurementService.update(measurementDto
                                , partElementMeasurement.getMeasurement()
                                , objectData
                                , objectElementData))));
        return new HashSet<>(partElements.values());
    }
}