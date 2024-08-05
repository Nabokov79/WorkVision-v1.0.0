package ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement;

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
@Table(name = "hardness_measurements")
public class HardnessMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "measurement_number")
    private Integer measurementNumber;
    @Column(name = "measurement_value")
    private Integer measurementValue;
    @Column(name = "validity")
    private String validity;
    @Column(name = "validity_value")
    private Boolean validityValue;

}