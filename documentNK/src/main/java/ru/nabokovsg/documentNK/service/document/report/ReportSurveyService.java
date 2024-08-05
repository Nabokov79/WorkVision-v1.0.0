package ru.nabokovsg.documentNK.service.document.report;

import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;

public interface ReportSurveyService {

    String create(DocumentCreationDataDto documentCreationDataDto);
}