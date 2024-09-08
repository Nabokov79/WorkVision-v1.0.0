package ru.nabokovsg.diagnosedNK.service.norms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.norms.measuredParameter.MeasuredParameterDto;
import ru.nabokovsg.diagnosedNK.mapper.norms.MeasuredParameterMapper;
import ru.nabokovsg.diagnosedNK.model.norms.*;
import ru.nabokovsg.diagnosedNK.repository.norms.MeasuredParameterRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeasuredParameterServiceImpl implements MeasuredParameterService {

    private final MeasuredParameterRepository repository;
    private final MeasuredParameterMapper mapper;

    @Override
    public Set<MeasuredParameter> saveForDefect(Defect defect, List<MeasuredParameterDto> parametersDto) {
        return new HashSet<>(repository.saveAll(mapMeasuredParameterDto(parametersDto)
                                       .stream()
                                       .map(p -> mapper.mapWithDefect(p, defect))
                                       .toList()));
    }

    @Override
    public Set<MeasuredParameter> saveForElementRepair(ElementRepair repair, List<MeasuredParameterDto> parametersDto) {
        return new HashSet<>(repository.saveAll(mapMeasuredParameterDto(parametersDto)
                                       .stream()
                                       .map(p -> mapper.mapWithElementRepair(p, repair))
                                       .toList()));
    }

    private List<MeasuredParameter> mapMeasuredParameterDto(List<MeasuredParameterDto> parametersDto) {
        return parametersDto.stream()
                            .map(mapper::mapToMeasuredParameter)
                            .toList();
    }

    @Override
    public Set<MeasuredParameter> update(List<MeasuredParameterDto> parametersDto) {
        Map<Long, MeasuredParameter> parameters = repository.findAllById(parametersDto.stream()
                                                                                      .map(MeasuredParameterDto::getId)
                                                                                      .toList())
                                                           .stream()
                                                           .collect(Collectors.toMap(MeasuredParameter::getId, p -> p));
        Map<Long, MeasuredParameterDto> updateParametersDto = parametersDto.stream()
                                                        .collect(Collectors.toMap(MeasuredParameterDto::getId, p -> p));
        compareParameter(parameters,updateParametersDto);
        if (parameters.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(repository.saveAll(
                parameters.values().stream()
                        .map(p -> mapper.mapToUpdateMeasuredParameter(p, updateParametersDto.get(p.getId())))
                        .toList())
        );
    }

    private void delete(List<Long> ids) {
        repository.deleteAllById(ids);
    }

    private void compareParameter(Map<Long, MeasuredParameter> parameters, Map<Long, MeasuredParameterDto> parametersDto) {
        List<Long> ids = new ArrayList<>();
        parameters.forEach((k, v) -> {
            MeasuredParameterDto parameter = parametersDto.get(k);
            if (parameter == null) {
                ids.add(k);
                parameters.remove(k);
            }
        });
        if (!ids.isEmpty()) {
            delete(ids);
        }
    }
}