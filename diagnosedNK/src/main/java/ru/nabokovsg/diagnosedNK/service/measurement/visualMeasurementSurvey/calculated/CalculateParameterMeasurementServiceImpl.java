package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedParameterRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation.CalculateAllParametersService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation.CalculateOneByOneParametersService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation.CalculateParameterMeasurementFactory;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculateParameterMeasurementServiceImpl implements CalculateParameterMeasurementService {

    private final CalculatedParameterRepository repository;
    private final CalculatedParameterMapper mapper;
    private final CalculateParameterMeasurementFactory factory;
    private final CalculateOneByOneParametersService calculateOneByOneParametersService;
    private final CalculateAllParametersService calculateAllParametersService;

    @Override
    public void save(CalculatedParameterData parameterData) {
        Map<String, CalculateParameterMeasurement> calculatedParameters = new HashMap<>();
        Set<CalculateParameterMeasurement> parameters = getAll(parameterData);
        Map<String, CalculateParameterMeasurement> calculatedParameters = calculate(parameterData);
        mapWith(parameterData, calculatedParameters);
        if (parameters != null) {
            update(parameters, calculatedParameters);
        }
        repository.saveAll(calculatedParameters.values());
    }

    private void update(Set<CalculateParameterMeasurement> parameters, Map<String, CalculateParameterMeasurement> calculatedParameters) {
        parameters.forEach(parameter -> calculatedParameters.put(parameter.getParameterName()
                      , mapper.mapToUpdateCalculatedParameter(parameter, calculatedParameters.get(parameter.getParameterName()))));
    }

    private List<CalculateParameterMeasurement> calculate(CalculatedParameterData parameterData) {
        Integer measurementNumber = 1;
        switch (parameterData.getCalculationType()) {
            case SQUARE -> {
                return factory.calculateOneByOne(createCalculateParameters(parameterData), parameterData.getCalculationType());
            };
            case MIN, MAX, MAX_MIN -> {
                return factory.calculateAll(createCalculateParameters(parameterData), parameterData.getCalculationType());
            }
            case NO_ACTION -> {
                return createCalculateParameters(parameterData).values()
                                        .stream()
                                        .map(parameters -> setSequentialParameterNumber(parameters, measurementNumber))
                                        .flatMap(Collection::stream)
                                        .toList();
            }
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    public Set<CalculateParameterMeasurement> setSequentialParameterNumber(Set<CalculateParameterMeasurement> parameterMeasurements
            , Integer measurementNumber) {
        int sequentialNumber = 1;
        int size = parameterMeasurements.size();
        for (CalculateParameterMeasurement parameter : parameterMeasurements) {
            if (parameter.getMeasurementNumber() == null) {
                if (parameter.getParameterName().equals(MeasuredParameterType.valueOf("SQUARE").label)) {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, sequentialNumber);
                    sequentialNumber++;
                } else if (parameter.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, size);
                } else {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, sequentialNumber);
                    sequentialNumber++;
                }
            }
        }
        return parameterMeasurements;
    }

    private Map<Long, Set<CalculateParameterMeasurement>> createCalculateParameters(CalculatedParameterData parameterData) {
        switch (parameterData.getTypeData()) {
            case DEFECT -> {
                return parameterData.getDefects()
                        .stream()
                        .collect(Collectors.toMap(IdentifiedDefect::getId
                                , defect ->  mapTo(defect.getParameterMeasurements(), parameterData.getCalculationType())));
            }
            case REPAIR -> {
                return parameterData.getRepairs()
                        .stream()
                        .collect(Collectors.toMap(CompletedRepair::getId
                                , repair ->  mapTo(repair.getParameterMeasurements(), parameterData.getCalculationType())));
            }
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    private Set<CalculateParameterMeasurement> mapTo(Set<ParameterMeasurement> parameterMeasurements, ParameterCalculationType calculation) {
        String measuredParameter = MeasuredParameterType.valueOf("QUANTITY").label;
        return parameterMeasurements.stream()
                .map(parameter -> {
                    if (parameter.getParameterName().equals(measuredParameter)) {
                        return mapper.mapToQuantity(parameter);
                    }
                    switch (calculation) {
                        case NO_ACTION, MIN, SQUARE -> {
                            return mapper.mapToMinValue(parameter);
                        }
                        case MAX -> {
                            return mapper.mapToMaxValue(parameter);
                        }
                        case MAX_MIN -> {
                            return mapper.mapToMaxMinValue(parameter);
                        }
                }})
                .collect(Collectors.toSet());

    }


    private void mapWith(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> calculatedParameters) {
        switch (parameterData.getTypeData()) {
            case DEFECT ->
                calculatedParameters.forEach((k,v) ->
                    calculatedParameters.put(k, mapper.mapWithDefect(v, parameterData.getDefect()))
               );
            case REPAIR -> calculatedParameters.forEach((k,v) ->
                    calculatedParameters.put(k, mapper.mapWithRepair(v, parameterData.getRepair()))
            );
            default -> throw new NotFoundException(String.format("Type data=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }


}