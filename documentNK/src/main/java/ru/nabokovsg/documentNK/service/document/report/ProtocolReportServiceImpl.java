package ru.nabokovsg.documentNK.service.document.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.document.WorkJournalDto;
import ru.nabokovsg.documentNK.mapper.document.report.ProtocolReportMapper;
import ru.nabokovsg.documentNK.mapper.document.reportSurvey.ProtocolReportMapper;
import ru.nabokovsg.documentNK.model.document.report.Section;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ProtocolReportTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;
import ru.nabokovsg.documentNK.repository.document.report.ProtocolReportRepository;
import ru.nabokovsg.documentNK.service.document.common.ConclusionService;
import ru.nabokovsg.documentNK.service.document.common.SubsectionService;
import ru.nabokovsg.documentNK.service.document.common.TableService;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProtocolReportServiceImpl implements ProtocolReportService {

    private final ProtocolReportRepository repository;
    private final ProtocolReportMapper mapper;
    private final ConclusionService conclusionService;
    private final TableService tableService;
    private final SubsectionService subsectionService;

    @Override
    public void save(Section section, SectionTemplate sectiontemplate, WorkJournalDto journal) {
        Map<Integer, ProtocolReportTemplate> templates = sectiontemplate.getProtocolReportTemplates().stream()
                                        .collect(Collectors.toMap(ProtocolReportTemplate::getSequentialNumber, t -> t));
        repository.saveAll(sectiontemplate.getProtocolReportTemplates().stream()
                .map(t -> mapper.mapToProtocolReport(t, section
                                                      , conclusionService.save(templates.get(t.getSequentialNumber())
                                                                                        .getConclusionTemplate())))
                .toList())
                .forEach(protocol -> {
                    ProtocolReportTemplate template = templates.get(protocol.getSequentialNumber());
                    if (template.getSectionTemplate() != null) {
                        subsectionService.saveForProtocolReport(protocol, template.getSubsectionTemplates(), journal);
                    }
                    if (template.getTableTemplates() != null) {
                        tableService.saveForProtocolReport(protocol, template.getTableTemplates(), journal);
                    }
                });
    }
}