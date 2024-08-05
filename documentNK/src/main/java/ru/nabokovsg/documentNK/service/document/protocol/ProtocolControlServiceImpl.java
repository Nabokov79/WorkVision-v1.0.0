package ru.nabokovsg.documentNK.service.document.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.mapper.document.protocol.ProtocolControlMapper;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.template.protocolControl.ProtocolControlTemplate;
import ru.nabokovsg.documentNK.repository.document.protocol.ProtocolControlRepository;
import ru.nabokovsg.documentNK.service.common.StringBuilderService;
import ru.nabokovsg.documentNK.service.document.common.DocumentHeaderService;
import ru.nabokovsg.documentNK.service.document.common.SubsectionService;
import ru.nabokovsg.documentNK.service.document.common.TableService;
import ru.nabokovsg.documentNK.service.template.protocolControl.ProtocolControlTemplateService;

@Service
@RequiredArgsConstructor
public class ProtocolControlServiceImpl implements ProtocolControlService {

    private final ProtocolControlRepository repository;
    private final ProtocolControlMapper mapper;
    private final ProtocolControlTemplateService templateService;
    private final DocumentHeaderService documentHeaderService;
    private final StringBuilderService stringBuilder;
    private final WorkplaceService workplaceService;
    private final InformationAboutObjectOfControlService informationAboutObjectOfControlService;
    private final SubsectionService subsectionService;
    private final TableService tableService;

    @Override
    public String create(DocumentCreationDataDto documentCreationDataDto) {
        ProtocolControlTemplate template = templateService.getByDocumentTypeId(
                                                           documentCreationDataDto.getDiagnosticDocumentType().getId());
        ProtocolControl protocol = repository.save(mapper.mapToProtocolControl(documentCreationDataDto.getId()
                                                        , stringBuilder.buildProtocolTitle(template.getTitle()
                                                                , documentCreationDataDto.getDate()
                                                                , documentCreationDataDto.getDocumentNumber())
                                                        , template.getSubtitle()
                                                        , workplaceService.save(documentCreationDataDto.getJournal())));
        documentHeaderService.saveForProtocolControl(protocol, template.getLeftHeaderTemplates());
        informationAboutObjectOfControlService.addProtocolControl(protocol, documentCreationDataDto.getJournal().getId());
        subsectionService.saveForProtocolControl(protocol, template.getSubsectionTemplates(), documentCreationDataDto.getJournal());
        tableService.saveForProtocolControl(protocol, template.getTableTemplate());
        return stringBuilder.buildResultDocumentCreate(documentCreationDataDto);
    }
}