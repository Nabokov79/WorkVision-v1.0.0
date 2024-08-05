package ru.nabokovsg.documentNK.model.template.protocolControl;

import jakarta.persistence.*;
import lombok.*;
import ru.nabokovsg.documentNK.model.template.common.DocumentHeaderTemplate;
import ru.nabokovsg.documentNK.model.template.common.SubsectionTemplate;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "protocol_control_templates")
public class ProtocolControlTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "document_type_id")
    private Long documentTypeId;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "protocol_control_templates_document_headers",
            joinColumns = {@JoinColumn(name = "protocol_template_id")},
            inverseJoinColumns = {@JoinColumn(name = "document_header_id")})
    @ToString.Exclude
    private Set<DocumentHeaderTemplate> leftHeaderTemplates;
    @OneToMany(mappedBy = "protocolControlTemplate", fetch = FetchType.LAZY)
    private Set<InformationAboutObjectOfControlTemplate> informationTemplate;
    @Column(name = "title")
    private String title;
    @Column(name = "subtitle")
    private String subtitle;
    @OneToMany(mappedBy = "protocolControlTemplate", fetch = FetchType.LAZY)
    private Set<SubsectionTemplate> subsectionTemplates;
    @OneToOne
    @JoinColumn(name = "table_template_id", referencedColumnName = "id")
    private TableTemplate tableTemplate;
}