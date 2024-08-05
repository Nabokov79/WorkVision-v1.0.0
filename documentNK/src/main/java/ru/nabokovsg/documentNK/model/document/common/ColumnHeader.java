package ru.nabokovsg.documentNK.model.document.common;

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
@Table(name = "columns_headers")
public class ColumnHeader {

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
    @JoinColumn(name = "table_id")
    private DocumentTable table;
}