package ru.nabokovsg.documentNK.service.template.protocolControl;

import ru.nabokovsg.documentNK.dto.template.protocolControl.ProtocolControlTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolControl.ResponseProtocolControlTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolControl.ShortResponseProtocolControlTemplateDto;
import ru.nabokovsg.documentNK.model.template.protocolControl.ProtocolControlTemplate;

import java.util.List;

public interface ProtocolControlTemplateService {

    ShortResponseProtocolControlTemplateDto save(ProtocolControlTemplateDto protocolDto);

    ShortResponseProtocolControlTemplateDto update(ProtocolControlTemplateDto protocolDto);

    ResponseProtocolControlTemplateDto get(Long id);

    ProtocolControlTemplate getByDocumentTypeId(Long documentTypeId);

    List<ShortResponseProtocolControlTemplateDto> getAll();

    void delete(Long id);
}