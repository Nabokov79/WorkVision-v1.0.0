package ru.nabokovsg.documentNK.service.template.protocolControl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.template.protocolControl.informationObjectOfControlTemplate.InformationObjectOfControlTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolControl.informationObjectOfControlTemplate.ResponseInformationObjectOfControlTemplateDto;
import ru.nabokovsg.documentNK.exceptions.BadRequestException;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.template.protocolControl.InformationAboutObjectOfControlTemplateMapper;
import ru.nabokovsg.documentNK.model.template.protocolControl.InformationAboutObjectOfControlTemplate;
import ru.nabokovsg.documentNK.repository.template.protocolControl.InformationAboutObjectOfControlTemplateRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InformationAboutObjectOfControlTemplateServiceImpl implements InformationAboutObjectOfControlTemplateService {

    private final InformationAboutObjectOfControlTemplateRepository repository;
    private final InformationAboutObjectOfControlTemplateMapper mapper;
    private final ProtocolControlTemplateService protocolTemplateService;

    @Override
    public ResponseInformationObjectOfControlTemplateDto save(InformationObjectOfControlTemplateDto templateDto) {
        if (repository.existsByDocumentTypeIdAndDataName(templateDto.getDocumentTypeId(), templateDto.getDataName())) {
            throw new BadRequestException(
                    String.format("InformationObjectOfControlTemplate with documentTypeId=%s and dataName=%s is found"
                            , templateDto.getDocumentTypeId(), templateDto.getDataName()));
        }
        return mapper.mapToResponseInformationObjectOfControlTemplateDto(repository.save(
                mapper.mapToInformationAboutObjectOfControlTemplate(templateDto
                                        , protocolTemplateService.getByDocumentTypeId(templateDto.getDocumentTypeId()))
        ));
    }

    @Override
    public ResponseInformationObjectOfControlTemplateDto update(InformationObjectOfControlTemplateDto templateDto) {
        if (repository.existsById(templateDto.getId())) {
            return mapper.mapToResponseInformationObjectOfControlTemplateDto(repository.save(
                    mapper.mapToInformationAboutObjectOfControlTemplate(templateDto
                            , protocolTemplateService.getByDocumentTypeId(templateDto.getDocumentTypeId()))
            ));
        }
        throw new NotFoundException(
                String.format("InformationObjectOfControlTemplate with id=%s not found for update"
                                                                                , templateDto.getId()));
    }

    @Override
    public ResponseInformationObjectOfControlTemplateDto get(Long id) {
        return mapper.mapToResponseInformationObjectOfControlTemplateDto(getById(id));
    }

    @Override
    public InformationAboutObjectOfControlTemplate getById(Long id) {
        return repository.findById(id).orElseThrow(
                        () -> new NotFoundException(
                                String.format("InformationObjectOfControlTemplate with id=%s not found", id))
        );
    }

    @Override
    public List<ResponseInformationObjectOfControlTemplateDto> getAll(Long documentTypeId) {
        return repository.findByDocumentTypeId(documentTypeId)
                         .stream()
                         .map(mapper::mapToResponseInformationObjectOfControlTemplateDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(
                               String.format("InformationObjectOfControlTemplate with id=%s not found for delete", id));
    }
}