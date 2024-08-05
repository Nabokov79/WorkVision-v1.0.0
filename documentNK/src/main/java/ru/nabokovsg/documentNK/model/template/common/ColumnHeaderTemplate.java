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
@Table(name = "columns_headers_templates")
public class ColumnHeaderTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sequential_number")
    private Integer sequentialNumber;
    @Column(name = "heading")
    private String heading;
    @Column(name = "column_header_type")
    private String columnHeaderType;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "table_template_id")
    private TableTemplate tableTemplate;
}
