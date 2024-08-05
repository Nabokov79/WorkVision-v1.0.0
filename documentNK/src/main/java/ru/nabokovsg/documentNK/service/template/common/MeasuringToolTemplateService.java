package ru.nabokovsg.documentNK.service.template.common;

import ru.nabokovsg.documentNK.dto.template.common.measuringToolTemplate.MeasuringToolTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.measuringToolTemplate.ResponseMeasuringToolTemplateDto;

public interface MeasuringToolTemplateService {

    ResponseMeasuringToolTemplateDto save(MeasuringToolTemplateDto measuringTool);

    ResponseMeasuringToolTemplateDto update(MeasuringToolTemplateDto measuringTool);

    void delete(Long id);
}