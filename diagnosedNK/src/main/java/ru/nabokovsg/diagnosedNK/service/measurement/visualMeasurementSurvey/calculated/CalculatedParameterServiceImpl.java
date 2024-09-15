package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedParameterRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation.ParameterCalculationManagerService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatedParameterServiceImpl implements CalculatedParameterService {

    private final CalculatedParameterRepository repository;
    private final CalculatedParameterMapper mapper;
    private final ParameterCalculationManagerService calculationManagerService;

    @Override
    public void save(CalculatedParameterData parameterData) {
        log.info(" ");
        log.info(" ----------------       Class : CalculatedParameterServiceImpl -----------------------");
        log.info(" ----------------  START save parameters -----------------------");
        Set<CalculatedParameter> parametersDb = getAll(parameterData);
        Map<String, CalculatedParameter> calculatedParameters = calculationManagerService.calculate(parameterData);
        if (parametersDb == null) {
            mapTo(parameterData, calculatedParameters);
            parametersDb = new HashSet<>(calculatedParameters.values());
        } else {
            update(parametersDb, calculatedParameters);
        }
        log.info("Before mapping parametersDb ={}", parametersDb);
        repository.saveAll(parametersDb);
        log.info(" ----------------  END save parameters -----------------------");
    }

    private void mapTo(CalculatedParameterData parameterData, Map<String, CalculatedParameter> calculatedParameters) {
        log.info(" ");
        log.info(" ----------------       START mapping -----------------------");
        switch (parameterData.getTypeData()) {
            case DEFECT -> {
                log.info(" ----------------      mapping with DEFECT -----------------------");
                log.info("INPUT defect ={}", parameterData.getDefect());
                log.info("INPUT calculatedParameters ={}", calculatedParameters.values());
                calculatedParameters.forEach((k,v) -> calculatedParameters.put(k, mapper.mapWithDefect(v, parameterData.getDefect())));
            }
            case REPAIR -> {
                log.info(" ----------------      mapping with REPAIR -----------------------");
                log.info("INPUT defect ={}", parameterData.getRepair());
                log.info("INPUT calculatedParameters ={}", calculatedParameters.values());
                calculatedParameters.forEach((k,v) -> calculatedParameters.put(k, mapper.mapWithRepair(v, parameterData.getRepair())));
            }
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    private Set<CalculatedParameter> getAll(CalculatedParameterData parameterData) {
        switch (parameterData.getTypeData()) {
            case DEFECT -> {
                return parameterData.getDefect().getParameters();
            }
            case REPAIR -> {
                return parameterData.getRepair().getParameters();
            }
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    private void update(Set<CalculatedParameter> parametersDb, Map<String, CalculatedParameter> calculatedParameters) {
        log.info(" ");
        log.info(" ----------------       START update -----------------------");
        log.info("INPUT parametersDb ={}", parametersDb);
        log.info("INPUT calculatedParameters ={}", calculatedParameters.values());
        parametersDb.forEach(
                parameter -> mapper.mapToUpdateCalculatedParameter(parameter
                                                             , calculatedParameters.get(parameter.getParameterName())));
    }
}