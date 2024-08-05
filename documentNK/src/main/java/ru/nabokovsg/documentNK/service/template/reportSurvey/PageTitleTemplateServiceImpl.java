package ru.nabokovsg.documentNK.service.template.reportSurvey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.pageTitle.PageTitleTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.pageTitle.ResponsePageTitleTemplateDto;
import ru.nabokovsg.documentNK.exceptions.BadRequestException;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.template.reportSurvey.PageTitleTemplateMapper;
import ru.nabokovsg.documentNK.model.template.reportSurvey.PageTitleTemplate;
import ru.nabokovsg.documentNK.repository.template.reportSurvey.PageTitleTemplateRepository;
import ru.nabokovsg.documentNK.service.template.common.DocumentHeaderTemplateService;

@Service
@RequiredArgsConstructor
public class PageTitleTemplateServiceImpl implements PageTitleTemplateService {

    private final PageTitleTemplateRepository repository;
    private final PageTitleTemplateMapper mapper;
    private final ReportTemplateService reportTemplateService;
    private final DocumentHeaderTemplateService documentHeaderService;
    private final DocumentNKClient client;

    @Override
    public ResponsePageTitleTemplateDto save(PageTitleTemplateDto pageTitleDto) {
        if (repository.existsByDocumentTypeIdAndEquipmentTypeId(pageTitleDto.getDocumentTypeId()
                                                              , pageTitleDto.getEquipmentTypeId())) {
            throw new BadRequestException(
                    String.format("PageTitle template by documentTypeId=%s and equipmentTypeIdId=%s is found"
                                                                                , pageTitleDto.getDocumentTypeId()
                                                                                , pageTitleDto.getEquipmentTypeId()));
        }
        PageTitleTemplate pageTitleTemplate = repository.save(mapper.mapToPageTitleTemplate(pageTitleDto
                                    , client.getDocumentType(pageTitleDto.getDocumentTypeId())
                                    , documentHeaderService.getAllByDocumentTypeId(pageTitleDto.getDocumentTypeId())));
        reportTemplateService.create(pageTitleTemplate);
        return mapper.mapToResponsePageTitleTemplateDto(pageTitleTemplate);
    }

    @Override
    public ResponsePageTitleTemplateDto update(PageTitleTemplateDto pageTitleDto) {
        if (repository.existsById(pageTitleDto.getId())) {
            return mapper.mapToResponsePageTitleTemplateDto(
                    repository.save(mapper.mapToPageTitleTemplate(pageTitleDto
                                    , client.getDocumentType(pageTitleDto.getDocumentTypeId())
                                    , documentHeaderService.getAllByDocumentTypeId(pageTitleDto.getDocumentTypeId())))
            );
        }
        throw new NotFoundException(
                String.format("PageTitle template with id=%s not found for update", pageTitleDto.getId()));
    }

    @Override
    public ResponsePageTitleTemplateDto get(Long id) {
        return mapper.mapToResponsePageTitleTemplateDto(
                repository.findById(id).orElseThrow(
                        () -> new NotFoundException(String.format("PageTitle template with id=%s not found", id))));
    }

    @Override
    public PageTitleTemplate getByIds(Long documentTypeId, Long equipmentTypeId) {
        return repository.findByDocumentTypeIdAndEquipmentTypeId(documentTypeId, equipmentTypeId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("PageTitle template by documentTypeId=%s and equipmentTypeId=%s not found"
                                                                                            , documentTypeId
                                                                                            , equipmentTypeId)));
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("PageTitle template with id=%s not found for delete", id));
    }
}