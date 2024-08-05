package ru.nabokovsg.documentNK.service.template.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.template.common.conclusion.ConclusionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.conclusion.ResponseConclusionTemplateDto;
import ru.nabokovsg.documentNK.exceptions.BadRequestException;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.template.common.ConclusionTemplateMapper;
import ru.nabokovsg.documentNK.model.template.common.ConclusionTemplate;
import ru.nabokovsg.documentNK.repository.template.common.ConclusionTemplateRepository;

@Service
@RequiredArgsConstructor
public class ConclusionTemplateServiceImpl implements ConclusionTemplateService {

    private final ConclusionTemplateRepository repository;
    private final ConclusionTemplateMapper mapper;

    @Override
    public ResponseConclusionTemplateDto save(ConclusionTemplateDto conclusionDto) {
        if (repository.existsByDocumentTypeId(conclusionDto.getDocumentTypeId())) {
            throw new BadRequestException(
                    String.format("Conclusion template with documentTypeId=%s is found"
                                                                        , conclusionDto.getDocumentTypeId()));
        }
        return mapper.mapToResponseConclusionTemplateDto(
                repository.save(mapper.mapToConclusionTemplate(conclusionDto)));
    }

    @Override
    public ResponseConclusionTemplateDto update(ConclusionTemplateDto conclusionDto) {
        if (repository.existsById(conclusionDto.getId())) {
            return mapper.mapToResponseConclusionTemplateDto(
                    repository.save(mapper.mapToConclusionTemplate(conclusionDto)));
        }
       throw new NotFoundException(String.format("Conclusion template with id=%s not found", conclusionDto.getId()));
    }

    @Override
    public ResponseConclusionTemplateDto get(Long id) {
        return mapper.mapToResponseConclusionTemplateDto(
                repository.findById(id).orElseThrow(
                        () -> new NotFoundException(String.format("Conclusion template with id=%s not found", id))));
    }

    @Override
    public ConclusionTemplate getByDocumentTypeId(Long documentTypeId) {
        return repository.findByDocumentTypeId(documentTypeId).orElseThrow(() -> new NotFoundException(
                String.format("Conclusion template with documentTypeId=%s not found", documentTypeId)));
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Conclusion template with id=%s not found for delete", id));
    }
}