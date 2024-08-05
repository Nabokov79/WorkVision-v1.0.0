package ru.nabokovsg.documentNK.mapper.document.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.documentNK.model.document.common.DocumentMeasuringTool;
import ru.nabokovsg.documentNK.model.document.common.Subsection;

@Mapper(componentModel = "spring")
public interface DocumentMeasuringToolMapper {

    @Mapping(source = "sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "measuringTool", target = "measuringTool")
    @Mapping(target = "subsection", ignore = true)
    @Mapping(target = "id", ignore = true)
    DocumentMeasuringTool mapToDocumentMeasuringTool(Integer sequentialNumber
                                                   , String measuringTool);

    @Mapping(source = "subsection", target = "subsection")
    @Mapping(target = "id", ignore = true)
    DocumentMeasuringTool mapWithSubsection(@MappingTarget DocumentMeasuringTool toll, Subsection subsection);
}