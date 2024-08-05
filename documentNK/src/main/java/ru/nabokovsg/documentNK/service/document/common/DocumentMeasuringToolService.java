package ru.nabokovsg.documentNK.service.document.common;

import ru.nabokovsg.documentNK.dto.client.documentCreate.LaboratoryEmployeeDto;
import ru.nabokovsg.documentNK.model.document.common.Subsection;
import ru.nabokovsg.documentNK.model.template.common.MeasuringToolTemplate;

import java.util.List;

public interface DocumentMeasuringToolService {

    void save(Subsection subsection
            , List<LaboratoryEmployeeDto> employees
            , List<MeasuringToolTemplate> measuringToolsTemplates);
}