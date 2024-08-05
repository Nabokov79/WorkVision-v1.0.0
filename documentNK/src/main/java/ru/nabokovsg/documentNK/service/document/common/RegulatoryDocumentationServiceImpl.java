package ru.nabokovsg.documentNK.service.document.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.mapper.document.common.RegulatoryDocumentationMapper;
import ru.nabokovsg.documentNK.model.document.common.Subsection;
import ru.nabokovsg.documentNK.model.template.common.DocumentationTemplate;
import ru.nabokovsg.documentNK.repository.document.common.RegulatoryDocumentationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegulatoryDocumentationServiceImpl implements RegulatoryDocumentationService {

    private final RegulatoryDocumentationRepository repository;
    private final RegulatoryDocumentationMapper mapper;

    @Override
    public void save(Subsection subsection, List<DocumentationTemplate> documentationTemplate) {
        repository.saveAll(documentationTemplate.stream()
                                                .map(d -> mapper.mapToRegulatoryDocumentation(d, subsection))
                                                .toList());
    }
}