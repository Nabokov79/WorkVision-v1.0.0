package ru.nabokovsg.documentNK.service.template.reportSurvey;

import ru.nabokovsg.documentNK.dto.template.reportSurvey.section.ResponseSectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.section.SectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.section.ShortResponseSectionTemplateDto;

import java.util.List;

public interface SectionTemplateService {

    ResponseSectionTemplateDto save(SectionTemplateDto sectionDto);

    ResponseSectionTemplateDto update(SectionTemplateDto sectionDto);

    ResponseSectionTemplateDto get(Long id);

    List<ShortResponseSectionTemplateDto> getAll(Long id);

    void delete(Long id);
}