package ru.nabokovsg.documentNK.model.document.report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.model.document.common.DocumentHeader;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "page_titles")
public class PageTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "diagnostic_document_id")
    private Long diagnosticDocumentId;
    @OneToMany(mappedBy = "pageTitle", fetch = FetchType.LAZY)
    private Set<DocumentHeader> documentHeaders;
    @Column(name = "title")
    private String title;
    @Column(name = "subtitle")
    private String subtitle;
    @Column(name = "equipment_text")
    private String equipmentText;
    @Column(name = "installation_location")
    private String installationLocation;
    @Column(name = "address")
    private String address;
    @Column(name = "number_date")
    private String numberAndDate;
    @Column(name = "post")
    private String post;
    @Column(name = "initials")
    private String initials;
    @Column(name = "city")
    private String city;
    @Column(name = "year")
    private String year;
}