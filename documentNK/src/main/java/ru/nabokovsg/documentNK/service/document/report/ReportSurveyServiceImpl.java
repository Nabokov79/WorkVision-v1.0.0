package ru.nabokovsg.documentNK.service.document.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.dto.document.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.mapper.document.reportSurvey.ReportMapper;
import ru.nabokovsg.documentNK.model.document.report.Report;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ReportTemplate;
import ru.nabokovsg.documentNK.repository.document.report.ReportRepository;
import ru.nabokovsg.documentNK.service.common.StringBuilderService;
import ru.nabokovsg.documentNK.service.document.common.AppendicesService;
import ru.nabokovsg.documentNK.service.template.reportSurvey.ReportTemplateService;

@Service
@RequiredArgsConstructor
public class ReportSurveyServiceImpl implements ReportSurveyService {

    private final ReportRepository repository;
    private final ReportMapper mapper;
    private final ReportTemplateService templateService;
    private final PageTitleService pageTitleService;
    private final SectionService sectionService;
    private final AppendicesService appendicesService;
    private final DocumentNKClient client;
    private final StringBuilderService stringBuilder;

    @Override
    public String create(DocumentCreationDataDto documentCreationDataDto) {
        if (repository.existsByDiagnosticDocumentId(documentCreationDataDto.getId())) {
            return document;
        }
        ReportTemplate template = getReportTemplate(documentCreationDataDto);
        getEquipmentPassport(documentCreationDataDto);
        Report report = save(documentCreationDataDto);
        sectionService.save(report, template, documentCreationDataDto);
        appendicesService.saveForReport(report, template.getAppendices());
        return stringBuilder.buildResultDocumentCreate(documentCreationDataDto);
    }

    private Report save(DocumentCreationDataDto documentCreationDataDto) {
        return repository.save(mapper.mapToReport(documentCreationDataDto.getId()
                             , pageTitleService.save(documentCreationDataDto)));
    }

    private ReportTemplate getReportTemplate(DocumentCreationDataDto documentCreationDataDto) {
        return templateService.getByDocumentTypeIdAndEquipmentTypeId(
                                                             documentCreationDataDto.getDiagnosticDocumentType().getId()
                                                           , documentCreationDataDto.getJournal().getEquipmentTypeId());
    }

    private void getEquipmentPassport(DocumentCreationDataDto documentCreationDataDto) {
        documentCreationDataDto.setPassports(client.getEquipmentPassport(documentCreationDataDto.getEquipmentId()));
    }
}