package ru.nabokovsg.laboratoryNK.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "diagnostic_documents")
public class DiagnosticDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_id")
    private Long equipmentId;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "work_journal_id",  nullable = false)
    private WorkJournal journal;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "branch")
    private String branch;
    @Column(name = "place_work")
    private String placeWork;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnostic_document_type_id",  nullable = false)
    private DiagnosticDocumentType diagnosticDocumentType;
    @Column(name = "document_number")
    private Integer documentNumber;
    @Column(name = "next_date")
    private LocalDate nextDate;
    @Column(name = "status")
    private String status;
    @Column(name = "document_path")
    private String documentPath;
    @Column(name = "drawing_path")
    private String drawingPath;
}