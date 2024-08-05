package ru.nabokovsg.laboratoryNK.mapper;

import org.mapstruct.Mapper;
import ru.nabokovsg.laboratoryNK.dto.laboratoryCertificate.LaboratoryCertificateDto;
import ru.nabokovsg.laboratoryNK.model.LaboratoryCertificate;

@Mapper(componentModel = "spring")
public interface LaboratoryCertificateMapper {

    LaboratoryCertificate mapToLaboratoryCertificate(LaboratoryCertificateDto certificateDto);

    LaboratoryCertificateDto mapToLaboratoryCertificateDto(LaboratoryCertificate certificate);
}