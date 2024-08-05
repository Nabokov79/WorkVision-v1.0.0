package ru.nabokovsg.documentNK.service.document.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.mapper.document.common.SubsectionMapper;
import ru.nabokovsg.documentNK.model.document.common.Subsection;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.ProtocolReport;
import ru.nabokovsg.documentNK.model.document.report.Section;
import ru.nabokovsg.documentNK.model.template.common.SubsectionTemplate;
import ru.nabokovsg.documentNK.repository.document.common.SubsectionRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubsectionServiceImpl implements SubsectionService {

    private final SubsectionRepository repository;
    private final SubsectionMapper mapper;
    private final RegulatoryDocumentationService documentationService;
    private final DocumentMeasuringToolService measuringToolService;
    private final TableService tableService;

    @Override
    public void saveForSection(Section section, Set<SubsectionTemplate> templates, DocumentCreationDataDto documentDataDto) {
        Map<String, SubsectionTemplate> subsectionTemplates = new HashMap<>();
        List<Subsection> subsections =
        templates.stream()
                 .map(template -> {
                                    Subsection subsection = mapper.mapWithSection(template, section);
                                    if (template.getTableTemplate() != null) {
                                        subsection = mapper.mapWithDocumentTable(subsection, tableService.saveForSubsection(template.getTableTemplate(), documentDataDto));
                                    }
                                    if (template.getDocumentationTemplate() != null || template.getMeasuringToolsTemplates() != null) {
                                        subsectionTemplates.put(section.getSectionName(), template);
                                    }
                                    return subsection;
                                })
                .toList();
        Map<String, Subsection> subsectionsDb = repository.saveAll(subsections).stream().collect(Collectors.toMap(Subsection::getSubsectionName, s -> s));
        if (!subsectionTemplates.isEmpty()) {
            subsectionTemplates.forEach((k, v) -> common(subsectionsDb.get(k), v, documentDataDto));
        }
    }

    @Override
    public void saveForProtocolReport(ProtocolReport protocol, Set<SubsectionTemplate> templates, DocumentCreationDataDto documentDataDto) {
        Map<String, SubsectionTemplate> subsectionTemplates = templates.stream().collect(Collectors.toMap(SubsectionTemplate::getSubsectionName, s -> s));
        repository.saveAll(templates.stream()
                .map(s -> mapper.mapWithProtocolReport(s, protocol))
                .toList())
                .forEach(subsection ->
                    common(subsection, subsectionTemplates.get(subsection.getSubsectionName()), documentDataDto)
       );
    }

    @Override
    public void saveForProtocolControl(ProtocolControl protocol, Set<SubsectionTemplate> templates, DocumentCreationDataDto documentDataDto) {
        Map<String, SubsectionTemplate> subsectionTemplates = templates.stream().collect(Collectors.toMap(SubsectionTemplate::getSubsectionName, s -> s));
        repository.saveAll(templates.stream()
                        .map(s -> mapper.mapWithProtocolControl(s, protocol))
                        .toList())
                .forEach(subsection ->
                        common(subsection, subsectionTemplates.get(subsection.getSubsectionName()), documentDataDto)
                );
    }

    @Override
    public void saveForSurveyProtocol(SurveyProtocol protocol, Set<SubsectionTemplate> templates, DocumentCreationDataDto documentDataDto) {
        Map<String, SubsectionTemplate> subsectionTemplates = templates.stream().collect(Collectors.toMap(SubsectionTemplate::getSubsectionName, s -> s));
        repository.saveAll(templates.stream()
                        .map(s -> mapper.mapWithSurveyProtocol(s, protocol))
                        .toList())
                .forEach(subsection ->
                        common(subsection, subsectionTemplates.get(subsection.getSubsectionName()), documentDataDto)
                );
    }

    private void common(Subsection subsection, SubsectionTemplate template, DocumentCreationDataDto documentDataDto) {
       if (template.getDocumentationTemplate() != null) {
            documentationService.save(subsection, template.getDocumentationTemplate());
        } else if (template.getMeasuringToolsTemplates() != null) {
            measuringToolService.save(subsection, documentDataDto.getEmployees(), template.getMeasuringToolsTemplates());
        }
    }
}