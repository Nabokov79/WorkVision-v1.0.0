package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected.ParameterMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected.ParameterMeasurementRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParameterMeasurementServiceImpl implements ParameterMeasurementService {

    private final ParameterMeasurementRepository repository;
    private final ParameterMeasurementMapper mapper;

    @Override
    public Set<ParameterMeasurement> save(Set<MeasuredParameter> measuredParameters
                                        , List<ParameterMeasurementDto> parameterMeasurementsDto) {
        Map<Long, MeasuredParameter> parameters = measuredParameters.stream()
                                                           .collect(Collectors.toMap(MeasuredParameter::getId, m -> m));
        return new HashSet<>(
                repository.saveAll(
                parameterMeasurementsDto.stream()
                                        .map(p -> mapper.mapToParameterMeasurement(parameters.get(p.getParameterId())
                                                                                 , p))
                                        .toList())
        );
    }
}