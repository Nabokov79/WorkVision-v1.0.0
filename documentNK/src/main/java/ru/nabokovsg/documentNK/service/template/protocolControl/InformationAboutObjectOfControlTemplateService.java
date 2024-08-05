package ru.nabokovsg.documentNK.service.template.protocolControl;

import ru.nabokovsg.documentNK.dto.template.protocolControl.informationObjectOfControlTemplate.InformationObjectOfControlTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolControl.informationObjectOfControlTemplate.ResponseInformationObjectOfControlTemplateDto;
import ru.nabokovsg.documentNK.model.template.protocolControl.InformationAboutObjectOfControlTemplate;

import java.util.List;

public interface InformationAboutObjectOfControlTemplateService {

    ResponseInformationObjectOfControlTemplateDto save(InformationObjectOfControlTemplateDto templateDto);

    ResponseInformationObjectOfControlTemplateDto update(InformationObjectOfControlTemplateDto templateDto);

    ResponseInformationObjectOfControlTemplateDto get(Long id);

    InformationAboutObjectOfControlTemplate getById(Long id);

    List<ResponseInformationObjectOfControlTemplateDto> getAll(Long documentTypeId);

    void delete(Long id);
}