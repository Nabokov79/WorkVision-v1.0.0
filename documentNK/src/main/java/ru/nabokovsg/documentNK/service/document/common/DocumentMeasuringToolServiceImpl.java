package ru.nabokovsg.documentNK.service.document.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.client.templateCreate.MeasuringToolDto;
import ru.nabokovsg.documentNK.dto.client.documentCreate.LaboratoryEmployeeDto;
import ru.nabokovsg.documentNK.mapper.document.common.DocumentMeasuringToolMapper;
import ru.nabokovsg.documentNK.model.document.common.DocumentMeasuringTool;
import ru.nabokovsg.documentNK.model.document.common.Subsection;
import ru.nabokovsg.documentNK.model.template.common.MeasuringToolTemplate;
import ru.nabokovsg.documentNK.repository.document.common.DocumentMeasuringToolRepository;
import ru.nabokovsg.documentNK.service.common.StringBuilderService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentMeasuringToolServiceImpl implements DocumentMeasuringToolService {

    private final DocumentMeasuringToolRepository repository;
    private final DocumentMeasuringToolMapper mapper;
    private final StringBuilderService stringBuilderService;
    private final DocumentNKClient client;

    @Override
    public void save(Subsection subsection
                   , List<LaboratoryEmployeeDto> employees
                   , List<MeasuringToolTemplate> templates) {
        repository.saveAll(
                getDocumentMeasuringTool(employees, templates).values()
                                                              .stream()
                                                              .map(t -> mapper.mapWithSubsection(t, subsection))
                                                              .toList()
        );
    }

    private Map<String, DocumentMeasuringTool> getDocumentMeasuringTool(List<LaboratoryEmployeeDto> employees
                                                                      , List<MeasuringToolTemplate> templates) {
        List<MeasuringToolDto> measuringTools = client.getMeasuringTools(employees.stream()
                                                                                  .map(LaboratoryEmployeeDto::getId)
                                                                                  .toList());
        Map<String, MeasuringToolTemplate> toolTemplates = templates.stream()
                .collect(Collectors.toMap(MeasuringToolTemplate::getTool, t -> t));
        Map<String, DocumentMeasuringTool> documentMeasuringTools = new HashMap<>();
        measuringTools.forEach(measuringTool -> {
            MeasuringToolTemplate template = toolTemplates.get(measuringTool.getToll());
            if (template != null) {
                if (measuringTool.getModel().equals(template.getModel())) {
                    documentMeasuringTools.put(template.getTool()
                            , mapper.mapToDocumentMeasuringTool(
                                    template.getSequentialNumber()
                                    , stringBuilderService.buildMeasuringTool(measuringTool)));
                } else {
                    documentMeasuringTools.put(template.getTool()
                            , mapper.mapToDocumentMeasuringTool(template.getSequentialNumber()
                                    , template.getMeasuringTool()));
                }
            }
        });
        return documentMeasuringTools;
    }
}