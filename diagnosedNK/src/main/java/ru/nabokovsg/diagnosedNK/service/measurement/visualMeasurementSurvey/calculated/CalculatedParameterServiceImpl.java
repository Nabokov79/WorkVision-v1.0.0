package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedParameterRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.CalculationMeasurementService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculatedParameterServiceImpl implements CalculatedParameterService {

    private final CalculatedParameterRepository repository;
    private final CalculatedParameterMapper mapper;
    private final CalculationMeasurementService calculationService;

    @Override
    public void save(Set<IdentifiedDefect> defects, ParameterCalculationType calculation) {
        repository.saveAll(calculation(defect.getCalculation()
                         , defect.getMeasuredParameters()
                         , parameters
                         , parameterMeasurements)
                  .stream()
                 .peek(p -> mapper.mapWithDefect(p, calculatedDefect))
                .toList());
    }

    private List<CalculatedParameter> calculation(Set<IdentifiedDefect> identifiedDefects, ParameterCalculationType calculation) {
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