package ru.nabokovsg.diagnosedNK.model.common;

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
@Table(name = "recommendation_employee")
public class RecommendationEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_id")
    private Long equipmentId;
    @Column(name = "recommendation")
    private String recommendation;
}