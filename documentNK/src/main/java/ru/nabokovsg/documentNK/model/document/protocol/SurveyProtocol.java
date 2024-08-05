package ru.nabokovsg.documentNK.model.document.protocol;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.model.document.common.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "survey_protocols")
public class SurveyProtocol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "diagnostic_document_id")
    private Long diagnosticDocumentId;
    @OneToMany(mappedBy = "surveyProtocol", fetch = FetchType.LAZY)
    private Set<DocumentHeader> leftHeader;
    @OneToOne
    @JoinColumn(name = "work_place_id", referencedColumnName = "id")
    private Workplace workPlace;
    @Column(name = "title")
    private String title;
    @Column(name = "subtitle")
    private String subtitle;
    @OneToMany(mappedBy = "surveyProtocol", fetch = FetchType.LAZY)
    private Set<DocumentEquipmentPassport> equipmentPassports;
    @OneToMany(mappedBy = "surveyProtocol", fetch = FetchType.LAZY)
    private Set<Subsection> subsections;
    @OneToMany(mappedBy = "surveyProtocol", fetch = FetchType.LAZY)
    private Set<DocumentTable> tables;
    @OneToOne
    @JoinColumn(name = "conclusion_id", referencedColumnName = "id")
    private Conclusion conclusion;
    @OneToMany(mappedBy = "surveyProtocol", fetch = FetchType.LAZY)
    private Set<Appendices> appendices;
}