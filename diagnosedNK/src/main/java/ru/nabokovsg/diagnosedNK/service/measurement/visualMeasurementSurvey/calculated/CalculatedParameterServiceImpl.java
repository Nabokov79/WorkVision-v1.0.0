package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedParameterRepository;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation.CalculateMeasurementVMSService;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatedParameterServiceImpl implements CalculatedParameterService {

    private final CalculatedParameterRepository repository;
    private final CalculatedParameterMapper mapper;
    private final ConstParameterMeasurementService measurementService;
    private final CalculateMeasurementVMSService calculateMeasurementService;
    private final static Integer MEASUREMENT = 1;

    @Override
    public void save(CalculatedParameterData parameterData) {
        log.info("-------START Calculated  Parameters ----------");
        Map<String, CalculatedParameter> parameters = new HashMap<>();
        log.info("Set<IdentifiedDefect> defects = {}", parameterData.getDefects());
        log.info("CalculatedDefect defect = {}", parameterData.getDefect());
        log.info("Set<CompletedRepair> repairs = {}", parameterData.getRepairs());
        log.info("CalculatedRepair repair = {}", parameterData.getRepair());
        log.info("ParameterCalculationType calculationType = {}", parameterData.getCalculationType());
        if (parameterData.getDefects() != null && parameterData.getRepairs() == null) {
            log.info("  ");
            log.info("-------START Calculated Defect Parameters ----------");
            calculateByDefect(parameters, parameterData);
        } else if (parameterData.getRepairs() != null && parameterData.getDefects() == null) {
            log.info("  ");
            log.info("-------START Calculated Repair Parameters ----------");
            calculateByRepair(parameters, parameterData);
        } else {
            throw new BadRequestException(
                    String.format("Defects=%s and Repairs=%s not be null", parameterData.getDefects()
                            , parameterData.getRepairs()));
        }
        repository.saveAll(parameters.values());
        log.info("Map<String, CalculatedParameter> parameters = {}", parameters);
        log.info("  ");
        log.info("-------END Calculated Parameters ----------");
    }

    private void calculateByDefect(Map<String, CalculatedParameter> parameters, CalculatedParameterData parameterData) {
        int measurementNumber = MEASUREMENT;
        switch (parameterData.getCalculationType()) {
            case SQUARE -> parameterData.getDefects().forEach(defect ->
                    calculateMeasurementService.calculation(parameters
                                                          , defect.getParameterMeasurements()
                                                          , parameterData.getCalculationType()));
            case MIN, MAX, MAX_MIN ->
                    calculateMeasurementService.calculation(parameters
                                                          , parameterData.getDefects()
                                                                        .stream()
                                                                        .map(IdentifiedDefect::getParameterMeasurements)
                                                                        .flatMap(Collection::stream)
                                                                        .collect(Collectors.toSet())
                                                          , parameterData.getCalculationType());
            default ->  throw new NotFoundException(String.format("Defect calculation type=%s not supported"
                                                                                , parameterData.getCalculationType()));
        }
        setSequentialParameterNumber(parameters.values().stream().toList(), measurementNumber);
        mapWithDefect(parameters, parameterData);
    }

    private void calculateByRepair(Map<String, CalculatedParameter> parameters, CalculatedParameterData parameterData) {
        int measurementNumber = MEASUREMENT;
        switch (parameterData.getCalculationType()) {
            case SQUARE -> parameterData.getRepairs().forEach(repair ->
                    calculateMeasurementService.calculation(parameters
                                                          , repair.getParameterMeasurements()
                                                          , parameterData.getCalculationType()));
            case MIN, MAX, MAX_MIN ->
                    calculateMeasurementService.calculation(parameters
                                                          , parameterData.getRepairs()
                                                                         .stream()
                                                                         .map(CompletedRepair::getParameterMeasurements)
                                                                         .flatMap(Collection::stream)
                                                                         .collect(Collectors.toSet())
                                                        , parameterData.getCalculationType());
            default ->  throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
        setSequentialParameterNumber(parameters.values().stream().toList(), measurementNumber);
        mapWithRepair(parameters, parameterData);
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