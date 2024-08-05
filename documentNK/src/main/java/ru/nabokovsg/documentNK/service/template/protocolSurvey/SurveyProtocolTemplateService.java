package ru.nabokovsg.documentNK.service.template.protocolSurvey;

import ru.nabokovsg.documentNK.dto.template.protocolSurvey.ResponseSurveyProtocolTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolSurvey.ShortResponseSurveyProtocolTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolSurvey.SurveyProtocolTemplateDto;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;

import java.util.List;

public interface SurveyProtocolTemplateService {

    ShortResponseSurveyProtocolTemplateDto save(SurveyProtocolTemplateDto protocolDto);

    ShortResponseSurveyProtocolTemplateDto update(SurveyProtocolTemplateDto protocolDto);

    ResponseSurveyProtocolTemplateDto get(Long id);

    SurveyProtocolTemplate getByDocumentTypeIdAndEquipmentTypeId(Long documentTypeId, Long equipmentTypeId);

    List<ShortResponseSurveyProtocolTemplateDto> getAll();

    void delete(Long id);
}