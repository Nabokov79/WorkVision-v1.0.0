package ru.nabokovsg.documentNK.service.template.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.template.common.columnHeader.ColumnHeaderTemplateDto;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.template.common.ColumnHeaderTemplateMapper;
import ru.nabokovsg.documentNK.model.template.common.ColumnHeaderTemplate;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;
import ru.nabokovsg.documentNK.repository.template.common.ColumnHeaderTemplateRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColumnHeaderTemplateServiceImpl implements ColumnHeaderTemplateService {

    private final ColumnHeaderTemplateRepository repository;
    private final ColumnHeaderTemplateMapper mapper;

    @Override
    public Set<ColumnHeaderTemplate> save(TableTemplate tableTemplate
                                         , List<ColumnHeaderTemplateDto> columnHeaderTemplatesDto) {
        return new HashSet<>(repository.saveAll(
                columnHeaderTemplatesDto.stream()
                        .map(t -> mapper.mapToColumnHeaderTemplates(tableTemplate, t
                                , t.getHeading()
                                , t.getColumnHeaderType()))
                        .toList()));
    }

    @Override
    public Set<ColumnHeaderTemplate> update(TableTemplate tableTemplate
                                           , List<ColumnHeaderTemplateDto> columnHeaderTemplatesDto) {
        validateIds(columnHeaderTemplatesDto.stream()
                                           .map(ColumnHeaderTemplateDto::getId)
                                           .toList());
        return new HashSet<>(repository.saveAll(
                columnHeaderTemplatesDto.stream()
                        .map(t -> mapper.mapToColumnHeaderTemplates(tableTemplate, t
                                , t.getHeading()
                                , t.getColumnHeaderType()))
                        .toList()));
    }

    private void validateIds(List<Long> ids) {
        Map<Long, ColumnHeaderTemplate> columnHeaderTemplate = repository.findAllById((ids))
                .stream().collect(Collectors.toMap(ColumnHeaderTemplate::getId, m -> m));
        if (columnHeaderTemplate.size() != ids.size() || columnHeaderTemplate.isEmpty()) {
            List<Long> idsDb = new ArrayList<>(columnHeaderTemplate.keySet());
            ids = ids.stream().filter(e -> !idsDb.contains(e)).collect(Collectors.toList());
            throw new NotFoundException(String.format("Column header template with ids= %s not found", ids));

        }
    }
}