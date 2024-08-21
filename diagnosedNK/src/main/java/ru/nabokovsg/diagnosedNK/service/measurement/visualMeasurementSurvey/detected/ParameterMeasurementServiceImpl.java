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
    public Set<ParameterMeasurement> save(Set<MeasuredParameter> parameters
                                        , List<ParameterMeasurementDto> parametersDto) {
        Map<Long, MeasuredParameter> measuredParameter = parameters.stream()
                                                           .collect(Collectors.toMap(MeasuredParameter::getId, m -> m));
        return new HashSet<>(
                repository.saveAll(parametersDto.stream()
                                        .map(p -> mapper.mapToParameterMeasurement(measuredParameter.get(p.getParameterId())
                                                                                 , p))
                                        .toList())
        );
    }

    @Override
    public Set<ParameterMeasurement> update(Set<ParameterMeasurement> parameters
                                          , List<ParameterMeasurementDto> parametersDto) {
        Map<Long, ParameterMeasurement> parametersDb = parameters.stream()
                                              .collect(Collectors.toMap(ParameterMeasurement::getParameterId, p -> p));
        return new HashSet<>(
                repository.saveAll(parametersDto.stream()
                        .map(p -> mapper.mapToUpdateParameterMeasurement(parametersDb.get(p.getParameterId()), p))
                        .toList())
        );
    }
}