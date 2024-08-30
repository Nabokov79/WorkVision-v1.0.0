package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedParameterRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatedParameterServiceImpl implements CalculatedParameterService {

    private final CalculatedParameterRepository repository;
    private final CalculatedParameterMapper mapper;
    private final CalculationParameterService calculationParameterService;
    private final SearchDuplicateParameterService searchDuplicateService;

    @Override
    public void save(CalculatedParameterData parameterData) {
        Map<String, CalculatedParameter> parameters = new HashMap<>();
        if(parameterData.getDefects() != null && parameterData.getRepairs() == null) {
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

    @Override
    public void update(List<CalculatedParameter> parameters) {
        repository.saveAll(parameters);
    }

    private void calculateByDefect(Map<String, CalculatedParameter> parameters, CalculatedParameterData parameterData) {
        int measurementNumber = 1;
        List<Integer> quantity = parameterData.getDefects().stream().map(IdentifiedDefect::getQuantity).toList();
        for (IdentifiedDefect defect : parameterData.getDefects()) {
           searchDuplicateService.search(parameters,
                                         calculationParameterService.calculation(defect.getParameterMeasurements()
                                                                               , parameterData.getCalculationType()
                                                                               , measurementNumber
                                                                               , defect.getQuantity()
                                                                               , quantity));
            measurementNumber++;
        }
    }

    public void calculateByRepair(Map<String, CalculatedParameter> parameters, CalculatedParameterData parameterData) {
        int measurementNumber = 1;
        List<Integer> quantity = parameterData.getRepairs().stream().map(CompletedRepair::getQuantity).toList();
        for (CompletedRepair repair :  parameterData.getRepairs()) {
            searchDuplicateService.search(parameters
                                      ,   calculationParameterService.calculation(repair.getParameterMeasurements()
                                                                                , parameterData.getCalculationType()
                                                                                , measurementNumber
                                                                                , repair.getQuantity()
                                                                                , quantity));
                measurementNumber++;
        }
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
}