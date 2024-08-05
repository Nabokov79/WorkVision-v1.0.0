package ru.nabokovsg.documentNK.repository.template.protocolControl;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.template.protocolControl.InformationAboutObjectOfControlTemplate;

import java.util.Set;

public interface InformationAboutObjectOfControlTemplateRepository
        extends JpaRepository<InformationAboutObjectOfControlTemplate, Long> {

    boolean existsByDocumentTypeIdAndDataName(Long documentTypeId, String dataName);

    Set<InformationAboutObjectOfControlTemplate> findByDocumentTypeId(Long documentTypeId);
}