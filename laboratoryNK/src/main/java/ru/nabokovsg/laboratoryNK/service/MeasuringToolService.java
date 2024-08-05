package ru.nabokovsg.laboratoryNK.service;

import ru.nabokovsg.laboratoryNK.dto.measuringTool.MeasuringToolDto;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.ResponseMeasuringToolDto;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.SearchParameters;
import ru.nabokovsg.laboratoryNK.model.MeasuringTool;

import java.util.List;

public interface MeasuringToolService {

    ResponseMeasuringToolDto save(MeasuringToolDto measuringToolDto);

    ResponseMeasuringToolDto update(MeasuringToolDto measuringToolDto);

    ResponseMeasuringToolDto get(Long id);

    List<ResponseMeasuringToolDto> getAll(SearchParameters parameters);

    List<MeasuringTool> getAllLaboratoryEmployeeId(List<Long>  employeesIds);

    void delete(Long id);
}