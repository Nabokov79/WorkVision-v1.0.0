package ru.nabokovsg.documentNK.service.document.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.mapper.document.protocol.SurveyProtocolMapper;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;
import ru.nabokovsg.documentNK.repository.document.protocol.SurveyProtocolRepository;
import ru.nabokovsg.documentNK.service.common.StringBuilderService;
import ru.nabokovsg.documentNK.service.document.common.DocumentEquipmentPassportService;
import ru.nabokovsg.documentNK.service.document.common.DocumentHeaderService;
import ru.nabokovsg.documentNK.service.document.common.SubsectionService;
import ru.nabokovsg.documentNK.service.document.common.TableService;
import ru.nabokovsg.documentNK.service.template.protocolSurvey.SurveyProtocolTemplateService;

@Service
@RequiredArgsConstructor
public class SurveyProtocolServiceImpl implements SurveyProtocolService {

    private final SurveyProtocolRepository repository;
    private final SurveyProtocolMapper mapper;
    private final SurveyProtocolTemplateService templateService;
    private final DocumentHeaderService documentHeaderService;
    private final DocumentEquipmentPassportService equipmentPassportService;
    private final StringBuilderService stringBuilder;
    private final WorkplaceService workplaceService;
    private final SubsectionService subsectionService;
    private final TableService tableService;

    @Override
    public String create(DocumentCreationDataDto documentCreationDataDto) {
        SurveyProtocolTemplate template = templateService.getByDocumentTypeIdAndEquipmentTypeId(
                                                          documentCreationDataDto.getTypeDocumentId()
                                                        , documentCreationDataDto.getEquipmentTypeId());
        SurveyProtocol protocol = repository.save(mapper.mapToSurveyProtocol(documentCreationDataDto.getId()
                                                    , stringBuilder.buildProtocolTitle(template.getTitle()
                                                            , documentCreationDataDto.getDate()
                                                            , documentCreationDataDto.getDocumentNumber())
                                                    , template.getSubtitle()
                                                    , workplaceService.save(documentCreationDataDto)));
        documentHeaderService.saveForSurveyProtocol(protocol, template.getLeftHeaderTemplates());
        equipmentPassportService.saveForSurveyProtocol(protocol, documentCreationDataDto.getPassports());
        subsectionService.saveForSurveyProtocol(protocol, template.getSubsectionTemplates(), documentCreationDataDto);
        tableService.saveForSurveyProtocol(protocol, template.getTableTemplates(), documentCreationDataDto);
        return stringBuilder.buildResultDocumentCreate(documentCreationDataDto);
    }
}