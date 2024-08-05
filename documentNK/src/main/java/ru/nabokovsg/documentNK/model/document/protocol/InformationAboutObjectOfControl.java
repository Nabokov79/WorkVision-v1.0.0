package ru.nabokovsg.documentNK.model.document.protocol;

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
@Table(name = "information_object_control")
public class InformationAboutObjectOfControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "template_id")
    private Long templateId;
    @Column(name = "work_journal_id")
    private Long workJournalId;
    @Column(name = "sequential_number")
    private Integer sequentialNumber;
    @Column(name = "data_name")
    private String dataName;
    @Column(name = "data_value")
    private String dataValue;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_control_id")
    private ProtocolControl protocolControl;
}