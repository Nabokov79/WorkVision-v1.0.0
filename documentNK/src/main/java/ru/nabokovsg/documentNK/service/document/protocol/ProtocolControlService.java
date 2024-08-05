package ru.nabokovsg.documentNK.service.document.protocol;

import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;

public interface ProtocolControlService {

    String create(DocumentCreationDataDto documentCreationDataDto);
}