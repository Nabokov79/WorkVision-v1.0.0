package ru.nabokovsg.documentNK.mapper.template.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.dto.template.common.columnHeader.ColumnHeaderTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.ColumnHeaderTemplate;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;

@Mapper(componentModel = "spring")
public interface ColumnHeaderTemplateMapper {

    @Mapping(source = "templateDto.id", target = "id")
    @Mapping(source = "templateDto.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "heading", target = "heading")
    @Mapping(source = "columnHeaderType", target = "columnHeaderType")
    @Mapping(source = "tableTemplate", target = "tableTemplate")
    ColumnHeaderTemplate mapToColumnHeaderTemplates(TableTemplate tableTemplate
                                                  , ColumnHeaderTemplateDto templateDto
                                                  , String heading
                                                  , String columnHeaderType);
}