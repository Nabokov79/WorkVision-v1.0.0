package ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicControl;

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
@Table(name = "ultrasonic_controls")
public class UltrasonicControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "work_journal_id")
    private Long workJournalId;
    @Column(name = "standard_size")
    private String standardSize;
    @Column(name = "welded_joint_number")
    private Integer weldedJointNumber;
    @Column(name = "measurement")
    private String measurement;
    @Column(name = "estimation")
    private String estimation;
}