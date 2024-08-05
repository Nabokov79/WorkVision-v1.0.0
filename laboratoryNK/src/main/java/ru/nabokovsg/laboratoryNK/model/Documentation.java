package ru.nabokovsg.laboratoryNK.model;

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
@Table(name = "documentations")
public class Documentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "view", nullable = false)
    private String view;
    @Column(name = "number", nullable = false)
    private String number;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "path")
    private String path;
}