package ru.nabokovsg.documentNK.service.template.common;

import ru.nabokovsg.documentNK.dto.template.common.columnHeader.ColumnHeaderTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.ColumnHeaderTemplate;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;

import java.util.List;
import java.util.Set;

public interface ColumnHeaderTemplateService {

    Set<ColumnHeaderTemplate> save(TableTemplate tableTemplate, List<ColumnHeaderTemplateDto> columnHeaderTemplatesDto);

    Set<ColumnHeaderTemplate> update(TableTemplate tableTemplate, List<ColumnHeaderTemplateDto> columnHeaderTemplatesDto);
}