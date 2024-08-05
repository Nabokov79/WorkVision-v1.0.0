package ru.nabokovsg.documentNK.service.document.report;

import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.model.document.report.PageTitle;

public interface PageTitleService {

    PageTitle save(DocumentCreationDataDto documentCreationDataDto);
}