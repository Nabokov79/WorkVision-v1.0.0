package ru.nabokovsg.documentNK.model.template.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "documentation_templates")
public class DocumentationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "documentation_id")
    private Long documentationId;
    @Column(name = "sequential_number")
    private Integer sequentialNumber;
    @Column(name = "document_name")
    private String documentName;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "subsection_template_id",  nullable = false)
    private SubsectionTemplate subsectionTemplate;
}