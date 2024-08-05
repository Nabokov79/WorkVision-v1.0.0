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
@Table(name = "employee_certificates")
public class EmployeeCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "document_type")
    private String documentType;
    @Column(name = "certificate_number")
    private String certificateNumber;
    @Column(name = "control_type")
    private String controlType;
    @Column(name = "level")
    private Integer level;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "points")
    private String points;
    @Column(name = "organization")
    private String organization;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id",  nullable = false)
    private LaboratoryEmployee employee;
}
