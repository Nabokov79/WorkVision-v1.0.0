package ru.nabokovsg.documentNK.service.document.protocol;

import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.model.document.protocol.Workplace;

public interface WorkplaceService {

    Workplace save(DocumentCreationDataDto journal);
}