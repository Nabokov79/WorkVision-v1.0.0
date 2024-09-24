package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected.ParameterMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.builders.ParameterMeasurementBuilder;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParameterMeasurementFactoryImpl implements ParameterMeasurementFactory {

    private final ParameterMeasurementMapper mapper;

    @Override
    public List<ParameterMeasurement> get(ParameterMeasurementBuilder builder) {
        List<ParameterMeasurement> parameterMeasurements = convert(builder.getParameterMeasurement(), builder.getMeasuredParameters());
        switch (builder.getTypeData()) {
            case DEFECT -> {
                return getForIdentifiedDefect(parameterMeasurements, builder);
            }
            case REPAIR -> {
                return getForCompletedRepair(parameterMeasurements, builder);
            }
            case VM_CONTROL -> {
                return getForVMControl(parameterMeasurements, builder);
            }
        }
        return parameterMeasurements;
    }

    private List<ParameterMeasurement> getForIdentifiedDefect(List<ParameterMeasurement> parameterMeasurements
            , ParameterMeasurementBuilder builder) {
        if (builder.getDefect().getParameterMeasurements() != null) {
            return sumQuantity(builder.getParameterMeasurement(), builder.getDefect().getParameterMeasurements());
        }
        return parameterMeasurements.stream()
                .peek(parameter -> mapper.mapWithIdentifiedDefect(parameter, builder.getDefect()))
                .toList();
    }


    private List<ParameterMeasurement> getForCompletedRepair(List<ParameterMeasurement> parameterMeasurements
            , ParameterMeasurementBuilder builder) {
        if (builder.getRepair().getParameterMeasurements() != null) {
            return sumQuantity(builder.getParameterMeasurement(), builder.getRepair().getParameterMeasurements());
        }
        return parameterMeasurements.stream()
                .peek(parameter -> mapper.mapWithCompletedRepair(parameter, builder.getRepair()))
                .toList();
    }

    private List<ParameterMeasurement> getForVMControl(List<ParameterMeasurement> parameterMeasurements
            , ParameterMeasurementBuilder builder) {
        return parameterMeasurements.stream()
                .peek(parameter -> mapper.mapWithVisualMeasurementControl(parameter, builder.getVmControl()))
                .toList();
    }

    private List<ParameterMeasurement> convert(List<ParameterMeasurementDto> parametersDto
            , Set<MeasuredParameter> parameters) {
        Map<Long, MeasuredParameter> measuredParameter = parameters.stream()
                .collect(Collectors.toMap(MeasuredParameter::getId, m -> m));
        return parametersDto.stream()
                .map(p -> mapper.mapToParameterMeasurement(p, measuredParameter.get(p.getParameterId())))
                .toList();
    }

    private List<ParameterMeasurement> sumQuantity(List<ParameterMeasurementDto> parametersDto
            , Set<ParameterMeasurement> parametersDb) {
        Map<Long, ParameterMeasurementDto> parameters = parametersDto
                .stream()
                .collect(Collectors.toMap(ParameterMeasurementDto::getParameterId, p -> p));
        parametersDb.forEach(p -> {
            if (p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                ParameterMeasurementDto parameter = parameters.get(p.getParameterId());
                p.setValue(p.getValue() + parameter.getValue());
            }
        });
        return new ArrayList<>(parametersDb);
    }
}