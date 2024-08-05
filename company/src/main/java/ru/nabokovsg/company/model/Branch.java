package ru.nabokovsg.company.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.company.model.enums.DivisionType;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "division_type")
    @Enumerated(EnumType.STRING)
    private DivisionType divisionType;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "short_name")
    private String shortName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<HeatSupplyArea> heatSupplyAreas;
    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<Department> departments;
    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<ExploitationRegion> exploitationRegions;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id",  nullable = false)
    private Organization organization;
}