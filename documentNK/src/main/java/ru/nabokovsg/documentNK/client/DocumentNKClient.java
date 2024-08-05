package ru.nabokovsg.documentNK.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.nabokovsg.documentNK.dto.client.documentCreate.EquipmentGeodesicMeasurementsDto;
import ru.nabokovsg.documentNK.dto.client.documentCreate.EquipmentInspectionDto;
import ru.nabokovsg.documentNK.dto.client.documentCreate.EquipmentRepairDto;
import ru.nabokovsg.documentNK.dto.client.documentCreate.VisualMeasuringSurveyDto;
import ru.nabokovsg.documentNK.dto.client.templateCreate.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentNKClient {
    private final LaboratoryNKClient laboratoryNKClient;
    private final CompanyClient companyClient;
    private final DiagnosedEquipmentClient equipmentClient;
    private final DiagnosedNKClient diagnosedNKClient;
    private static final String DELIMITER = "/";
    private static final String API_PREFIX_DOCUMENTATION = "/laboratory/nk/documentation";
    private static final String API_PREFIX_DOCUMENT_TYPE = "/laboratory/nk/document/type";
    private static final String API_PREFIX_CERTIFICATES = "/laboratory/nk/certificate";
    private static final String API_PREFIX_TOLL = "/laboratory/nk/instrument";
    private static final String API_PREFIX_ORGANIZATION = "/organization";
    private static final String API_PREFIX_BRANCH = "/branch";
    private static final String API_PREFIX_DEPARTMENT = "/department";
    private static final String API_PREFIX_HEAT_SUPPLE_AREA = "/heat/supply/area";
    private static final String API_PREFIX_EXPLOITATION_REGION = "/exploitation";
    private static final String API_PREFIX_EMPLOYEE = "/employee";
    private static final String API_PREFIX_INSPECTION = "/equipments/inspection";
    private static final String API_PREFIX_REPAIR = "/equipments/repair";
    private static final String API_PREFIX_GEODESIC = "/diagnosed/nk/calculated/geodesy";
    private static final String API_PREFIX_VMS = "/diagnosed/nk/measurement/vms";

    public DocumentationDto getDocumentation(Long id) {
        return laboratoryNKClient.getDocumentation(String.join(DELIMITER, API_PREFIX_DOCUMENTATION, String.valueOf(id)));
    }

    public DocumentTypeDto getDocumentType(Long id) {
        return laboratoryNKClient.getDocumentType(String.join(DELIMITER, API_PREFIX_DOCUMENT_TYPE, String.valueOf(id)));
    }

    public List<LaboratoryCertificateDto> getLaboratoryCertificate() {
        return laboratoryNKClient.getLaboratoryCertificate(String.join(DELIMITER, API_PREFIX_CERTIFICATES));
    }

    public MeasuringToolDto getMeasuringTool(Long id) {
        return laboratoryNKClient.getMeasuringTool(String.join(DELIMITER, API_PREFIX_TOLL, String.valueOf(id)));
    }

    public List<MeasuringToolDto> getMeasuringTools(List<Long> ids) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("employeeId", ids.stream().map(String::valueOf).toList());
        return laboratoryNKClient.getMeasuringTools(String.join(DELIMITER, API_PREFIX_TOLL, "/all"), params);
    }

    public OrganizationDto getOrganization(Long id) {
        return companyClient.getOrganization(String.join(DELIMITER, API_PREFIX_ORGANIZATION, String.valueOf(id)));
    }

    public BranchDto getBranch(Long id) {
        return companyClient.getBranch(String.join(DELIMITER, API_PREFIX_BRANCH, String.valueOf(id)));
    }

    public HeatSupplyAreaDto getHeatSupplyArea(Long id) {
        return companyClient.getHeatSupplyArea(String.join(DELIMITER, API_PREFIX_HEAT_SUPPLE_AREA, String.valueOf(id)));
    }
    public DepartmentDto getDepartment(Long id) {
        return companyClient.getDepartment(String.join(DELIMITER, API_PREFIX_DEPARTMENT, String.valueOf(id)));
    }
    public ExploitationRegionDto getExploitationRegion(Long id) {
        return companyClient.getExploitationRegion(
                String.join(DELIMITER, API_PREFIX_EXPLOITATION_REGION, String.valueOf(id)));
    }

    public List<EmployeeDto> getAllEmployees(Long id, String divisionType) {
        return companyClient.getAllEmployees(String.join(DELIMITER, API_PREFIX_EMPLOYEE,
                "all", String.valueOf(id)), "divisionType", divisionType);
    }

    public List<EquipmentInspectionDto> getEquipmentInspections(Long id) {
        return equipmentClient.getEquipmentInspections(
                String.join(DELIMITER, API_PREFIX_INSPECTION, "/all", String.valueOf(id))
        );
    }

    public List<EquipmentRepairDto> getEquipmentRepairs(Long id) {
        return equipmentClient.getEquipmentRepairs(
                String.join(DELIMITER, API_PREFIX_REPAIR, "/all", String.valueOf(id))
        );
    }

    public EquipmentGeodesicMeasurementsDto getEquipmentGeodesicMeasurements(Long equipmentId) {
        return diagnosedNKClient.getEquipmentGeodesicMeasurements(
                String.join(DELIMITER, API_PREFIX_GEODESIC, String.valueOf(equipmentId)));
    }

    public List<VisualMeasuringSurveyDto> getVisualMeasuringSurvey(Long equipmentId) {
        return diagnosedNKClient.getVisualMeasuringSurvey(
                String.join(DELIMITER, API_PREFIX_VMS, String.valueOf(equipmentId)));
    }
}