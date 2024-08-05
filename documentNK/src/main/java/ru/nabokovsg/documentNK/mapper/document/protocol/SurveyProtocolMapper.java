package ru.nabokovsg.documentNK.mapper.document.protocol;

import org.mapstruct.Mapper;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.protocol.Workplace;

@Mapper(componentModel = "spring")
public interface SurveyProtocolMapper {

    SurveyProtocol mapToSurveyProtocol(Long diagnosticDocumentId
                                     , String title
                                     , String subtitle
                                     , Workplace workPlace);
}