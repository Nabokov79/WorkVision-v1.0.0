package ru.nabokovsg.documentNK.model.document.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.PageTitle;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "document_headers")
public class DocumentHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "division_type")
    private String divisionType;
    @Column(name = "division")
    private String division;
    @Column(name = "address")
    private String address;
    @Column(name = "certificate")
    private String certificate;
    @Column(name = "contacts")
    private String contacts;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "page_title_id",  nullable = false)
    private PageTitle pageTitle;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_protocol_id")
    private SurveyProtocol surveyProtocol;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_control_id")
    private ProtocolControl protocolControl;
}