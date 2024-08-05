package ru.nabokovsg.documentNK.mapper.template.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.dto.template.common.measuringToolTemplate.MeasuringToolTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.measuringToolTemplate.ResponseMeasuringToolTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.MeasuringToolTemplate;
import ru.nabokovsg.documentNK.model.template.common.SubsectionTemplate;

@Mapper(componentModel = "spring")
public interface MeasuringToolTemplateMapper {

    @Mapping(source = "measuringTool.measuringTool", target = "measuringToolId")
    @Mapping(source = "tool", target = "measuringTool")
    @Mapping(source = "measuringTool.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "subsection", target = "subsectionTemplate")
    @Mapping(source = "measuringTool.id", target = "id")
    MeasuringToolTemplate mapToMeasuringToolTemplate(MeasuringToolTemplateDto measuringTool
                                                   , String tool
                                                   , SubsectionTemplate subsection);

    ResponseMeasuringToolTemplateDto mapToResponseMeasuringToolTemplateDto(MeasuringToolTemplate template);
}