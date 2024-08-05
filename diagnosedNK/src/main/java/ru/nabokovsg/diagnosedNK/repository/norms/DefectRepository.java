package ru.nabokovsg.diagnosedNK.repository.norms;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;

public interface DefectRepository extends JpaRepository<Defect, Long> {

    Defect findByDefectName(String defectName);
}