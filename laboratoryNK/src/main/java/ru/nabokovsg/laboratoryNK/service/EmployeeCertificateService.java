package ru.nabokovsg.laboratoryNK.service;

import ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employeeCertificate.EmployeeCertificateDto;
import ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employeeCertificate.ResponseEmployeeCertificateDto;

import java.util.List;

public interface EmployeeCertificateService {

    ResponseEmployeeCertificateDto save(EmployeeCertificateDto certificateDto);

    ResponseEmployeeCertificateDto update(EmployeeCertificateDto certificateDto);

    List<ResponseEmployeeCertificateDto> getAll(Long id);

    void delete(Long id);
}