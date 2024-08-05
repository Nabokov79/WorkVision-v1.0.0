package ru.nabokovsg.laboratoryNK.service;

import ru.nabokovsg.laboratoryNK.dto.client.AddressDto;
import ru.nabokovsg.laboratoryNK.dto.client.BuildingDto;
import ru.nabokovsg.laboratoryNK.dto.client.EmployeeDto;
import ru.nabokovsg.laboratoryNK.dto.client.EquipmentDto;
import ru.nabokovsg.laboratoryNK.model.DiagnosticDocumentType;

public interface StringBuilderService {

    String buildInitials(EmployeeDto employee);

    String buildBuilding(BuildingDto building);

    String buildEquipmentDiagnosed(EquipmentDto equipment);

    String buildDiagnosticDocumentType(DiagnosticDocumentType diagnosticDocumentType);

    String buildAddress(AddressDto address);
}