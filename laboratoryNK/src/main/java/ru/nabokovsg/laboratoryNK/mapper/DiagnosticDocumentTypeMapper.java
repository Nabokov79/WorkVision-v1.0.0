package ru.nabokovsg.laboratoryNK.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.laboratoryNK.dto.diagnosticDocument.DiagnosticDocumentTypeDto;
import ru.nabokovsg.laboratoryNK.model.DiagnosticDocumentType;
import ru.nabokovsg.laboratoryNK.model.TypeDocument;

@Mapper(componentModel = "spring")
public interface DiagnosticDocumentTypeMapper {

    @Mapping(source = "typeDocument", target = "typeDocument")
    @Mapping(source = "documentTypeDto.id", target = "id")
    DiagnosticDocumentType mapToDiagnosticDocument(DiagnosticDocumentTypeDto documentTypeDto, TypeDocument typeDocument);

    DiagnosticDocumentTypeDto mapToDiagnosticDocumentTypeDto(DiagnosticDocumentType documentType);
}