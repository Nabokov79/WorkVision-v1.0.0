package ru.nabokovsg.documentNK.service.document.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.mapper.document.common.AppendicesMapper;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.Report;
import ru.nabokovsg.documentNK.model.template.common.AppendicesTemplate;
import ru.nabokovsg.documentNK.repository.document.common.AppendicesRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppendicesServiceImpl implements AppendicesService {

    private final AppendicesRepository repository;
    private final AppendicesMapper mapper;

    @Override
    public void saveForReport(Report report, Set<AppendicesTemplate> templates) {
        repository.saveAll(templates.stream()
                                    .map(a -> mapper.mapWithReport(a, report))
                                    .toList());
    }

    @Override
    public void saveForSurveyProtocol(SurveyProtocol protocol, Set<AppendicesTemplate> templates) {
        repository.saveAll(templates.stream()
                                    .map(a -> mapper.mapWithSurveyProtocol(a, protocol))
                                    .toList());
    }
}