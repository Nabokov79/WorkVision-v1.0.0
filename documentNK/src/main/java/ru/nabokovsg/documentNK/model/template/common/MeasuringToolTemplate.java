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
@Table(name = "measuring_tool_templates")
public class MeasuringToolTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "measuring_tool_id")
    private Long measuringToolId;
    @Column(name = "sequential_number")
    private Integer sequentialNumber;
    @Column(name = "tool")
    private String tool;
    @Column(name = "model")
    private String model;
    @Column(name = "measuring_tool")
    private String measuringTool;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "subsection_template_id")
    private SubsectionTemplate subsectionTemplate;
}