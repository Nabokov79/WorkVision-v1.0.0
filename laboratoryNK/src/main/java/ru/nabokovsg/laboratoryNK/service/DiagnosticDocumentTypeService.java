package ru.nabokovsg.laboratoryNK.service;

import ru.nabokovsg.laboratoryNK.dto.diagnosticDocument.DiagnosticDocumentTypeDto;
import ru.nabokovsg.laboratoryNK.model.DiagnosticDocumentType;

import java.util.List;

public interface DiagnosticDocumentTypeService {

    DiagnosticDocumentTypeDto save(DiagnosticDocumentTypeDto documentTypeDto);

    DiagnosticDocumentTypeDto update(DiagnosticDocumentTypeDto documentTypeDto);

    DiagnosticDocumentTypeDto get(Long id);

    List<DiagnosticDocumentTypeDto> getAll();

    void delete(Long id);

    DiagnosticDocumentType getById(Long diagnosticDocumentTypeId);
}