package ru.nabokovsg.documentNK.model.document.report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.model.document.common.Appendices;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "diagnostic_document_id")
    private Long diagnosticDocumentId;
    @OneToOne
    @JoinColumn(name = "page_title_id", referencedColumnName = "id")
    private PageTitle pageTitle;
    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY)
    private Set<Section> sections;
    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY)
    private Set<Appendices> appendices;
}