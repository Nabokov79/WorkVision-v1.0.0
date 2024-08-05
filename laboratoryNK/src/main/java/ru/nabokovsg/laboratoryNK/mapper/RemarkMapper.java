package ru.nabokovsg.laboratoryNK.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.laboratoryNK.dto.remark.RemarkDto;
import ru.nabokovsg.laboratoryNK.dto.remark.ResponseRemarkDto;
import ru.nabokovsg.laboratoryNK.model.DiagnosticDocument;
import ru.nabokovsg.laboratoryNK.model.LaboratoryEmployee;
import ru.nabokovsg.laboratoryNK.model.Remark;

@Mapper(componentModel = "spring")
public interface RemarkMapper {

    @Mapping(source = "remarkDto.remark", target = "remark")
    @Mapping(source = "employee.employeeId", target = "employeeId")
    @Mapping(source = "employee.initials", target = "initials")
    @Mapping(source = "document", target = "document")
    @Mapping(source = "remarkDto.id", target = "id")
    Remark mapToRemark(RemarkDto remarkDto, LaboratoryEmployee employee, DiagnosticDocument document);


    @Mapping(source = "remark.id", target = "id")
    @Mapping(source = "remark.document.diagnosticDocumentType.typeDocument", target = "documentType")
    @Mapping(source = "remark.document.documentNumber", target = "documentNumber")
    @Mapping(source = "remark.remark", target = "remark")
    @Mapping(source = "remark.initials", target = "initials")
    @Mapping(source = "remark.documentCorrected", target = "documentCorrected")
    ResponseRemarkDto mapToRemarkDto(Remark remark);
}