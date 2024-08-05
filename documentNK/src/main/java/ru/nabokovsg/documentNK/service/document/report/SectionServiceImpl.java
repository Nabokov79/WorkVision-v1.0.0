package ru.nabokovsg.documentNK.service.document.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.dto.document.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.mapper.document.report.SectionMapper;
import ru.nabokovsg.documentNK.mapper.document.reportSurvey.SectionMapper;
import ru.nabokovsg.documentNK.model.document.report.Report;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ReportTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;
import ru.nabokovsg.documentNK.repository.document.report.SectionRepository;
import ru.nabokovsg.documentNK.service.document.common.DocumentEquipmentPassportService;
import ru.nabokovsg.documentNK.service.document.common.SubsectionService;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository repository;
    private final SectionMapper mapper;
    private final DocumentEquipmentPassportService equipmentPassportService;
    private final ProtocolReportService protocolReportService;
    private final SubsectionService subsectionService;

    @Override
    public void save(Report report, ReportTemplate template, DocumentCreationDataDto documentCreationDataDto) {
        Map<Integer, SectionTemplate> sectionsTemplates = template.getSectionsTemplate().stream()
                .collect(Collectors.toMap(SectionTemplate::getSequentialNumber, s -> s));
        repository.saveAll(template.getSectionsTemplate().stream()
                                                  .map(s -> mapper.mapToSection(s, report))
                                                  .toList()).forEach(s -> {
            SectionTemplate sectiontemplate = sectionsTemplates.get(s.getSequentialNumber());
            if (sectiontemplate.getSpecifyEquipmentPassport()) {
                equipmentPassportService.saveForSection(s, documentCreationDataDto.getPassports());
            }
            if (!sectiontemplate.getSubsectionTemplates().isEmpty()) {
                subsectionService.saveForSection(s, sectiontemplate.getSubsectionTemplates(), documentCreationDataDto.getJournal());
            }
            if (!sectiontemplate.getProtocolReportTemplates().isEmpty()) {
                protocolReportService.save(s, sectiontemplate, documentCreationDataDto.getJournal());
            }
        });
    }
}