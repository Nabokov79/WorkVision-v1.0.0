package ru.nabokovsg.documentNK.model.template.reportSurvey;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.model.template.common.SubsectionTemplate;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "section_templates")
public class SectionTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sequential_number")
    private Integer sequentialNumber;
    @Column(name = "section_name")
    private String sectionName;
    @Column(name = "specify_passport")
    private Boolean specifyEquipmentPassport;
    @OneToMany(mappedBy = "sectionTemplate", fetch = FetchType.LAZY)
    private Set<SubsectionTemplate> subsectionTemplates;
    @OneToMany(mappedBy = "sectionTemplate", fetch = FetchType.LAZY)
    private Set<ProtocolReportTemplate> protocolReportTemplates;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "report_template_id",  nullable = false)
    private ReportTemplate reportTemplate;
}