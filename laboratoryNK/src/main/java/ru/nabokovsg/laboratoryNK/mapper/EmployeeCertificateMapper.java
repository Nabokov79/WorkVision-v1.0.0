package ru.nabokovsg.laboratoryNK.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employeeCertificate.EmployeeCertificateDto;
import ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employeeCertificate.ResponseEmployeeCertificateDto;
import ru.nabokovsg.laboratoryNK.model.EmployeeCertificate;
import ru.nabokovsg.laboratoryNK.model.LaboratoryEmployee;

@Mapper(componentModel = "spring")
public interface EmployeeCertificateMapper {

    @Mapping(source = "certificateDto.documentType", target = "documentType")
    @Mapping(source = "certificateDto.certificateNumber", target = "certificateNumber")
    @Mapping(source = "certificateDto.controlType", target = "controlType")
    @Mapping(source = "certificateDto.level", target = "level")
    @Mapping(source = "certificateDto.startDate", target = "startDate")
    @Mapping(source = "certificateDto.endDate", target = "endDate")
    @Mapping(source = "certificateDto.points", target = "points")
    @Mapping(source = "certificateDto.organization", target = "organization")
    @Mapping(source = "employee", target = "employee")
    @Mapping(source = "certificateDto.id", target = "id")
    EmployeeCertificate mapToCertificate(EmployeeCertificateDto certificateDto, LaboratoryEmployee employee);

    ResponseEmployeeCertificateDto mapToFullCertificateDto(EmployeeCertificate certificate);
}