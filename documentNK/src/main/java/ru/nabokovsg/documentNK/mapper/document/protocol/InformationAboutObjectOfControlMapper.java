package ru.nabokovsg.documentNK.mapper.document.protocol;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.documentNK.dto.document.informationAboutObjectOfControl.InformationAboutObjectOfControlDto;
import ru.nabokovsg.documentNK.dto.document.informationAboutObjectOfControl.ResponseInformationAboutObjectOfControlDto;
import ru.nabokovsg.documentNK.model.document.protocol.InformationAboutObjectOfControl;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.template.protocolControl.InformationAboutObjectOfControlTemplate;

@Mapper(componentModel = "spring")
public interface InformationAboutObjectOfControlMapper {

    @Mapping(source = "template.id", target = "templateId")
    @Mapping(source = "template.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "template.dataName", target = "dataName")
    @Mapping(source = "informationDto.dataValue", target = "dataValue")
    @Mapping(source = "informationDto.id", target = "id")
    InformationAboutObjectOfControl mapToInformationAboutObjectOfControl(
                                                                     InformationAboutObjectOfControlDto informationDto
                                                                   , InformationAboutObjectOfControlTemplate template);

    ResponseInformationAboutObjectOfControlDto mapToResponseInformationAboutObjectOfControlDto(
                                                                          InformationAboutObjectOfControl information);


    @Mapping(source = "protocol", target = "protocolControl")
    @Mapping(source = "information.id", target = "id")
    InformationAboutObjectOfControl mapTWithProtocolControl(@MappingTarget InformationAboutObjectOfControl information
                                                                         , ProtocolControl protocol);
}