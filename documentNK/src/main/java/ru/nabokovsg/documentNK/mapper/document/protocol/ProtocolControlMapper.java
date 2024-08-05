package ru.nabokovsg.documentNK.mapper.document.protocol;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.document.protocol.Workplace;

@Mapper(componentModel = "spring")
public interface ProtocolControlMapper {

    @Mapping(source = "diagnosticDocumentId", target = "diagnosticDocumentId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "subtitle", target = "subtitle")
    @Mapping(source = "workPlace", target = "workPlace")
    @Mapping(target = "id", ignore = true)
    ProtocolControl mapToProtocolControl(Long diagnosticDocumentId
                                       , String title
                                       , String subtitle
                                       , Workplace workPlace);
}