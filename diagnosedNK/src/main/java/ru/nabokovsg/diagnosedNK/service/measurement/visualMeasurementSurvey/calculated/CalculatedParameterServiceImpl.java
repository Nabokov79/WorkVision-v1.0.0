package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.CalculationDefectOrRepair;
import ru.nabokovsg.diagnosedNK.model.norms.CalculationParameter;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedParameterRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.CalculationMeasurementService;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculatedParameterServiceImpl implements CalculatedParameterService {

    private final CalculatedParameterRepository repository;
    private final CalculatedParameterMapper mapper;
    private final CalculationMeasurementService calculationService;

    @Override
    public void save(CalculatedDefect calculatedDefect
                   , Set<CalculatedParameter> parameters
                   , Defect defect
                   , Set<ParameterMeasurement> parameterMeasurements) {
        repository.saveAll(calculation(defect.getCalculation()
                         , defect.getMeasuredParameters()
                         , parameters
                         , parameterMeasurements)
                  .stream()
                 .peek(p -> mapper.mapWithDefect(p, calculatedDefect))
                .toList());
    }

    public Set<CalculatedParameter> calculation(CalculationDefectOrRepair calculation
                                            , Set<MeasuredParameter> measuredParameters
                                            , Set<CalculatedParameter> parameters
                                            , Set<ParameterMeasurement> parameterMeasurements) {
        Map<Long, CalculationParameter> typeCalculationsDb = measuredParameters
                .stream()
                .collect(Collectors.toMap(MeasuredParameter::getId, MeasuredParameter::getCalculation));
        Map<Long, CalculatedParameter> parameterMeasurementsDb = parameters.stream()
                .collect(Collectors.toMap(CalculatedParameter::getId, r -> r));
        switch (calculation) {
            case NO_ACTION -> { return parameterMeasurements.stream()
                    .map(p -> calculationParameter(typeCalculationsDb.get(p.getParameterId())
                            , parameterMeasurementsDb.get(p.getParameterId())
                            , p))
                    .collect(Collectors.toSet());
            }
            case SQUARE -> {
                return calculationService.countSquare(
                        parameters.stream()
                                .collect(Collectors.toMap(CalculatedParameter::getId, p -> p))
                        , parameterMeasurements.stream()
                                .collect(Collectors.toMap(p -> parameterMeasurementsDb.get(p.getParameterId())
                                                .getParameterName()
                                        , p -> p)));
            }
            case QUANTITY -> {
                return calculationService.countQuantity(
                        parameters.stream()
                                .collect(Collectors.toMap(CalculatedParameter::getId, p -> p))
                        , parameterMeasurements.stream()
                                .collect(Collectors.toMap(p -> parameterMeasurementsDb.get(p.getParameterId())
                                                .getParameterName()
                                        , p -> p)));}
            default -> {return parameters;}
        }
    }

    private CalculatedParameter calculationParameter(CalculationParameter calculation
            , CalculatedParameter parameter
            , ParameterMeasurement parameterMeasurement) {
        switch (calculation) {
            case MIN -> {
                return calculationService.countMin(parameter, parameterMeasurement);
            }
            case MAX -> {
                return calculationService.countMax(parameter, parameterMeasurement);
            }
            case MAX_MIN -> {
                return calculationService.countMax(
                        calculationService.countMin(parameter, parameterMeasurement), parameterMeasurement);
            }
            default ->
                    throw new NotFoundException(String.format("Unknown type=%s calculation parameters", calculation));
        }
    }
}