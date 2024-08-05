package ru.nabokovsg.laboratoryNK.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employeeCertificate.EmployeeCertificateDto;
import ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employeeCertificate.ResponseEmployeeCertificateDto;
import ru.nabokovsg.laboratoryNK.exceptions.NotFoundException;
import ru.nabokovsg.laboratoryNK.mapper.EmployeeCertificateMapper;
import ru.nabokovsg.laboratoryNK.model.EmployeeCertificate;
import ru.nabokovsg.laboratoryNK.repository.EmployeeCertificateRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeCertificateServiceImpl implements EmployeeCertificateService {

    private final EmployeeCertificateRepository repository;
    private final EmployeeCertificateMapper mapper;
    private final LaboratoryEmployeeService employeeService;

    @Override
    public ResponseEmployeeCertificateDto save(EmployeeCertificateDto certificateDto) {
        EmployeeCertificate certificate = repository.findByControlTypeAndEmployeeId(certificateDto.getControlType()
                , certificateDto.getEmployeeId());
        if (certificate == null) {
            certificate = mapper.mapToCertificate(certificateDto
                    , employeeService.getById(certificateDto.getEmployeeId()));
        }
        return mapper.mapToFullCertificateDto(repository.save(certificate));
    }

    @Override
    public ResponseEmployeeCertificateDto update(EmployeeCertificateDto certificateDto) {
        if (repository.existsById(certificateDto.getId())) {
            return mapper.mapToFullCertificateDto(repository.save(mapper.mapToCertificate(certificateDto
                    , employeeService.getById(certificateDto.getEmployeeId()))));
        }
        throw new NotFoundException(
                String.format("Certificate with id=%s not found for update", certificateDto.getId())
        );
    }

    @Override
    public List<ResponseEmployeeCertificateDto> getAll(Long id) {
        return repository.findAllByEmployeeId(id)
                .stream()
                .map(mapper::mapToFullCertificateDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Certificate with id = %s not found for delete", id));
    }
}