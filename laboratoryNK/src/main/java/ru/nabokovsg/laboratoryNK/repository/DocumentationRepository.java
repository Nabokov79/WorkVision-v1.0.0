package ru.nabokovsg.laboratoryNK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.laboratoryNK.model.Documentation;

public interface DocumentationRepository extends JpaRepository<Documentation, Long> {

    Documentation findByTitle(String title);
}