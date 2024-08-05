package ru.nabokovsg.documentNK.mapper.document.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;

@Mapper(componentModel = "spring")
public interface CellTableMapper {

    @Mapping(source = "table", target = "table")
    @Mapping(target = "id", ignore = true)
    CellTable mapToCellTable(@MappingTarget CellTable cell,  DocumentTable table);

    @Mapping(source = "mergeLines", target = "mergeLines")
    @Mapping(source = "columnSequentialNumber", target = "columnSequentialNumber")
    @Mapping(source = "stringSequentialNumber", target = "stringSequentialNumber")
    @Mapping(source = "cellValue", target = "cellValue")
    @Mapping(target = "table", ignore = true)
    @Mapping(target = "id", ignore = true)
    CellTable mapToCell(Integer mergeLines, Integer columnSequentialNumber,  Integer stringSequentialNumber, String cellValue);
}
