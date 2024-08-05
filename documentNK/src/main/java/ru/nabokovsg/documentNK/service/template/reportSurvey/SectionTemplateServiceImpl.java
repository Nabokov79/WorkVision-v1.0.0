package ru.nabokovsg.documentNK.service.template.reportSurvey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.section.ResponseSectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.section.SectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.section.ShortResponseSectionTemplateDto;
import ru.nabokovsg.documentNK.exceptions.BadRequestException;
import ru.nabokovsg.documentNK.exceptions.NotFoundException;
import ru.nabokovsg.documentNK.mapper.template.reportSurvey.SectionTemplateMapper;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;
import ru.nabokovsg.documentNK.repository.template.reportSurvey.SectionTemplateRepository;
import ru.nabokovsg.documentNK.service.template.common.SubsectionTemplateService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionTemplateServiceImpl implements SectionTemplateService {

    private final SectionTemplateRepository repository;
    private final SectionTemplateMapper mapper;
    private final ReportTemplateService reportTemplateService;
    private final ProtocolReportTemplateService protocolReportTemplateService;
    private final SubsectionTemplateService subsectionTemplateService;

    @Override
    public ResponseSectionTemplateDto save(SectionTemplateDto sectionDto) {
        if (repository.existsByReportTemplateIdAndSectionName(sectionDto.getReportTemplateId()
                                                            , sectionDto.getSectionName())) {
            throw new BadRequestException(
                    String.format("SectionTemplate with Name=%s for report template with id=%s is found"
                                                                                    , sectionDto.getSectionName()
                                                                                    , sectionDto.getReportTemplateId())
            );
        }
        SectionTemplate template = repository.save(mapper.mapToNewSectionTemplate(sectionDto
                                                   , reportTemplateService.getById(sectionDto.getReportTemplateId())));
        protocolReportTemplateService.addSectionTemplate(template, sectionDto.getProtocolReportTemplatesId());
        subsectionTemplateService.addSectionTemplate(template, sectionDto.getSubsectionTemplatesId());
        return mapper.mapToResponseSectionTemplateDto(template);
    }

    @Override
    public ResponseSectionTemplateDto update(SectionTemplateDto sectionDto) {
        SectionTemplate template = getById(sectionDto.getId());
        if (template != null) {
            template = repository.save(mapper.mapToSectionTemplate(sectionDto, template));
            protocolReportTemplateService.addSectionTemplate(template, sectionDto.getProtocolReportTemplatesId());
            subsectionTemplateService.addSectionTemplate(template, sectionDto.getSubsectionTemplatesId());
            return mapper.mapToResponseSectionTemplateDto(template);
        }
        throw new NotFoundException(
                String.format("SectionTemplate with id=%s not found for update", sectionDto.getId())
        );
    }

    @Override
    public ResponseSectionTemplateDto get(Long id) {
        return mapper.mapToResponseSectionTemplateDto(getById(id));
    }

    @Override
    public List<ShortResponseSectionTemplateDto> getAll(Long id) {
        return repository.findByReportTemplateId(id)
                         .stream()
                         .map(mapper::mapToShortResponseSectionTemplateDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("SectionTemplate with id=%s not found for delete", id));
    }

    private SectionTemplate getById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format(String.format("Section template with id= %s not found", id))));
    }
}
