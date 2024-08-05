package ru.nabokovsg.documentNK.service.common;

import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.dto.client.templateCreate.*;
import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.DivisionDataDto;

import java.time.LocalDate;
import java.util.List;

public interface StringBuilderService {

    String buildMeasuringTool(MeasuringToolDto measuringTool);

    String buildDocumentation(DocumentationDto documentation);

    String buildDivision(DivisionDataDto param, DivisionDto division, List<LaboratoryCertificateDto> certificates);

    String buildCertificate(List<LaboratoryCertificateDto> certificates);

    String buildAddress(AddressDto address);

    String buildEmployeeContacts(EmployeeDto employeeDto);

    String numberAndDate(LocalDate date, Integer documentNumber);

    String buildInstallationLocation(String installationLocation, String building, String buildingType);

    String buildResultDocumentCreate(DocumentCreationDataDto documentCreationDataDto);

    String buildProtocolTitle(String titleTemplate, LocalDate date, Integer documentNumber);
}