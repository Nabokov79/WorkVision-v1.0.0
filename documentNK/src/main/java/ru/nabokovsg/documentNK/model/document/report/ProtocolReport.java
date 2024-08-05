package ru.nabokovsg.documentNK.model.document.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.model.document.common.Conclusion;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;
import ru.nabokovsg.documentNK.model.document.common.Subsection;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report_protocols")
public class ProtocolReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sequential_number")
    private Integer sequentialNumber;
    @Column(name = "title")
    private String title;
    @Column(name = "subtitle")
    private String subtitle;
    @Column(name = "user_text_after_subtitle")
    private String userTextAfterSubtitle;
    @OneToMany(mappedBy = "protocolReport", fetch = FetchType.LAZY)
    private Set<Subsection> subsection;
    @OneToMany(mappedBy = "protocolReport", fetch = FetchType.LAZY)
    private Set<DocumentTable> table;
    @OneToOne
    @JoinColumn(name = "conclusion_id", referencedColumnName = "id")
    private Conclusion conclusion;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    @JsonIgnore
    private Section section;
}