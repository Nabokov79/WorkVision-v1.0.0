package ru.nabokovsg.documentNK.service.document.common;

import ru.nabokovsg.documentNK.dto.client.documentCreate.EquipmentPassportDto;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.Section;

import java.util.List;

public interface DocumentEquipmentPassportService {

    void saveForSection(Section section, List<EquipmentPassportDto> passports);

    void saveForSurveyProtocol(SurveyProtocol protocol, List<EquipmentPassportDto> passports);
}