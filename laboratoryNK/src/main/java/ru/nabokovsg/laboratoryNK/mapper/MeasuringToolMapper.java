package ru.nabokovsg.laboratoryNK.mapper;

import org.mapstruct.Mapper;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.MeasuringToolDto;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.ResponseMeasuringToolDto;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.SearchParameters;
import ru.nabokovsg.laboratoryNK.model.MeasuringTool;

@Mapper(componentModel = "spring")
public interface MeasuringToolMapper {

    MeasuringTool mapToMeasuringTool(MeasuringToolDto measuringToolDto);

    ResponseMeasuringToolDto mapToResponseMeasuringToolDto(MeasuringTool newMeasuringTool);

    SearchParameters mapToRequestParameters(MeasuringToolDto measuringToolDto);
}