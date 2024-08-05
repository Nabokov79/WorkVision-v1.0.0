package ru.nabokovsg.documentNK.service.template.reportSurvey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.report.ResponseReportTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.report.ShortResponseReportTemplateDto;
import ru.nabokovsg.documentNK.exceptions.BadRequestException;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.template.reportSurvey.ReportTemplateMapper;
import ru.nabokovsg.documentNK.model.template.reportSurvey.PageTitleTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ReportTemplate;
import ru.nabokovsg.documentNK.repository.template.reportSurvey.ReportTemplateRepository;
import ru.nabokovsg.documentNK.service.template.common.AppendicesTemplateService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportTemplateServiceImpl implements ReportTemplateService {

    private final ReportTemplateRepository repository;
    private final ReportTemplateMapper mapper;
    private final AppendicesTemplateService appendicesTemplateService;

    @Override
    public void create(PageTitleTemplate pageTitleTemplate) {
        if (repository.existsByDocumentTypeIdAndEquipmentTypeId(pageTitleTemplate.getDocumentTypeId()
                                                              , pageTitleTemplate.getEquipmentTypeId())) {
            throw new BadRequestException(
                   String.format("ReportTemplate by documentTypeId=%s and equipmentTypeId=%s is create"
                                                           , pageTitleTemplate.getDocumentTypeId()
                                                           , pageTitleTemplate.getEquipmentTypeId()));
        }
        repository.save(mapper.mapToReportTemplate(pageTitleTemplate.getDocumentTypeId()
                                                 , pageTitleTemplate.getEquipmentTypeId()
                                                 , pageTitleTemplate
                                                 , appendicesTemplateService.getAllByEquipmentTypeId(
                                                                             pageTitleTemplate.getEquipmentTypeId())));

    }

    @Override
    public ResponseReportTemplateDto get(Long id) {
        return mapper.mapToResponseReportTemplateDto(getById(id));
    }

    @Override
    public List<ShortResponseReportTemplateDto> getAll() {
        return repository.findAllPageTitleTemplate().stream().map(mapper::mapToShortResponseReportTemplateDto).toList();
    }

    @Override
    public ReportTemplate getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("ReportTemplate with id=%s not found", id)));
    }

    @Override
    public ReportTemplate getByDocumentTypeIdAndEquipmentTypeId(Long documentTypeId, Long equipmentTypeId) {
        return repository.findByDocumentTypeIdAndEquipmentTypeId(documentTypeId, equipmentTypeId)
                .orElseThrow(() -> new NotFoundException(
                String.format("ReportTemplate with documentTypeId=%s and equipmentTypeId=%s not found"
                                                                 , documentTypeId, equipmentTypeId)));
    }
}