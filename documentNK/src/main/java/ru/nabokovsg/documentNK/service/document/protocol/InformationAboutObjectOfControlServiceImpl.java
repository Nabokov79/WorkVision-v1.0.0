package ru.nabokovsg.documentNK.service.document.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.document.informationAboutObjectOfControl.InformationAboutObjectOfControlDto;
import ru.nabokovsg.documentNK.dto.document.informationAboutObjectOfControl.ResponseInformationAboutObjectOfControlDto;
import ru.nabokovsg.documentNK.exceptions.BadRequestException;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.document.protocol.InformationAboutObjectOfControlMapper;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.repository.document.protocol.InformationAboutObjectOfControlRepository;
import ru.nabokovsg.documentNK.service.template.protocolControl.InformationAboutObjectOfControlTemplateService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InformationAboutObjectOfControlServiceImpl implements InformationAboutObjectOfControlService {

    private final InformationAboutObjectOfControlRepository repository;
    private final InformationAboutObjectOfControlMapper mapper;
    private final InformationAboutObjectOfControlTemplateService templateService;

    @Override
    public ResponseInformationAboutObjectOfControlDto save(InformationAboutObjectOfControlDto informationDto) {
        if (repository.existsByWorkJournalIdAndDataValue(informationDto.getWorkJournalId()
                                                       , informationDto.getDataValue())) {
            throw new BadRequestException(
                    String.format("InformationObjectOfControl with templateId=%s and dataValue=%s is found"
                                                  , informationDto.getTemplateId(), informationDto.getDataValue()));
        }
        return mapper.mapToResponseInformationAboutObjectOfControlDto(
                                            repository.save(mapper.mapToInformationAboutObjectOfControl(informationDto
                                                         , templateService.getById(informationDto.getTemplateId()))));
    }

    @Override
    public ResponseInformationAboutObjectOfControlDto update(InformationAboutObjectOfControlDto informationDto) {
        if (repository.existsById(informationDto.getId())) {
            return mapper.mapToResponseInformationAboutObjectOfControlDto(
                                            repository.save(mapper.mapToInformationAboutObjectOfControl(informationDto
                                                         , templateService.getById(informationDto.getTemplateId()))));
        }
        throw new NotFoundException(
                String.format("InformationObjectOfControlTemplate with id=%s not found for update"
                                                                                            , informationDto.getId()));
    }

    @Override
    public List<ResponseInformationAboutObjectOfControlDto> getAll(Long protocolId) {
        return repository.findByProtocolControlId(protocolId)
                         .stream()
                         .map(mapper::mapToResponseInformationAboutObjectOfControlDto)
                         .toList();
    }

    @Override
    public void addProtocolControl(ProtocolControl protocol, Long workJournalId) {
        repository.saveAll(
                repository.findByWorkJournalId(workJournalId)
                          .stream()
                          .map(i -> mapper.mapTWithProtocolControl(i, protocol))
                          .toList()
        );
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("InformationObjectOfControl with id=%s not found for delete", id));
    }
}