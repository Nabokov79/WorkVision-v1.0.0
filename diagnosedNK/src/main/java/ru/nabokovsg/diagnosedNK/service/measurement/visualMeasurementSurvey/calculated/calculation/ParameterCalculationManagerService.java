package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParameterCalculationManagerService {

    private final CalculateMeasurementVMSService calculateMeasurementService;

    public void cal(CalculatedParameterData parameterData) {
        Map<String, CalculatedParameter> parameters = new HashMap<>();
        switch (parameterData.getCalculationType()) {
            case SQUARE -> calculateParameter(parameterData);
            case MIN, MAX, MAX_MIN -> calculateAllParameter(parameterData);
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    private void calculateAllParameter(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        if (flag) {
            calculateMeasurementService.calculation(parameters, getAllDefectsParameters(parameterData), parameterData.getCalculationType());
        } else {
            calculateMeasurementService.calculation(parameters, getAllRepairsParameters(parameterData), parameterData.getCalculationType());
        }
    }

    private Set<ParameterMeasurement> getAllDefectsParameters(CalculatedParameterData parameterData) {
        return parameterData.getDefects()
                .stream()
                .map(IdentifiedDefect::getParameterMeasurements)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<ParameterMeasurement> getAllRepairsParameters(CalculatedParameterData parameterData) {
        return parameterData.getRepairs()
                .stream()
                .map(CompletedRepair::getParameterMeasurements)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private boolean getFlag(CalculatedParameterData parameterData) {
        if (parameterData.getDefects() != null) {
            return true;
        } else if (parameterData.getRepairs() != null) {
            return false;
        }
        throw new NotFoundException
                (String.format("Parameters measurement for calculated non found: defects=%s, repairs=%s"
                                                , parameterData.getDefects(), parameterData.getRepairs()));
    }

    private Set<ParameterMeasurement> calculateParameter(CalculatedParameterData parameterData) {
        if (flag) {
            return parameterData.getDefects()
                    .stream()
                    .map(IdentifiedDefect::getParameterMeasurements)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        } else {
            return parameterData.getDefects().getParameterMeasurements();
        }
    }

    private Set<ParameterMeasurement> calculationAll(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        boolean flag = getFlag(parameterData);
        calculateMeasurementService.calculation(parameters
                , measurements
                , parameterData.getCalculationType());
    }

    private Set<ParameterMeasurement> calculation(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        calculateMeasurementService.calculation(parameters
                , measurements
                , parameterData.getCalculationType());
    }



    private Set<ParameterMeasurement> calculateByDefect(CalculatedParameterData parameterData, boolean flag) {
        if (flag) {
            return parameterData.getDefects()
                    .stream()
                    .map(IdentifiedDefect::getParameterMeasurements)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        } else {
            return parameterData.getDefects().getParameterMeasurements();
        }
    }

    private Set<ParameterMeasurement> calculateByRepair(CalculatedParameterData parameterData, boolean flag) {
        Map<String, CalculatedParameter> parameters = new HashMap<>();
        if (flag) {
            calculateMeasurementService.calculation(parameters
                    , parameterData.getRepairs()
                            .stream()
                            .map(CompletedRepair::getParameterMeasurements)
                            .flatMap(Collection::stream)
                            .collect(Collectors.toSet())
                    , parameterData.getCalculationType());
        } else {
            parameterData.getRepairs().forEach(repair ->
                    calculateMeasurementService.calculation(parameters
                            , repair.getParameterMeasurements()
                            , parameterData.getCalculationType()));
        }
    }



    private Set<ParameterMeasurement> getAllParameterMeasurements(CalculatedParameterData parameterData, boolean flag) {
        if (flag) {
            return parameterData.getDefects()
                    .stream()
                    .map(IdentifiedDefect::getParameterMeasurements)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        } else {
            return parameterData.getRepairs()
                    .stream()
                    .map(CompletedRepair::getParameterMeasurements)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        }
    }

    private Set<ParameterMeasurement> getParameterMeasurements(CalculatedParameterData parameterData) {
        if (parameterData.getDefects() != null && parameterData.getRepairs() == null) {
            calculateByDefect(parameters, parameterData);
        } else if (parameterData.getRepairs() != null && parameterData.getDefects() == null) {
            calculateByRepair(parameters, parameterData);
        } else {
            throw new BadRequestException(
                    String.format("Defects=%s and Repairs=%s not be null", parameterData.getDefects()
                            , parameterData.getRepairs()));
        }
    }
}