package ru.nabokovsg.documentNK.model.document.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.ProtocolReport;
import ru.nabokovsg.documentNK.model.document.report.Section;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subsections")
public class Subsection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sequential_number")
    private Integer sequentialNumber;
    @Column(name = "subsection_name")
    private String subsectionName;
    @Column(name = "user_text")
    private String userText;
    @Column(name = "division")
    private String division;
    @OneToOne
    @JoinColumn(name = "table_id", referencedColumnName = "id")
    private DocumentTable table;
    @OneToMany(mappedBy = "subsection", fetch = FetchType.LAZY)
    private List<RegulatoryDocumentation> documentation;
    @OneToMany(mappedBy = "subsection", fetch = FetchType.LAZY)
    private List<DocumentMeasuringTool> measuringTools;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_report_id")
    private ProtocolReport protocolReport;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_protocol_id")
    private SurveyProtocol surveyProtocol;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_control_id")
    private ProtocolControl protocolControl;
}