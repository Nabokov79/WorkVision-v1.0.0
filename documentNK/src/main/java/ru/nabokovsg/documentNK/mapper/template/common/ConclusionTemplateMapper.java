package ru.nabokovsg.documentNK.mapper.template.common;

import org.mapstruct.Mapper;
import ru.nabokovsg.documentNK.dto.template.common.conclusion.ConclusionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.conclusion.ResponseConclusionTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.ConclusionTemplate;

@Mapper(componentModel = "spring")
public interface ConclusionTemplateMapper {

    ConclusionTemplate mapToConclusionTemplate(ConclusionTemplateDto conclusionDto);

    ResponseConclusionTemplateDto mapToResponseConclusionTemplateDto(ConclusionTemplate conclusion);
}