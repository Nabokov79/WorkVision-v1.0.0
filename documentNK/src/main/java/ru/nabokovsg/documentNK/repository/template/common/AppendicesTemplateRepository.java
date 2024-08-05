package ru.nabokovsg.documentNK.repository.template.common;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.template.common.AppendicesTemplate;

import java.util.Optional;
import java.util.Set;

public interface AppendicesTemplateRepository extends JpaRepository<AppendicesTemplate, Long> {

    AppendicesTemplate findByAppendicesName(String appendicesName);

    Optional<AppendicesTemplate> findByEquipmentTypeId(Long equipmentTypeId);

    Set<AppendicesTemplate> findAllByEquipmentTypeId(Long equipmentTypeId);
}