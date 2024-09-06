package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedParameterRepository;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstUnitMeasurementService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation.CalculationParameterService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculatedParameterServiceImpl implements CalculatedParameterService {

    private final CalculatedParameterRepository repository;
    private final CalculatedParameterMapper mapper;
    private final CalculationParameterService calculationService;
    private final ConstParameterMeasurementService measurementService;
    private final ConstParameterMeasurementService constParameter;
    private final ConstUnitMeasurementService constUnit;
    private final static Integer MEASUREMENT = 1;

    @Override
    public void save(CalculatedParameterData parameterData) {
        Map<String, CalculatedParameter> parameters = new HashMap<>();
        if (parameterData.getDefects() != null && parameterData.getRepairs() == null) {
            calculateByDefect(parameters, parameterData);
        } else if (parameterData.getRepairs() != null && parameterData.getDefects() == null) {
            calculateByRepair(parameters, parameterData);
        } else {
            throw new BadRequestException(
                    String.format("Defects=%s and Repairs=%s not be null", parameterData.getDefects()
                            , parameterData.getRepairs()));
        }
        repository.saveAll(parameters.values());
    }

    private void calculateByDefect(Map<String, CalculatedParameter> parameters, CalculatedParameterData parameterData) {
        int measurementNumber = MEASUREMENT;
        for (IdentifiedDefect defect : parameterData.getDefects()) {
            List<CalculatedParameter> calculatedParameters = calculationService.calculation(defect.getParameterMeasurements()
                    , parameterData.getCalculationType());
            if (!parameters.isEmpty()) {
                compareParameters(parameters, calculatedParameters);
            } else {
                setSequentialParameterNumber(calculatedParameters, measurementNumber);
            }
            measurementNumber++;
        }
        mapWithDefect(parameters, parameterData);
    }

    private void calculateByRepair(Map<String, CalculatedParameter> parameters, CalculatedParameterData parameterData) {
        int measurementNumber = MEASUREMENT;
        for (CompletedRepair repair : parameterData.getRepairs()) {
            List<CalculatedParameter> calculatedParameters =
                                                    calculationService.calculation(repair.getParameterMeasurements()
                                                                                 , parameterData.getCalculationType());
            if (!parameters.isEmpty()) {
                compareParameters(parameters, calculatedParameters);
            }
            setSequentialParameterNumber(calculatedParameters, measurementNumber);
            measurementNumber++;
        }
        mapWithRepair(parameters, parameterData);
    }

    private void compareParameters(Map<String, CalculatedParameter> parameters
                                 , List<CalculatedParameter> calculatedParameters) {
        String quantity = measurementService.get(String.valueOf(MeasuredParameterType.QUANTITY));
        Map<String, CalculatedParameter> newParameters = calculatedParameters.stream()
                                              .collect(Collectors.toMap(CalculatedParameter::getParameterName, p -> p));
        Map<Integer, Integer> coincidences = getNumberOfMatches(parameters, newParameters);
        int size = calculatedParameters.size();
        parameters.forEach((k, v) -> {
            Integer coincidence = coincidences.get(v.getMeasurementNumber());
            if (coincidence == size) {
                CalculatedParameter parameter = parameters.get(quantity);
                CalculatedParameter newParameter = newParameters.get(quantity);
                if (parameter == null) {
                    if (newParameter == null) {
                        parameter = createQuantityParameter();
                    } else {
                        parameter = newParameter;
                        parameter.setMinValue(newParameter.getMinValue() + 1.0);
                    }
                } else {
                    if (newParameter == null) {
                        parameter.setMinValue(parameter.getMinValue() + 1.0);
                    } else {
                        parameter.setMinValue(parameter.getMinValue() + newParameter.getMinValue());
                    }
                }
                parameters.put(parameter.getParameterName(), parameter);
            }
        });
    }

    private Map<Integer, Integer> getNumberOfMatches(Map<String, CalculatedParameter> parameters
            , Map<String, CalculatedParameter> calculatedParameters) {
        Map<Integer, Integer> coincidences = new HashMap<>();
        parameters.forEach((k, v) -> {
            CalculatedParameter parameter = calculatedParameters.get(v.getParameterName());
            if (parameter != null && Objects.equals(parameter.getMinValue(), v.getMinValue())) {
                coincidences.merge(v.getMeasurementNumber(), 1, Integer::sum);
            }
        });
        return coincidences;
    }

    private List<CalculatedParameter> convertParameters(Set<CalculatedParameter> parameters) {
        List<CalculatedParameter> parametersDb = new ArrayList<>();
        if (parameters != null) {
            parametersDb.addAll(parameters);
        }
        return parametersDb;
    }

    private void setSequentialParameterNumber(List<CalculatedParameter> calculatedParameters
            , Integer measurementNumber) {
        int sequentialNumber = 1;
        String square = measurementService.get(String.valueOf(MeasuredParameterType.SQUARE));
        String quantity = measurementService.get(String.valueOf(MeasuredParameterType.QUANTITY));
        int size = calculatedParameters.size();
        for (CalculatedParameter parameter : calculatedParameters) {
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
    }
    private CalculatedParameter createQuantityParameter() {
        return mapper.mapToQuantity(constParameter.get(String.valueOf(MeasuredParameterType.QUANTITY))
                , constUnit.get(String.valueOf(UnitMeasurementType.PIECES))
                , 2);
    }

    private void mapWithDefect(Map<String, CalculatedParameter> parameters, CalculatedParameterData parameterData) {
        Map<String, CalculatedParameter> parametersDb = convertParameters(parameterData.getDefect()
                .getParameters())
                .stream()
                .collect(Collectors.toMap(CalculatedParameter::getParameterName, p -> p));
        parameters.forEach((k, v) -> parameters.put(k, mapper.mapWithDefect(v, parametersDb.get(v.getParameterName())
                , parameterData.getDefect())));
    }

    private void mapWithRepair(Map<String, CalculatedParameter> parameters, CalculatedParameterData parameterData) {
        Map<String, CalculatedParameter> parametersDb = convertParameters(parameterData.getRepair()
                .getParameters())
                .stream()
                .collect(Collectors.toMap(CalculatedParameter::getParameterName, p -> p));
        parameters.forEach((k, v) -> parameters.put(k, mapper.mapWithDefect(v, parametersDb.get(v.getParameterName())
                , parameterData.getDefect())));
    }
}