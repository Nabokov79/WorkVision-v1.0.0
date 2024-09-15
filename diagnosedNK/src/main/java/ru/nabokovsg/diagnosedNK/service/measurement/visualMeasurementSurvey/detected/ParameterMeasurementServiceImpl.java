package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected.ParameterMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementControl.VisualMeasurementControl;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected.ParameterMeasurementRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParameterMeasurementServiceImpl implements ParameterMeasurementService {

    private final ParameterMeasurementRepository repository;
    private final ParameterMeasurementMapper mapper;

    @Override
    public Set<ParameterMeasurement> saveForIdentifiedDefect(IdentifiedDefect defect
                                                           , Set<MeasuredParameter> parameters
                                                           , List<ParameterMeasurementDto> parametersDto) {
        if (defect.getParameterMeasurements() != null) {
            return new HashSet<>(
                    repository.saveAll(sumQuantity(parametersDto, defect.getParameterMeasurements()))
            );
        }
        return new HashSet<>(repository.saveAll(map(parametersDto, parameters)
                                                                    .stream()
                                                                    .map(p -> mapper.mapWithIdentifiedDefect(p, defect))
                                                                    .toList())
        );
    }

    @Override
    public Set<ParameterMeasurement> saveForCompletedRepair(CompletedRepair repair
                                                          , Set<MeasuredParameter> parameters
                                                          , List<ParameterMeasurementDto> parametersDto) {
        return new HashSet<>(repository.saveAll(map(parametersDto, parameters)
                                                                    .stream()
                                                                    .map(p -> mapper.mapWithCompletedRepair(p, repair))
                                                                    .toList())
        );
    }

    @Override
    public Set<ParameterMeasurement> saveForVMControl(VisualMeasurementControl vmControl
                                                    , Set<MeasuredParameter> parameters
                                                    , List<ParameterMeasurementDto> parametersDto) {
        return new HashSet<>(
                repository.saveAll(map(parametersDto, parameters)
                                                .stream()
                                                .map(p -> mapper.mapWithVisualMeasurementControl(p, vmControl))
                                                .toList())
        );
    }

    @Override
    public Set<ParameterMeasurement> update(Set<ParameterMeasurement> parameters
                                          , List<ParameterMeasurementDto> parametersDto) {
        Map<Long, ParameterMeasurement> parametersDb = parameters.stream()
                                               .collect(Collectors.toMap(ParameterMeasurement::getParameterId, p -> p));
        return new HashSet<>(
                repository.saveAll(parametersDto.stream()
                        .map(p -> mapper.mapToUpdateParameterMeasurement(parametersDb.get(p.getParameterId()), p))
                        .toList())
        );
    }

    @Override
    public void deleteAll(Set<ParameterMeasurement> parameters) {
        repository.deleteAll(parameters);
    }

    private List<ParameterMeasurement> sumQuantity(List<ParameterMeasurementDto> parametersDto
                                                 , Set<ParameterMeasurement> parametersDb) {
        Map<Long, ParameterMeasurementDto> parameters = parametersDto
                .stream()
                .collect(Collectors.toMap(ParameterMeasurementDto::getParameterId, p -> p));
        parametersDb.forEach(p -> {
            if (p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                ParameterMeasurementDto parameter = parameters.get(p.getParameterId());
                p.setValue(p.getValue() + parameter.getValue());
            }
        });
        return new ArrayList<>(parametersDb);
    }

    @Override
    public List<ParameterMeasurement> map(List<ParameterMeasurementDto> parametersDto
                                        , Set<MeasuredParameter> parameters) {
        Map<Long, MeasuredParameter> measuredParameter = parameters.stream()
                .collect(Collectors.toMap(MeasuredParameter::getId, m -> m));
        return parametersDto.stream()
                            .map(p -> mapper.mapToParameterMeasurement(p, measuredParameter.get(p.getParameterId())))
                            .toList();
    }

    @Override
    public boolean searchParameterDuplicate(Set<ParameterMeasurement> parameterMeasurements
                                          , Map<String, ParameterMeasurement> parameters) {
        String quantityName = MeasuredParameterType.valueOf("QUANTITY").label;
        int coincidences = 0;
        for (ParameterMeasurement parameterDb : parameterMeasurements) {
            ParameterMeasurement parameter = parameters.get(parameterDb.getParameterName());
            if (parameter != null) {
                if (parameter.getParameterName().equals(quantityName) || parameterDb.getValue().equals(parameter.getValue())) {
                    coincidences++;
                }
            }
        }
        if (coincidences == parameters.size()) {
            ParameterMeasurement parameter = parameters.get(quantityName);
            parameterMeasurements.forEach(v -> {
                if (v.getParameterName().equals(quantityName)) {
                   mapper.mapToUpdateQuantity(v, v.getValue() + parameter.getValue());
                }
            });
            return true;
        }
        return false;
    }
}