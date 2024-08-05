package ru.nabokovsg.documentNK.model.document.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.Section;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "document_equipment_passports")
public class DocumentEquipmentPassport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sequential_number")
    private String sequentialNumber;
    @Column(name = "header")
    private String header;
    @Column(name = "meaning")
    private String meaning;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id",  nullable = false)
    private Section section;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_protocol_id",  nullable = false)
    private SurveyProtocol surveyProtocol;
}