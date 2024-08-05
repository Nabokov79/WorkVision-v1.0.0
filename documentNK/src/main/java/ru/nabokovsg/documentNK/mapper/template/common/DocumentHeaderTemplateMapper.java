package ru.nabokovsg.documentNK.mapper.template.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.dto.client.templateCreate.*;
import ru.nabokovsg.documentNK.dto.template.common.documentHeader.DocumentHeaderTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.documentHeader.ResponseDocumentHeaderTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.DivisionType;
import ru.nabokovsg.documentNK.model.template.common.DocumentHeaderTemplate;

@Mapper(componentModel = "spring")
public interface DocumentHeaderTemplateMapper {

    @Mapping(source = "headerDto.id", target = "id")
    @Mapping(source = "headerDto.documentTypeId", target = "documentTypeId")
    @Mapping(source = "divisionType", target = "divisionType")
    @Mapping(source = "division", target = "division")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "certificate", target = "certificate")
    @Mapping(source = "contacts", target = "contacts")
    DocumentHeaderTemplate mapToDocumentHeader(DocumentHeaderTemplateDto headerDto
                                     , DivisionType divisionType
                                     , String division
                                     , String address
                                     , String certificate
                                     , String contacts);
    ResponseDocumentHeaderTemplateDto mapToResponseDocumentHeaderDto(DocumentHeaderTemplate documentHeader);

    DivisionDto mapFromOrganization(OrganizationDto organization);

    DivisionDto mapFromBranch(BranchDto branch);

    DivisionDto mapFromDepartment(DepartmentDto department);

    DivisionDto mapFromHeatSupplyAreaDto(HeatSupplyAreaDto heatSupplyArea);

    DivisionDto mapFromExploitationRegionDto(ExploitationRegionDto exploitationRegion);
}