package ru.nabokovsg.documentNK.service.template.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.template.common.table.ResponseTableTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.table.TableTemplateDto;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.template.common.TableTemplateMapper;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;
import ru.nabokovsg.documentNK.model.template.common.TableType;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ProtocolReportTemplate;
import ru.nabokovsg.documentNK.repository.template.common.TableTemplateRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TableTemplateServiceImpl implements TableTemplateService {

    private final TableTemplateRepository repository;
    private final TableTemplateMapper mapper;
    private final ColumnHeaderTemplateService columnHeaderService;

    @Override
    public ResponseTableTemplateDto save(TableTemplateDto tableDto) {
        TableTemplate tableTemplate = repository.save(mapper.mapToTableTemplate(tableDto
                                                                , TableType.valueOf(tableDto.getTableType()).label));
        tableTemplate.setColumnHeaders(columnHeaderService.save(tableTemplate, tableDto.getColumnHeaders()));
        return mapper.mapToResponseTableTemplateDto(tableTemplate);
    }

    @Override
    public ResponseTableTemplateDto update(TableTemplateDto tableDto) {
        if (repository.existsById(tableDto.getId())) {
            TableTemplate tableTemplate = repository.save(mapper.mapToTableTemplate(tableDto
                                                                , TableType.valueOf(tableDto.getTableType()).label));
            tableTemplate.setColumnHeaders(columnHeaderService.update(tableTemplate, tableDto.getColumnHeaders()));
            return mapper.mapToResponseTableTemplateDto(tableTemplate);
        }
       throw new NotFoundException(String.format("Table template with id=%s not found for update", tableDto.getId()));
    }

    @Override
    public ResponseTableTemplateDto get(Long id) {
        return mapper.mapToResponseTableTemplateDto(getById(id));
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Table template with id=%s not found for delete", id));
    }

    @Override
    public void addProtocolReportTemplate(ProtocolReportTemplate template, List<Long> tableTemplatesId) {
        repository.saveAll(getAllByIds(tableTemplatesId)
                  .stream()
                  .map(t -> mapper.mapWithProtocolReportTemplate(t, template))
                  .toList());
    }

    @Override
    public void addProtocolTemplate(SurveyProtocolTemplate template, List<Long> tableTemplatesId) {
        repository.saveAll(getAllByIds(tableTemplatesId)
                .stream()
                .map(t -> mapper.mapWithProtocolTemplate(t, template))
                .toList());
    }

    private Set<TableTemplate> getAllByIds(List<Long> ids) {
        Set<TableTemplate> templates = new HashSet<>(repository.findAllById(ids));
        if (templates.isEmpty()) {
            throw new NotFoundException(String.format("Table template by ids=%s not found", ids));
        }
        return templates;
    }

    @Override
    public TableTemplate getById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Table template with id=%s not found", id)));
    }
}