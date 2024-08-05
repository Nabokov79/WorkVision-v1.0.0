package ru.nabokovsg.laboratoryNK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.laboratoryNK.model.DiagnosticDocumentType;
import ru.nabokovsg.laboratoryNK.model.TypeDocument;

public interface DiagnosticDocumentTypeRepository extends JpaRepository<DiagnosticDocumentType, Long> {

    DiagnosticDocumentType findByTitleAndSubtitleAndTypeDocument(String title
                                                              , String subtitle
                                                              , TypeDocument typeDocument);
}