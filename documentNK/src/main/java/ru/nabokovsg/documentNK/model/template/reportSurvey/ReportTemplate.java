package ru.nabokovsg.documentNK.model.template.reportSurvey;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.model.template.common.AppendicesTemplate;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report_templates")
public class ReportTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "document_type_id")
    private Long documentTypeId;
    @Column(name = "equipment_type_id")
    private Long equipmentTypeId;
    @OneToOne
    @JoinColumn(name = "page_title_id", referencedColumnName = "id")
    private PageTitleTemplate pageTitleTemplate;
    @OneToMany(mappedBy = "reportTemplate", fetch = FetchType.LAZY)
    private Set<SectionTemplate> sectionsTemplate;
    @OneToMany(mappedBy = "reportTemplate", fetch = FetchType.LAZY)
    private Set<AppendicesTemplate> appendices;
}