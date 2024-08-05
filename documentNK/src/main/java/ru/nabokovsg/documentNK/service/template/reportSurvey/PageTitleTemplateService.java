package ru.nabokovsg.documentNK.service.template.reportSurvey;

import ru.nabokovsg.documentNK.dto.template.reportSurvey.pageTitle.PageTitleTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.pageTitle.ResponsePageTitleTemplateDto;
import ru.nabokovsg.documentNK.model.template.reportSurvey.PageTitleTemplate;

public interface PageTitleTemplateService {

    ResponsePageTitleTemplateDto save(PageTitleTemplateDto pageTitleDto);

    ResponsePageTitleTemplateDto update(PageTitleTemplateDto pageTitleDto);

    ResponsePageTitleTemplateDto get(Long id);

    PageTitleTemplate getByIds(Long documentTypeId, Long equipmentTypeId);

    void delete(Long id);
}