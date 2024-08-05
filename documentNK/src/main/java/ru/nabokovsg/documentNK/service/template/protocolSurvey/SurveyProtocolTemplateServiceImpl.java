package ru.nabokovsg.documentNK.service.template.protocolSurvey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.template.protocolSurvey.ResponseSurveyProtocolTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolSurvey.ShortResponseSurveyProtocolTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolSurvey.SurveyProtocolTemplateDto;
import ru.nabokovsg.documentNK.exceptions.BadRequestException;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.template.protocolSurvey.SurveyProtocolTemplateMapper;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;
import ru.nabokovsg.documentNK.repository.template.protocolSurvey.SurveyProtocolTemplateRepository;
import ru.nabokovsg.documentNK.service.template.common.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyProtocolTemplateServiceImpl implements SurveyProtocolTemplateService {

    private final SurveyProtocolTemplateRepository repository;
    private final SurveyProtocolTemplateMapper mapper;
    private final DocumentHeaderTemplateService documentHeaderService;
    private final DocumentNKClient client;
    private final SubsectionTemplateService subsectionService;
    private final TableTemplateService tableService;
    private final ConclusionTemplateService conclusionService;
    private final AppendicesTemplateService appendicesTemplateService;

    @Override
    public ShortResponseSurveyProtocolTemplateDto save(SurveyProtocolTemplateDto protocolDto) {
        if (repository.existsByDocumentTypeIdAndEquipmentTypeId(protocolDto.getDocumentTypeId()
                                                              , protocolDto.getEquipmentTypeId())) {
            throw new BadRequestException(
                    String.format("ReportTemplate with documentTypeId=%s equipmentTypeId=%s is found"
                                                                                   , protocolDto.getDocumentTypeId()
                                                                                   , protocolDto.getEquipmentTypeId()));
        }
        SurveyProtocolTemplate template = repository.save(mapper.mapToProtocolTemplate(protocolDto
                                        , client.getDocumentType(protocolDto.getDocumentTypeId())
                                        , documentHeaderService.getAllByDocumentTypeId(protocolDto.getDocumentTypeId())
                                        , conclusionService.getByDocumentTypeId(protocolDto.getDocumentTypeId())));
       addProtocolTemplate(template, protocolDto);
       return mapper.mapToShortResponseProtocolTemplateDto(template);
    }

    @Override
    public ShortResponseSurveyProtocolTemplateDto update(SurveyProtocolTemplateDto protocolDto) {
        if (repository.existsById(protocolDto.getId())) {
            SurveyProtocolTemplate template = repository.save(mapper.mapToProtocolTemplate(protocolDto
                    , client.getDocumentType(protocolDto.getDocumentTypeId())
                    , documentHeaderService.getAllByDocumentTypeId(protocolDto.getDocumentTypeId())
                    , conclusionService.getByDocumentTypeId(protocolDto.getDocumentTypeId())));
            addProtocolTemplate(template, protocolDto);
            return mapper.mapToShortResponseProtocolTemplateDto(template);
        }
        throw new NotFoundException(
                String.format("Protocol template by id=%s not found for update", protocolDto.getId())
        );
    }

    @Override
    public ResponseSurveyProtocolTemplateDto get(Long id) {
        return mapper.mapToResponseProtocolTemplateDto(repository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Protocol template by id=%s not found", id))));
    }

    @Override
    public SurveyProtocolTemplate getByDocumentTypeIdAndEquipmentTypeId(Long documentTypeId, Long equipmentTypeId) {
        return repository.findByDocumentTypeIdAndEquipmentTypeId(documentTypeId, equipmentTypeId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("SurveyProtocolTemplate with documentTypeId=%s and equipmentTypeId=%s not found"
                                , documentTypeId, equipmentTypeId)));
    }

    @Override
    public List<ShortResponseSurveyProtocolTemplateDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::mapToShortResponseProtocolTemplateDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Protocol template with id=%s not found for delete", id));
    }

    private void addProtocolTemplate(SurveyProtocolTemplate template, SurveyProtocolTemplateDto protocolDto) {
        subsectionService.addProtocolTemplate(template, protocolDto.getSubsectionTemplatesId());
        tableService.addProtocolTemplate(template, protocolDto.getTableTemplatesId());
        appendicesTemplateService.addProtocolTemplate(template);
    }
}