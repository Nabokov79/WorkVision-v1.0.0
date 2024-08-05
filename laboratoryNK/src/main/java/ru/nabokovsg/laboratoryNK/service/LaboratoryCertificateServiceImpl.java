package ru.nabokovsg.laboratoryNK.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.laboratoryNK.dto.laboratoryCertificate.LaboratoryCertificateDto;
import ru.nabokovsg.laboratoryNK.exceptions.NotFoundException;
import ru.nabokovsg.laboratoryNK.mapper.LaboratoryCertificateMapper;
import ru.nabokovsg.laboratoryNK.repository.LaboratoryCertificateRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LaboratoryCertificateServiceImpl implements LaboratoryCertificateService {

    private final LaboratoryCertificateRepository repository;
    private final LaboratoryCertificateMapper mapper;

    @Override
    public LaboratoryCertificateDto save(LaboratoryCertificateDto certificateDto) {
        return mapper.mapToLaboratoryCertificateDto(
                Objects.requireNonNullElseGet(
                       repository.findByDocumentTypeAndLicenseNumberAndOrganization(certificateDto.getDocumentType()
                                                                                  , certificateDto.getLicenseNumber()
                                                                                  , certificateDto.getOrganization())
                        , () -> repository.save(mapper.mapToLaboratoryCertificate(certificateDto)))
        );
    }

    @Override
    public LaboratoryCertificateDto update(LaboratoryCertificateDto certificateDto) {
        if (repository.existsById(certificateDto.getId())) {
            return mapper.mapToLaboratoryCertificateDto(
                    repository.save(mapper.mapToLaboratoryCertificate(certificateDto))
            );
        }
        throw new NotFoundException(
                String.format("Laboratory certificate with id=%s not found for update", certificateDto.getId())
        );
    }

    @Override
    public List<LaboratoryCertificateDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::mapToLaboratoryCertificateDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Laboratory certificate with id=%s not found for delete", id));
    }
}