package ru.nabokovsg.laboratoryNK.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nabokovsg.laboratoryNK.dto.documentNkService.DocumentCreationDataDto;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.ResponseMeasuringToolDto;

@Component
public class DocumentClient {

    private final WebClient client;

    @Autowired
    public DocumentClient(@Qualifier(value = "webDocument") WebClient client) {
        this.client = client;
    }

    public String createDocument(String path, DocumentCreationDataDto body) {
        return client.post()
                .uri(path)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public void addMeasuringTool(String path, ResponseMeasuringToolDto body) {
         client.post()
                .uri(path)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ResponseMeasuringToolDto.class)
                .block();
    }

    public void updateMeasuringTool(String path, ResponseMeasuringToolDto body) {
        client.patch()
                .uri(path)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ResponseMeasuringToolDto.class)
                .block();
    }
}
