package ru.nabokovsg.documentNK.service.document.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.client.documentCreate.EquipmentPassportDto;
import ru.nabokovsg.documentNK.mapper.document.common.DocumentEquipmentPassportMapper;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.Section;
import ru.nabokovsg.documentNK.repository.document.common.DocumentEquipmentPassportRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentEquipmentPassportServiceImpl implements DocumentEquipmentPassportService {

    private final DocumentEquipmentPassportRepository repository;
    private final DocumentEquipmentPassportMapper mapper;

    @Override
    public void saveForSection(Section section, List<EquipmentPassportDto> passports) {
        repository.saveAll(passports.stream()
                                    .map(p -> mapper.mapForSection(section, p))
                                    .toList());
    }

    @Override
    public void saveForSurveyProtocol(SurveyProtocol protocol, List<EquipmentPassportDto> passports) {
        repository.saveAll(passports.stream()
                                    .filter(passport -> passport.getUseToProtocol().equals(true))
                                    .map(p -> mapper.mapForSurveyProtocol(protocol, p))
                                    .toList());
    }
}