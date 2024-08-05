package ru.nabokovsg.documentNK.service.template.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.template.common.documentationTemplate.DocumentationTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.documentationTemplate.ResponseDocumentationTemplateDto;
import ru.nabokovsg.documentNK.exceptions.BadRequestException;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.template.common.DocumentationTemplateMapper;
import ru.nabokovsg.documentNK.repository.template.common.DocumentationTemplateRepository;
import ru.nabokovsg.documentNK.service.common.StringBuilderService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentationTemplateServiceImpl implements DocumentationTemplateService {

    private final DocumentationTemplateRepository repository;
    private final DocumentationTemplateMapper mapper;
    private final StringBuilderService stringBuilderService;
    private final DocumentNKClient client;
    private final SubsectionTemplateService subsectionService;

    @Override
    public ResponseDocumentationTemplateDto save(DocumentationTemplateDto documentationDto) {
        if (repository.existsByDocumentationId(documentationDto.getDocumentationId())) {
            throw new BadRequestException(
                    String.format("Documentation template with id=%s is found", documentationDto.getDocumentationId()));
        }
        return mapper.mapToResponseDocumentationTemplateDto(repository.save(
                mapper.mapToDocumentationTemplate(documentationDto
                                                , stringBuilderService.buildDocumentation(
                                                        client.getDocumentation(documentationDto.getDocumentationId())
                                                )
                                                , subsectionService.getById(documentationDto.getSubsectionId()))
        ));
    }

    @Override
    public ResponseDocumentationTemplateDto update(DocumentationTemplateDto documentationDto) {
        if (repository.existsByDocumentationId(documentationDto.getDocumentationId())) {
            return mapper.mapToResponseDocumentationTemplateDto(repository.save(
                    mapper.mapToDocumentationTemplate(documentationDto
                            , stringBuilderService.buildDocumentation(
                                    client.getDocumentation(documentationDto.getDocumentationId())
                            )
                            , subsectionService.getById(documentationDto.getSubsectionId()))
            ));
        }
        throw new NotFoundException(
                String.format("Documentation template with id=%s not found for update"
                                             , documentationDto.getDocumentationId()));
    }

    @Override
    public List<ResponseDocumentationTemplateDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::mapToResponseDocumentationTemplateDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Documentation template with id=%s not found for delete", id));
    }
}