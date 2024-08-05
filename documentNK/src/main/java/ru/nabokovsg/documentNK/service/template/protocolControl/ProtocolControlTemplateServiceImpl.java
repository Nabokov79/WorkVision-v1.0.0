package ru.nabokovsg.documentNK.service.template.protocolControl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.template.protocolControl.ProtocolControlTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolControl.ResponseProtocolControlTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolControl.ShortResponseProtocolControlTemplateDto;
import ru.nabokovsg.documentNK.exceptions.BadRequestException;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.template.protocolControl.ProtocolControlTemplateMapper;
import ru.nabokovsg.documentNK.model.template.protocolControl.ProtocolControlTemplate;
import ru.nabokovsg.documentNK.repository.template.protocolControl.ProtocolControlTemplateRepository;
import ru.nabokovsg.documentNK.service.template.common.DocumentHeaderTemplateService;
import ru.nabokovsg.documentNK.service.template.common.SubsectionTemplateService;
import ru.nabokovsg.documentNK.service.template.common.TableTemplateService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProtocolControlTemplateServiceImpl implements ProtocolControlTemplateService {

    private final ProtocolControlTemplateRepository repository;
    private final ProtocolControlTemplateMapper mapper;
    private final DocumentHeaderTemplateService documentHeaderService;
    private final DocumentNKClient client;
    private final SubsectionTemplateService subsectionService;
    private final TableTemplateService tableService;

    @Override
    public ShortResponseProtocolControlTemplateDto save(ProtocolControlTemplateDto protocolDto) {
        if (repository.existsByDocumentTypeId(protocolDto.getDocumentTypeId())) {
            throw new BadRequestException(
                    String.format("ReportControlTemplate with documentTypeId=%s is found"
                                                                                    , protocolDto.getDocumentTypeId()));
        }
        ProtocolControlTemplate template = repository.save(mapper.mapToProtocolTemplate(protocolDto
                , client.getDocumentType(protocolDto.getDocumentTypeId())
                , documentHeaderService.getAllByDocumentTypeId(protocolDto.getDocumentTypeId())
        , tableService.getById(protocolDto.getTableTemplateId())));
        subsectionService.addProtocolControlTemplate(template, protocolDto.getSubsectionTemplatesId());
        return mapper.mapToShortResponseProtocolTemplateDto(template);
    }

    @Override
    public ShortResponseProtocolControlTemplateDto update(ProtocolControlTemplateDto protocolDto) {
        if (repository.existsById(protocolDto.getId())) {
            ProtocolControlTemplate template = repository.save(mapper.mapToProtocolTemplate(protocolDto
                    , client.getDocumentType(protocolDto.getDocumentTypeId())
                    , documentHeaderService.getAllByDocumentTypeId(protocolDto.getDocumentTypeId())
                    , tableService.getById(protocolDto.getTableTemplateId())));
            subsectionService.addProtocolControlTemplate(template, protocolDto.getSubsectionTemplatesId());
            return mapper.mapToShortResponseProtocolTemplateDto(template);
        }
        throw new NotFoundException(
                String.format("Protocol template by id=%s not found for update", protocolDto.getId())
        );
    }

    @Override
    public ResponseProtocolControlTemplateDto get(Long id) {
        return mapper.mapToResponseProtocolTemplateDto(repository.findById(id).
                orElseThrow(() -> new NotFoundException(String.format("Protocol template by id=%s not found", id))));
    }

    @Override
    public ProtocolControlTemplate getByDocumentTypeId(Long documentTypeId) {
        return repository.findByDocumentTypeId(documentTypeId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("ReportTemplate with documentTypeId=%s not found", documentTypeId)));
    }

    @Override
    public List<ShortResponseProtocolControlTemplateDto> getAll() {
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
}