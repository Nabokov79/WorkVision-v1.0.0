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
@Table(name = "document_measuring_tools")
public class DocumentMeasuringTool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sequential_number")
    private Integer sequentialNumber;
    @Column(name = "measuring_tool")
    private String measuringTool;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "subsection_id")
    private Subsection subsection;
}
