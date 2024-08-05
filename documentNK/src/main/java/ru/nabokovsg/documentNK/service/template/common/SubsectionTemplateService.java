package ru.nabokovsg.documentNK.service.template.common;

import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.ResponseSubsectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.SubsectionTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.SubsectionTemplate;
import ru.nabokovsg.documentNK.model.template.protocolControl.ProtocolControlTemplate;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ProtocolReportTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;

import java.util.List;

public interface SubsectionTemplateService {

    ResponseSubsectionTemplateDto save(SubsectionTemplateDto subsectionsDto);

    ResponseSubsectionTemplateDto update(SubsectionTemplateDto subsectionsDto);

    ResponseSubsectionTemplateDto get(Long id);

    SubsectionTemplate getById(Long id);

    void delete(Long id);

    void addProtocolReportTemplate(ProtocolReportTemplate template, List<Long> subsectionTemplatesId);

    void addSectionTemplate(SectionTemplate template, List<Long> subsectionTemplatesId);

    void addProtocolTemplate(SurveyProtocolTemplate template, List<Long> subsectionTemplatesId);

    void addProtocolControlTemplate(ProtocolControlTemplate template, List<Long> subsectionTemplatesId);
}