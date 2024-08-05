package ru.nabokovsg.gateway.client.diagnosedNK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.inspection.NewInspectionDto;

@Service
public class InspectionClient extends BaseClient {

    private static final String API_PREFIX = "/diagnosed/nk/measurement/inspection";
    private static final String DELIMITER = "/";

    @Autowired
    public InspectionClient(@Value("${diagnosedNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewInspectionDto inspectionDto) {
        return post(API_PREFIX, inspectionDto);
    }

    public Flux<Object> getAll(Long equipmentId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(equipmentId)));
    }
}