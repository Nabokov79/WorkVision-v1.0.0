package ru.nabokovsg.documentNK.repository.template.protocolControl;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.template.protocolControl.ProtocolControlTemplate;

import java.util.Optional;

public interface ProtocolControlTemplateRepository extends JpaRepository<ProtocolControlTemplate, Long> {

    boolean existsByDocumentTypeId(Long documentTypeId);

    Optional<ProtocolControlTemplate> findByDocumentTypeId(Long documentTypeId);
}