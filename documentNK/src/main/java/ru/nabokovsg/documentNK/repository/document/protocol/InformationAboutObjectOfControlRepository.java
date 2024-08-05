package ru.nabokovsg.documentNK.repository.document.protocol;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.document.protocol.InformationAboutObjectOfControl;

import java.util.Set;

public interface InformationAboutObjectOfControlRepository
        extends JpaRepository<InformationAboutObjectOfControl, Long> {

    boolean existsByWorkJournalIdAndDataValue(Long workJournalId, String dataValue);

    Set<InformationAboutObjectOfControl> findByProtocolControlId(Long protocolId);

    Set<InformationAboutObjectOfControl> findByWorkJournalId(Long workJournalId);
}