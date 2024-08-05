package ru.nabokovsg.documentNK.model.document.protocol;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.model.document.common.DocumentHeader;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;
import ru.nabokovsg.documentNK.model.document.common.Subsection;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "protocol_controls")
public class ProtocolControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "work_journal_id")
    private Long workJournalId;
    @OneToMany(mappedBy = "protocolControl", fetch = FetchType.LAZY)
    private Set<DocumentHeader> leftHeader;
    @OneToOne
    @JoinColumn(name = "work_place_id", referencedColumnName = "id")
    private Workplace workPlace;
    @Column(name = "title")
    private String title;
    @Column(name = "subtitle")
    private String subtitle;
    @OneToMany(mappedBy = "protocolControl", fetch = FetchType.LAZY)
    private Set<InformationAboutObjectOfControl> objectOfControl;
    @OneToMany(mappedBy = "protocolControl", fetch = FetchType.LAZY)
    private Set<Subsection> subsections;
    @OneToMany(mappedBy = "protocolControl", fetch = FetchType.LAZY)
    private Set<DocumentTable> tables;
}