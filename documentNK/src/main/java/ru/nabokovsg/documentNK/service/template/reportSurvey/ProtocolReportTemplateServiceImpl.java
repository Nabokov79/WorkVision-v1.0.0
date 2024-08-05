package ru.nabokovsg.documentNK.service.template.reportSurvey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.protocolReport.ProtocolReportTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.protocolReport.ResponseProtocolReportTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.protocolReport.ShortResponseProtocolReportTemplateDto;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.template.reportSurvey.ProtocolReportTemplateMapper;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ProtocolReportTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;
import ru.nabokovsg.documentNK.repository.template.reportSurvey.ProtocolReportTemplateRepository;
import ru.nabokovsg.documentNK.service.template.common.ConclusionTemplateService;
import ru.nabokovsg.documentNK.service.template.common.SubsectionTemplateService;
import ru.nabokovsg.documentNK.service.template.common.TableTemplateService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProtocolReportTemplateServiceImpl implements ProtocolReportTemplateService {

    private final ProtocolReportTemplateRepository repository;
    private final ProtocolReportTemplateMapper mapper;
    private final DocumentNKClient client;
    private final SubsectionTemplateService subsectionTemplateService;
    private final TableTemplateService tableTemplateService;
    private final ConclusionTemplateService conclusionTemplateService;

    @Override
    public ShortResponseProtocolReportTemplateDto save(ProtocolReportTemplateDto protocolDto) {
        ProtocolReportTemplate protocol = repository.findByDocumentTypeId(protocolDto.getDocumentTypeId());
        if (protocol == null) {
            protocol = repository.save(mapper.mapToProtocolReportTemplate(protocolDto
                                    , client.getDocumentType(protocolDto.getDocumentTypeId())
                                    , conclusionTemplateService.getByDocumentTypeId(protocolDto.getDocumentTypeId())));
            subsectionTemplateService.addProtocolReportTemplate(protocol, protocolDto.getSubsectionTemplatesId());
            tableTemplateService.addProtocolReportTemplate(protocol, protocolDto.getTableTemplatesId());
        }
        return mapper.mapToShortProtocolReportTemplateDto(protocol);
    }

    @Override
    public ShortResponseProtocolReportTemplateDto update(ProtocolReportTemplateDto protocolDto) {
        if (repository.existsById(protocolDto.getId())) {
            ProtocolReportTemplate protocol = repository.save(mapper.mapToProtocolReportTemplate(protocolDto
                                    , client.getDocumentType(protocolDto.getDocumentTypeId())
                                    , conclusionTemplateService.getByDocumentTypeId(protocolDto.getDocumentTypeId())));
            subsectionTemplateService.addProtocolReportTemplate(protocol, protocolDto.getSubsectionTemplatesId());
            tableTemplateService.addProtocolReportTemplate(protocol, protocolDto.getTableTemplatesId());
            return mapper.mapToShortProtocolReportTemplateDto(repository.save(protocol));
        }
        throw new NotFoundException(
                String.format("ProtocolReportTemplate with id=%s not found for update", protocolDto.getId())
        );
    }

    @Override
    public ResponseProtocolReportTemplateDto get(Long id) {
        return mapper.mapToResponseProtocolReportTemplateDto(getById(id));
    }

    @Override
    public List<ShortResponseProtocolReportTemplateDto> getAll(Long id) {
        return repository.findAllBySectionId(id)
                         .stream()
                         .map(mapper::mapToShortProtocolReportTemplateDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Protocol report template with id=%s not found for delete", id));
    }

    @Override
    public void addSectionTemplate(SectionTemplate template, List<Long> protocolReportTemplatesId) {
        if (protocolReportTemplatesId != null && !protocolReportTemplatesId.isEmpty()) {
            repository.saveAll(getAllById(protocolReportTemplatesId)
                    .stream()
                    .map(p -> mapper.mapWithSectionTemplate(p, template))
                    .toList());
        }
    }

    private ProtocolReportTemplate getById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Protocol report template with id=%s not found", id))
        );
    }

    private List<ProtocolReportTemplate> getAllById(List<Long> protocolReportTemplatesId) {
        List<ProtocolReportTemplate> templates = repository.findAllById(protocolReportTemplatesId);
        if (templates.isEmpty()) {
            throw new NotFoundException(
                    String.format("Protocol report template with ids=%s not found", protocolReportTemplatesId));
        }
        return templates;
    }
}