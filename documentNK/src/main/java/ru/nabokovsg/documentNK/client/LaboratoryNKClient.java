package ru.nabokovsg.documentNK.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nabokovsg.documentNK.dto.client.templateCreate.DocumentTypeDto;
import ru.nabokovsg.documentNK.dto.client.templateCreate.DocumentationDto;
import ru.nabokovsg.documentNK.dto.client.templateCreate.LaboratoryCertificateDto;
import ru.nabokovsg.documentNK.dto.client.templateCreate.MeasuringToolDto;

import java.util.List;

@Component
public class LaboratoryNKClient {

    private final WebClient client;

    @Autowired
    public LaboratoryNKClient(@Qualifier(value = "webLaboratoryNK") WebClient client) {
        this.client = client;
    }

    public DocumentationDto getDocumentation(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToMono(DocumentationDto.class)
                .block();
    }

    public DocumentTypeDto getDocumentType(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToMono(DocumentTypeDto.class)
                .block();
    }

    public List<LaboratoryCertificateDto> getLaboratoryCertificate(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToFlux(LaboratoryCertificateDto.class)
                .buffer()
                .blockFirst();
    }

    public MeasuringToolDto getMeasuringTool(String path) {
        return client.get()
                .uri(path)
                .retrieve()
                .bodyToMono(MeasuringToolDto.class)
                .block();
    }

    public List<MeasuringToolDto> getMeasuringTools(String path, MultiValueMap<String, String> params) {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParams(params)
                        .build())
                .retrieve()
                .bodyToFlux(MeasuringToolDto.class)
                .buffer()
                .blockFirst();
    }
}