package ru.nabokovsg.diagnosedNK.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.common.RecommendationEmployee;

import java.util.Set;

public interface RecommendationEmployeeRepository extends JpaRepository<RecommendationEmployee, Long> {

    boolean existsByEquipmentIdAndRecommendation(Long equipmentId, String recommendation);

    Set<RecommendationEmployee> findAllByEquipmentId(Long equipmentId);
}