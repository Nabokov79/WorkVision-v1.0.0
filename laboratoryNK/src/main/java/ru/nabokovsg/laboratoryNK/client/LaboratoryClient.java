package ru.nabokovsg.laboratoryNK.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.laboratoryNK.dto.client.BranchDto;
import ru.nabokovsg.laboratoryNK.dto.client.EmployeeDto;
import ru.nabokovsg.laboratoryNK.dto.client.EquipmentDto;
import ru.nabokovsg.laboratoryNK.dto.documentNkService.DocumentCreationDataDto;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.ResponseMeasuringToolDto;
import ru.nabokovsg.laboratoryNK.model.TypeDocument;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaboratoryClient {

    private final CompanyClient companyClient;
    private final DiagnosticObjectNKClient equipmentClient;
    private final DocumentClient documentClient;
    private final DiagnosedNKClient diagnosedNKClient;
    private static final String DELIMITER = "/";
    private static final String API_PREFIX_EMPLOYEE = "/employee";
    private static final String API_PREFIX_BRANCH = "/branch";
    private static final String API_PREFIX_EQUIPMENT = "/diagnosed/equipments";
    private static final String API_PREFIX_REPORT = "/document/survey/report";
    private static final String API_PREFIX_SURVEY_PROTOCOL = "/document/survey/protocol";
    private static final String API_PREFIX_CONTROL_PROTOCOL = "/document/control/protocol";
    private static final String API_PREFIX_DIAGNOSTIC_OBJECT = "/diagnosed/nk/diagnostic/object";
    private static final String API_PREFIX_TOLL = "/template/measuring/tool";

    public List<EmployeeDto> getAllEmployees(Long id, String divisionType) {
        return companyClient.getAllEmployees(String.join(DELIMITER, API_PREFIX_EMPLOYEE,
                "all", String.valueOf(id)), "divisionType", divisionType);
    }

    public BranchDto getBranch(Long id) {
        return companyClient.getBranch(String.join(DELIMITER, API_PREFIX_BRANCH, String.valueOf(id)));
    }

    public EquipmentDto getEquipment(Long id) {
        return equipmentClient.getEquipment(String.join(DELIMITER, API_PREFIX_EQUIPMENT, String.valueOf(id)));
    }

    public String createDocument(TypeDocument typeDocument, DocumentCreationDataDto body) {
        switch (typeDocument) {
            case REPORT -> {
                return documentClient.createDocument(String.join(DELIMITER, API_PREFIX_REPORT), body);
            }
            case SURVEY_PROTOCOL -> {
                return documentClient.createDocument(String.join(DELIMITER, API_PREFIX_SURVEY_PROTOCOL), body);
            }
            case CONTROL_PROTOCOL -> {
                return documentClient.createDocument(String.join(DELIMITER,API_PREFIX_CONTROL_PROTOCOL), body);
            }
            default -> {
                return "Тип документа не поддерживается приложением";
            }
        }
    }

    public void addEquipmentDataForDiagnosed(EquipmentDto equipmentDto) {
        diagnosedNKClient.addEquipmentDataForDiagnosed(String.join(DELIMITER, API_PREFIX_DIAGNOSTIC_OBJECT)
                                                     , equipmentDto);
    }

    public void addMeasuringTool(ResponseMeasuringToolDto measuringTool) {
        documentClient.addMeasuringTool(String.join(DELIMITER, API_PREFIX_TOLL), measuringTool);
    }

    public void updateMeasuringTool(ResponseMeasuringToolDto measuringTool) {
        documentClient.updateMeasuringTool(String.join(DELIMITER, API_PREFIX_TOLL), measuringTool);
    }
}