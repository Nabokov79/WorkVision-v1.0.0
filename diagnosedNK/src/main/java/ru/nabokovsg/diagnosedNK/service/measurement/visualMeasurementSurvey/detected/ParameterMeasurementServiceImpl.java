package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected.ParameterMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.builders.ParameterMeasurementBuilder;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected.ParameterMeasurementRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParameterMeasurementServiceImpl implements ParameterMeasurementService {

    private final ParameterMeasurementRepository repository;
    private final ParameterMeasurementMapper mapper;
    private final ParameterMeasurementFactory factory;

    @Override
    public Set<ParameterMeasurement> save(ParameterMeasurementBuilder builder) {
        return new HashSet<>(repository.saveAll(factory.get(builder)));
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

    @Override
    public void deleteAll(Set<ParameterMeasurement> parameters) {
        repository.deleteAll(parameters);
    }

    @Override
    public Set<ParameterMeasurement> updateQuantity(Set<ParameterMeasurement> parameterMeasurements, Integer quantity) {
        parameterMeasurements.addAll(parameterMeasurements.stream().peek(p -> {
                    if (p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                        p.setValue(p.getValue() - quantity);
                    }
                })
                .toList());
        return parameterMeasurements;
    }

    @Override
    public boolean searchParameterDuplicate(Set<ParameterMeasurement> parameterMeasurements
            , Map<Long, ParameterMeasurementDto> parameters) {
        String quantityName = MeasuredParameterType.valueOf("QUANTITY").label;
        int coincidences = 0;
        Map<String, Double> quantity = new HashMap<>(1);
        for (ParameterMeasurement parameterDb : parameterMeasurements) {
            ParameterMeasurementDto parameter = parameters.get(parameterDb.getParameterId());
            if (parameterDb.getParameterName().equals(quantityName)) {
                quantity.put(parameterDb.getParameterName(), parameter.getValue());
            }
            if (parameter != null) {
                if (parameterDb.getParameterName().equals(quantityName) || parameterDb.getValue().equals(parameter.getValue())) {
                    coincidences++;
                }
            }
        }
        if (coincidences == parameters.size()) {
            parameterMeasurements.forEach(v -> {
                if (v.getParameterName().equals(quantityName)) {
                    mapper.mapToUpdateQuantity(v, v.getValue() + quantity.get(quantityName));
                }
            });
            return true;
        }
        return false;
    }
}