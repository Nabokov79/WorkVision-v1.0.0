package ru.nabokovsg.documentNK.model.template.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.model.template.protocolControl.ProtocolControlTemplate;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ProtocolReportTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subsection_templates")
public class SubsectionTemplate {

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
    @Column(name = "summary_results")
    private boolean summaryResults;
    @OneToOne
    @JoinColumn(name = "table_id", referencedColumnName = "id")
    private TableTemplate tableTemplate;
    @OneToMany(mappedBy = "subsectionTemplate", fetch = FetchType.LAZY)
    private List<DocumentationTemplate> documentationTemplate;
    @OneToMany(mappedBy = "subsectionTemplate", fetch = FetchType.LAZY)
    private List<MeasuringToolTemplate> measuringToolsTemplates;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_report_template_id")
    private ProtocolReportTemplate protocolReportTemplate;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "section_template_id")
    private SectionTemplate sectionTemplate;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_template_id")
    private SurveyProtocolTemplate surveyProtocolTemplate;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_control_template_id")
    private ProtocolControlTemplate protocolControlTemplate;
}