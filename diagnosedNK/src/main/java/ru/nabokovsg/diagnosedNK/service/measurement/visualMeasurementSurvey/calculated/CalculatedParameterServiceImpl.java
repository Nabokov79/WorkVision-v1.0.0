package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;

import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedParameterRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculatedParameterServiceImpl implements CalculatedParameterService {

    private final CalculatedParameterRepository repository;
    private final CalculatedParameterMapper mapper;
    private final CalculationParameterService factory;

    @Override
    public void saveForDefect(Set<IdentifiedDefect> defects
            , CalculatedDefect defect
            , ParameterCalculationType calculation) {
        List<CalculatedParameter> parameters = factory.calculateByDefect(defects, calculation);
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
        factory.calculateByDefect(defects, calculation).forEach(p -> {
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
        List<CalculatedParameter> parameters = factory.calculateByRepair(repairs, calculation);
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
        factory.calculateByRepair(repairs, calculation).forEach(p -> {
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
}