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
@Table(name = "document_headers_templates")
public class DocumentHeaderTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "document_type_id")
    private Long documentTypeId;
    @Column(name = "division_type")
    @Enumerated(EnumType.STRING)
    private DivisionType divisionType;
    @Column(name = "division")
    private String division;
    @Column(name = "address")
    private String address;
    @Column(name = "certificate")
    private String certificate;
    @Column(name = "contacts")
    private String contacts;
}