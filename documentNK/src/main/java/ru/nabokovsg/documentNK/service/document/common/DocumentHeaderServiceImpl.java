package ru.nabokovsg.documentNK.service.document.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.mapper.document.common.DocumentHeaderMapper;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.PageTitle;
import ru.nabokovsg.documentNK.model.template.common.DocumentHeaderTemplate;
import ru.nabokovsg.documentNK.repository.document.common.DocumentHeaderRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DocumentHeaderServiceImpl implements DocumentHeaderService {

    private final DocumentHeaderRepository repository;
    private final DocumentHeaderMapper mapper;

    @Override
    public void saveForPageTitle(PageTitle pageTitle, Set<DocumentHeaderTemplate> documentHeaders) {
        repository.saveAll(documentHeaders.stream()
                                          .map(mapper::mapToDocumentHeader)
                                          .map(h -> mapper.mapWithPageTitle(h, pageTitle))
                                          .toList());
    }

    @Override
    public void saveForSurveyProtocol(SurveyProtocol protocol, Set<DocumentHeaderTemplate> documentHeaders) {
        repository.saveAll(documentHeaders.stream()
                .map(mapper::mapToDocumentHeader)
                .map(h -> mapper.mapWithSurveyProtocol(h, protocol))
                .toList());
    }

    @Override
    public void saveForProtocolControl(ProtocolControl protocol, Set<DocumentHeaderTemplate> documentHeaders) {
        repository.saveAll(documentHeaders.stream()
                .map(mapper::mapToDocumentHeader)
                .map(h -> mapper.mapWithProtocolControl(h, protocol))
                .toList());
    }
}