package ru.nabokovsg.documentNK.model.document.protocol;

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
@Table(name = "work_places")
public class Workplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "branch")
    private String branch;
    @Column(name = "place_work")
    private String placeWork;
    @Column(name = "address")
    private String address;
}