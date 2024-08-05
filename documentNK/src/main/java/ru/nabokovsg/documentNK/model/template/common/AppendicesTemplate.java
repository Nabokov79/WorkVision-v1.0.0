package ru.nabokovsg.documentNK.model.template.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ReportTemplate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appendices_templates")
public class AppendicesTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_type_id")
    private Long equipmentTypeId;
    @Column(name = "name_of_list")
    private String nameOfList;
    @Column(name = "sequential_number")
    private Integer sequentialNumber;
    @Column(name = "appendices_name")
    private String appendicesName;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "report_template_id",  nullable = false)
    private ReportTemplate reportTemplate;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_template_id",  nullable = false)
    private SurveyProtocolTemplate surveyProtocolTemplate;
}