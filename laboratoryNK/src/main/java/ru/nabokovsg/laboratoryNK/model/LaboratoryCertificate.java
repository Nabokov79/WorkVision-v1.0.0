package ru.nabokovsg.laboratoryNK.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "laboratory_certificates")
public class LaboratoryCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "division_id")
    private Long divisionId;
    @Column(name = "document_type")
    private String documentType;
    @Column(name = "license_number")
    private String licenseNumber;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "organization")
    private String organization;
}