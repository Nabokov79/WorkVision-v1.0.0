package ru.nabokovsg.documentNK.service.document.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.mapper.document.report.PageTitleMapper;
import ru.nabokovsg.documentNK.model.document.report.PageTitle;
import ru.nabokovsg.documentNK.model.template.reportSurvey.PageTitleTemplate;
import ru.nabokovsg.documentNK.repository.document.report.PageTitleRepository;
import ru.nabokovsg.documentNK.service.common.StringBuilderService;
import ru.nabokovsg.documentNK.service.document.common.DocumentHeaderService;
import ru.nabokovsg.documentNK.service.template.reportSurvey.PageTitleTemplateService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PageTitleServiceImpl implements PageTitleService {

    private final PageTitleRepository repository;
    private final PageTitleMapper mapper;
    private final PageTitleTemplateService pageTitleTemplateService;
    private final StringBuilderService builderService;
    private final DocumentHeaderService documentHeaderService;
    private final static String BOILER_ROOM = "котельная";

    @Override
    public PageTitle save(DocumentCreationDataDto documentCreationDataDto) {
        PageTitle pageTitle = repository.findByDiagnosticDocumentId(documentCreationDataDto.getId());
        PageTitleTemplate pageTitleTemplate = pageTitleTemplateService.getByIds(
                                                          documentCreationDataDto.getDiagnosticDocumentType().getId()
                                                        , documentCreationDataDto.getJournal().getEquipmentTypeId());
        if (pageTitle == null) {
           set(pageTitleTemplate
             , documentCreationDataDto.getEquipmentDiagnosed()
             , documentCreationDataDto.getJournal().getBuilding());
             pageTitle = repository.save(mapper.mapToPageTitle(pageTitleTemplate
                    , documentCreationDataDto.getChief()
                    , builderService.numberAndDate(documentCreationDataDto.getDate(), documentCreationDataDto.getDocumentNumber())
                    , String.valueOf(LocalDate.now().getYear())
                    , documentCreationDataDto.getJournal().getBuilding().split(",")[1]));
            documentHeaderService.saveForPageTitle(pageTitle, pageTitleTemplate.getDocumentHeaders());
            return pageTitle;
        }
        update(pageTitle, pageTitleTemplate, documentCreationDataDto);
       return pageTitle;
    }

    public void update(PageTitle pageTitle, PageTitleTemplate pageTitleTemplate, DocumentCreationDataDto documentCreationDataDto) {
        set(pageTitleTemplate, documentCreationDataDto.getEquipmentDiagnosed(), documentCreationDataDto.getJournal().getBuilding());
        repository.save(mapper.mapToUpdatePageTitle(pageTitle
                , documentCreationDataDto.getChief()
                , builderService.numberAndDate(documentCreationDataDto.getDate(), documentCreationDataDto.getDocumentNumber())
                , documentCreationDataDto.getJournal().getBuilding().split(",")[1]));
    }

    private String unitEquipmentText (String equipmentText, String equipmentDiagnosed) {
        return String.join(" ", equipmentText, equipmentDiagnosed.split(",")[1]);
    }

    private void set(PageTitleTemplate pageTitleTemplate, String equipmentDiagnosed, String building) {
        pageTitleTemplate.setEquipmentText(unitEquipmentText(pageTitleTemplate.getEquipmentText()
                , equipmentDiagnosed));
        pageTitleTemplate.setInstallationLocation(
                builderService.buildInstallationLocation(pageTitleTemplate.getInstallationLocation()
                        , building, BOILER_ROOM));
    }
}