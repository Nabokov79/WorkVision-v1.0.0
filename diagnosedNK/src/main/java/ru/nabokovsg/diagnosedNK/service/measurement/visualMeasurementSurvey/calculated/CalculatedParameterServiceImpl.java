package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedParameterRepository;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;
import ru.nabokovsg.diagnosedNK.service.measurement.CalculationMeasurementService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculatedParameterServiceImpl implements CalculatedParameterService {

    private final CalculatedParameterRepository repository;
    private final CalculatedParameterMapper mapper;
    private final CalculationMeasurementService calculationService;
    private final ConstParameterMeasurementService measurementService;

    @Override
    public void saveForDefect(Set<IdentifiedDefect> defects
                            , CalculatedDefect defect
                            , ParameterCalculationType calculation) {
        List<CalculatedParameter> parameters = createParametersByDefect(defects, calculation);
        repository.saveAll(parameters.stream()
                .map(p -> mapper.mapWithDefect(p, defect))
                .toList());
    }

    @Override
    public void updateForDefect(Set<IdentifiedDefect> defects
                              , CalculatedDefect defect
                              , ParameterCalculationType calculation) {
        Map<Long, CalculatedParameter> parametersDb = convertParameters(defect.getParameters());
        List<Long> ids = getIds(defect.getParameters());
        List<CalculatedParameter> parameters = new ArrayList<>();
        createParametersByDefect(defects, calculation).forEach(p -> {
            if (ids.isEmpty()) {
                parameters.add(mapper.mapWithDefect(p, defect));
            } else {
                Long id = ids.get(0);
                parameters.add(mapper.mapToUpdateCalculatedParameter(parametersDb.get(id), p));
                ids.remove(id);
            }
        });
        if (!ids.isEmpty()) {
            repository.deleteAllById(ids);
        }
        repository.saveAll(parameters);
    }

    @Override
    public void saveForRepair(Set<CompletedRepair> repairs
                            , CalculatedRepair repair
                            , ParameterCalculationType calculation) {
        List<CalculatedParameter> parameters = createParametersByRepair(repairs, calculation);
        repository.saveAll(parameters.stream()
                .map(p -> mapper.mapWithRepair(p, repair))
                .toList());
    }

    @Override
    public void updateForRepair(Set<CompletedRepair> repairs
                              , CalculatedRepair repair
                              , ParameterCalculationType calculation) {
        Map<Long, CalculatedParameter> parametersDb = convertParameters(repair.getParameters());
        List<Long> ids = getIds(repair.getParameters());
        List<CalculatedParameter> parameters = new ArrayList<>();
        createParametersByRepair(repairs, calculation).forEach(p -> {
            if (ids.isEmpty()) {
                parameters.add(mapper.mapWithRepair(p, repair));
            } else {
                Long id = ids.get(0);
                parameters.add(mapper.mapToUpdateCalculatedParameter(parametersDb.get(id), p));
                ids.remove(id);
            }
        });
        if (!ids.isEmpty()) {
            repository.deleteAllById(ids);
        }
        repository.saveAll(parameters);
    }

    private List<Long> getIds(Set<CalculatedParameter> parameters) {
        return parameters.stream()
                         .map(CalculatedParameter::getId)
                         .toList();
    }

    private Map<Long, CalculatedParameter> convertParameters(Set<CalculatedParameter> parameters) {
        return parameters.stream()
                         .collect(Collectors.toMap(CalculatedParameter::getId, p -> p));
    }

    private List<CalculatedParameter> createParametersByDefect(Set<IdentifiedDefect> identifiedDefects
                                                             , ParameterCalculationType calculation) {
        List<CalculatedParameter> parameters = new ArrayList<>();
        int measurementNumber = 1;
        if (calculation.equals(ParameterCalculationType.QUANTITY)) {
            parameters.addAll(calculationService.countQuantityDefect(identifiedDefects));
        } else {
            for (IdentifiedDefect defect : identifiedDefects) {
                parameters.addAll(set(calculation(defect.getParameterMeasurements(), calculation), measurementNumber));
                if (defect.getQuantity() != null && defect.getQuantity() > 1) {
                    parameters.add(calculationService.createQuantityParameter(defect.getQuantity()));
                }
                measurementNumber++;
            }
        }
        return parameters;
    }

    private List<CalculatedParameter> createParametersByRepair(Set<CompletedRepair> repairs
                                                             , ParameterCalculationType calculation) {
        List<CalculatedParameter> parameters = new ArrayList<>();
        int measurementNumber = 1;
        if (calculation.equals(ParameterCalculationType.QUANTITY)) {
            parameters.addAll(calculationService.countQuantityRepair(repairs));
        } else {
            for (CompletedRepair repair : repairs) {
                parameters.addAll(set(calculation(repair.getParameterMeasurements(), calculation), measurementNumber));
                if (repair.getQuantity() != null && repair.getQuantity() > 1) {
                    parameters.add(calculationService.createQuantityParameter(repair.getQuantity()));
                }
                measurementNumber++;
            }
        }
        return parameters;
    }

    private List<CalculatedParameter> calculation(Set<ParameterMeasurement> parameters, ParameterCalculationType calculation) {
        switch (calculation) {
            case NO_ACTION -> {
                return parameters.stream().map(mapper::mapToCalculatedParameter).toList();
            }
            case SQUARE -> {
                return calculationService.countSquare(parameters);
            }
            case MIN -> {
                return calculationService.countMin(parameters);
            }
            case MAX -> {
                return calculationService.countMax(parameters);
            }
            case MAX_MIN -> {
                return calculationService.countMaxMin(parameters);
            }
            default ->
                    throw new NotFoundException(String.format("Unknown type=%s calculation parameters", calculation));
        }
    }

    private List<CalculatedParameter> set(List<CalculatedParameter> parameters, Integer measurementNumber) {
        int sequentialNumber = 1;
        String square = measurementService.get(String.valueOf(MeasuredParameterType.SQUARE));
        String quantity = measurementService.get(String.valueOf(MeasuredParameterType.QUANTITY));
        int size = parameters.size();
        for (CalculatedParameter parameter : parameters) {
            if (parameter.getMeasurementNumber() == null) {
                if (parameter.getParameterName().equals(square)) {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, sequentialNumber);
                    sequentialNumber++;
                } else if (parameter.getParameterName().equals(quantity)) {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, size);
                } else {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, sequentialNumber);
                    sequentialNumber++;
                }
            }
        }
        return parameters;
    }
}