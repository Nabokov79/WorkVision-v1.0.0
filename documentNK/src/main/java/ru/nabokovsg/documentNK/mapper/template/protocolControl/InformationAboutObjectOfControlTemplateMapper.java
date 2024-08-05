package ru.nabokovsg.documentNK.mapper.template.protocolControl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.dto.template.protocolControl.informationObjectOfControlTemplate.InformationObjectOfControlTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolControl.informationObjectOfControlTemplate.ResponseInformationObjectOfControlTemplateDto;
import ru.nabokovsg.documentNK.model.template.protocolControl.InformationAboutObjectOfControlTemplate;
import ru.nabokovsg.documentNK.model.template.protocolControl.ProtocolControlTemplate;

@Mapper(componentModel = "spring")
public interface InformationAboutObjectOfControlTemplateMapper {

    @Mapping(source = "templateDto.documentTypeId", target = "documentTypeId")
    @Mapping(source = "templateDto.dataName", target = "dataName")
    @Mapping(source = "template", target = "protocolControlTemplate")
    @Mapping(source = "templateDto.id", target = "id")
    InformationAboutObjectOfControlTemplate mapToInformationAboutObjectOfControlTemplate(
                                                                    InformationObjectOfControlTemplateDto templateDto
                                                                  , ProtocolControlTemplate template);

    ResponseInformationObjectOfControlTemplateDto mapToResponseInformationObjectOfControlTemplateDto(
                                                                     InformationAboutObjectOfControlTemplate template);
}