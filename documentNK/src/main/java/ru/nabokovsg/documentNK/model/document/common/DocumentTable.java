package ru.nabokovsg.documentNK.model.document.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.ProtocolReport;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "document_tables")
public class DocumentTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "table_type")
    private String tableType;
    @Column(name = "sequential_number")
    private Integer sequentialNumber;
    @Column(name = "table_name")
    private String tableName;
    @Column(name = "text_before_table")
    private String textBeforeTable;
    @Column(name = "text_after_table")
    private String textAfterTable;
    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    private Set<ColumnHeader> columnHeaders;
    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    private Set<CellTable> cells;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_report_id")
    private ProtocolReport protocolReport;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_protocol_id")
    private SurveyProtocol surveyProtocol;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_control_id")
    private ProtocolControl protocolControl;
}