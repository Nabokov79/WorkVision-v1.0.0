package ru.nabokovsg.documentNK.service.template.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.client.templateCreate.DivisionDto;
import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.DivisionDataDto;
import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.ResponseSubsectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.SubsectionTemplateDto;
import ru.nabokovsg.documentNK.exceptions.BadRequestException;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.template.common.SubsectionTemplateMapper;
import ru.nabokovsg.documentNK.model.template.common.DivisionType;
import ru.nabokovsg.documentNK.model.template.common.SubsectionTemplate;
import ru.nabokovsg.documentNK.model.template.protocolControl.ProtocolControlTemplate;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ProtocolReportTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;
import ru.nabokovsg.documentNK.repository.template.common.SubsectionTemplateRepository;
import ru.nabokovsg.documentNK.service.common.StringBuilderService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubsectionTemplateServiceImpl implements SubsectionTemplateService {

    private final SubsectionTemplateRepository repository;
    private final SubsectionTemplateMapper mapper;
    private final TableTemplateService tableTemplateService;
    private final DocumentNKClient client;
    private final StringBuilderService stringBuilderService;

    @Override
    public ResponseSubsectionTemplateDto save(SubsectionTemplateDto subsectionDto) {
        return mapper.mapToResponseSubsectionTemplateDto(
                repository.save(createSubsectionTemplate(subsectionDto)));
    }

    @Override
    public ResponseSubsectionTemplateDto update(SubsectionTemplateDto subsectionDto) {
        SubsectionTemplate template = getById(subsectionDto.getId());
        if (template != null) {
            return mapper.mapToResponseSubsectionTemplateDto(
                    repository.save(mapper.mapToUpdateSubsectionTemplate(template, subsectionDto)));
        }
        throw new NotFoundException(
                String.format("Subsection template with id=%s not found for delete", subsectionDto.getId())
        );
    }

    @Override
    public ResponseSubsectionTemplateDto get(Long id) {
        return mapper.mapToResponseSubsectionTemplateDto(getById(id));
    }

    @Override
    public SubsectionTemplate getById(Long id) {
        return  repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Subsection template with id=%s not found", id)));
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Subsection template with id=%s not found for delete", id));
    }


    @Override
    public void addProtocolReportTemplate(ProtocolReportTemplate template, List<Long> subsectionTemplatesId) {
        repository.saveAll(getAllByIds(subsectionTemplatesId)
                .stream()
                .map(s -> mapper.mapWithProtocolReportTemplate(s, template))
                .toList());
    }

    @Override
    public void addSectionTemplate(SectionTemplate template, List<Long> subsectionTemplatesId) {
        repository.saveAll(getAllByIds(subsectionTemplatesId)
                .stream()
                .map(s -> mapper.mapWithSectionTemplate(s, template))
                .toList());
    }

    @Override
    public void addProtocolTemplate(SurveyProtocolTemplate template, List<Long> subsectionTemplatesId) {
        repository.saveAll(getAllByIds(subsectionTemplatesId)
                .stream()
                .map(s -> mapper.mapWithProtocolTemplate(s, template))
                .toList());
    }

    @Override
    public void addProtocolControlTemplate(ProtocolControlTemplate template, List<Long> subsectionTemplatesId) {
        repository.saveAll(getAllByIds(subsectionTemplatesId)
                .stream()
                .map(s -> mapper.mapWithProtocolControlTemplate(s, template))
                .toList());
    }

    private SubsectionTemplate createSubsectionTemplate(SubsectionTemplateDto subsectionDto) {
        SubsectionTemplate template = mapper.mapToSubsectionTemplate(subsectionDto);
        if (subsectionDto.getDivisionParam() != null) {
            template = setDivision(template, subsectionDto.getDivisionParam());
        }
        if (subsectionDto.getTableId() != null) {
            template = setTable(template, subsectionDto.getTableId());
        }
        return template;
    }

    private Set<SubsectionTemplate> getAllByIds(List<Long> ids) {
        Set<SubsectionTemplate> templates = new HashSet<>(repository.findAllById(ids));
        if (templates.isEmpty()) {
            throw new NotFoundException(String.format("Subsection template by ids=%s not found", ids));
        }
        return templates;
    }

    private SubsectionTemplate setDivision(SubsectionTemplate template, DivisionDataDto param) {
        DivisionDto division;
        switch (convertToDivisionType(param.getDivisionType())) {
            case BRANCH -> division = mapper.mapFromBranch(client.getBranch(param.getDivisionId()));

            case DEPARTMENT -> division = mapper.mapFromDepartment(client.getDepartment(param.getDivisionId()));
            default -> throw new BadRequestException(
                    String.format(String.format("Unknown divisionType=%s", param.getDivisionType()))
            );
        }
        return mapper.mapWithDivisionContact(template
                , stringBuilderService.buildDivision(param, division, client.getLaboratoryCertificate()));
    }

    private SubsectionTemplate setTable(SubsectionTemplate template, Long tableId) {
        return mapper.mapWithTableTemplate(template, tableTemplateService.getById(tableId));
    }

    protected DivisionType convertToDivisionType(String divisionType) {
        return DivisionType.from(divisionType)
                .orElseThrow(() -> new BadRequestException(
                        String.format("Unknown data format divisionType=%s", divisionType))
                );
    }
}