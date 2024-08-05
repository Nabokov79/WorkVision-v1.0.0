package ru.nabokovsg.diagnosedNK.repository.measurement.ultrasonicControl;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicControl.UltrasonicControl;

import java.util.Set;

public interface UltrasonicControlRepository extends JpaRepository<UltrasonicControl, Long> {

    Set<UltrasonicControl> findAllByWorkJournalId(Long workJournalId);

    UltrasonicControl findByWorkJournalIdAndWeldedJointNumber(Long workJournalId, Integer weldedJointNumber);
}