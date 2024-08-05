package ru.nabokovsg.laboratoryNK.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.laboratoryNK.dto.diagnosticDocument.DiagnosticDocumentDto;
import ru.nabokovsg.laboratoryNK.dto.documentNkService.DocumentCreationDataDto;
import ru.nabokovsg.laboratoryNK.model.DiagnosticDocument;
import ru.nabokovsg.laboratoryNK.model.DiagnosticDocumentType;
import ru.nabokovsg.laboratoryNK.model.MeasuringTool;
import ru.nabokovsg.laboratoryNK.model.WorkJournal;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiagnosticDocumentMapper {

    @Mapping(source = "journal.equipmentId", target = "equipmentId")
    @Mapping(source = "journal", target = "journal")
    @Mapping(source = "journal.date", target = "date")
    @Mapping(source = "journal.branch", target = "branch")
    @Mapping(source = "journal.placeWork", target = "placeWork")
    @Mapping(source = "diagnosticDocumentType", target = "diagnosticDocumentType")
    @Mapping(source = "documentNumber", target = "documentNumber")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "id", ignore = true)
    DiagnosticDocument mapToDiagnosticDocument(WorkJournal journal
                                             , Long equipmentId
                                             , DiagnosticDocumentType diagnosticDocumentType
                                             , String documentType
                                             , String status
                                             , Integer documentNumber);

    @Mapping(source = "journal.equipmentId", target = "equipmentId")
    @Mapping(source = "journal", target = "journal")
    @Mapping(source = "journal.date", target = "date")
    @Mapping(source = "journal.branch", target = "branch")
    @Mapping(source = "journal.placeWork", target = "placeWork")
    @Mapping(source = "diagnosticDocumentType", target = "diagnosticDocumentType")
    @Mapping(source = "document.documentNumber", target = "documentNumber")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "document.id", target = "id")
    DiagnosticDocument mapToUpdateDiagnosticDocument(DiagnosticDocument document
                                                   , WorkJournal journal
                                                   , Long equipmentId
                                                   , String status
                                                   , DiagnosticDocumentType diagnosticDocumentType
                                                   , String documentType);

    DiagnosticDocumentDto mapToDiagnosticDocumentDto(DiagnosticDocument document);

    @Mapping(source = "document.journal.equipmentTypeId", target = "equipmentTypeId")
    @Mapping(source = "document.journal.equipmentId", target = "equipmentId")
    @Mapping(source = "document.date", target = "date")
    @Mapping(source = "document.branch", target = "branch")
    @Mapping(source = "document.placeWork", target = "placeWork")
    @Mapping(source = "document.journal.address", target = "address")
    @Mapping(source = "document.diagnosticDocumentType.title", target = "title")
    @Mapping(source = "document.diagnosticDocumentType.subtitle", target = "subtitle")
    @Mapping(source = "document.diagnosticDocumentType.id", target = "typeDocumentId")
    @Mapping(source = "document.documentNumber", target = "documentNumber")
    @Mapping(source = "document.journal.chief", target = "chief")
    @Mapping(source = "document.journal.employees", target = "employees")
    @Mapping(source = "measuringTools", target = "measuringTools")
    DocumentCreationDataDto mapToDocumentCreationDataDto(DiagnosticDocument document
                                                       , List<MeasuringTool> measuringTools);
}