package ru.nabokovsg.documentNK.service.document.protocol;

import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;

public interface SurveyProtocolService {

    String create(DocumentCreationDataDto documentCreationDataDto);
}