package ru.nabokovsg.documentNK.service.template.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.template.common.measuringToolTemplate.MeasuringToolTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.measuringToolTemplate.ResponseMeasuringToolTemplateDto;
import ru.nabokovsg.documentNK.exceptions.BadRequestException;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.template.common.MeasuringToolTemplateMapper;
import ru.nabokovsg.documentNK.repository.template.common.MeasuringToolTemplateRepository;
import ru.nabokovsg.documentNK.service.common.StringBuilderService;

@Service
@RequiredArgsConstructor
public class MeasuringToolTemplateServiceImpl implements MeasuringToolTemplateService {

    private final MeasuringToolTemplateRepository repository;
    private final MeasuringToolTemplateMapper mapper;
    private final StringBuilderService stringBuilderService;
    private final DocumentNKClient client;
    private final SubsectionTemplateService subsectionService;

    @Override
    public ResponseMeasuringToolTemplateDto save(MeasuringToolTemplateDto measuringToolDto) {
        if (repository.existsByMeasuringToolId(measuringToolDto.getMeasuringToolId())) {
            throw new BadRequestException(
                    String.format("MeasuringTool template with id=%s is found", measuringToolDto.getMeasuringToolId()));
        }
        return mapper.mapToResponseMeasuringToolTemplateDto(repository.save(
                mapper.mapToMeasuringToolTemplate(measuringToolDto
                        , stringBuilderService.buildMeasuringTool(
                                client.getMeasuringTool(measuringToolDto.getMeasuringToolId())
                        )
                        , subsectionService.getById(measuringToolDto.getSubsectionId()))));
    }

    @Override
    public ResponseMeasuringToolTemplateDto update(MeasuringToolTemplateDto measuringToolDto) {
        if (repository.existsById(measuringToolDto.getId())) {
            return mapper.mapToResponseMeasuringToolTemplateDto(repository.save(
                    mapper.mapToMeasuringToolTemplate(measuringToolDto
                            , stringBuilderService.buildMeasuringTool(
                                    client.getMeasuringTool(measuringToolDto.getMeasuringToolId())
                            )
                            , subsectionService.getById(measuringToolDto.getSubsectionId()))));
        }
        throw new NotFoundException(
                String.format("MeasuringTool template with id=%s not found for update", measuringToolDto.getId()));
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("MeasuringTool template with id=%s not found for delete", id));
    }
}