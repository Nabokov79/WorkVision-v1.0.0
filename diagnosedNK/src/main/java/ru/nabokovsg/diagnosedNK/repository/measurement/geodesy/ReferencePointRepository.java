package ru.nabokovsg.diagnosedNK.repository.measurement.geodesy;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ReferencePoint;

public interface ReferencePointRepository extends JpaRepository<ReferencePoint, Long> {
}