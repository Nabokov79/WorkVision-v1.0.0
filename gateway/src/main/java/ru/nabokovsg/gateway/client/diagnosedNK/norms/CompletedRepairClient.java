package ru.nabokovsg.gateway.client.diagnosedNK.norms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.BaseClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.completedRepair.NewCompletedRepairDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.completedRepair.UpdateCompletedRepairDto;

@Service
public class CompletedRepairClient extends BaseClient {

    private static final String API_PREFIX = "/diagnosed/nk/measurement/completed/repair";
    private static final String DELIMITER = "/";

    @Autowired
    public CompletedRepairClient(@Value("${diagnosedNK-server.url}") String baseUrl) {
        super(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

    public Mono<Object> save(NewCompletedRepairDto repairDto) {
        return post(API_PREFIX, repairDto);
    }

    public Mono<Object> update(UpdateCompletedRepairDto repairDto) {
        return patch(API_PREFIX, repairDto);
    }

    public Flux<Object> getAll(Long equipmentId) {
        return getAll(String.join(DELIMITER, API_PREFIX, "all", String.valueOf(equipmentId)));
    }

    public Mono<String> delete(Long id) {
        return delete(String.join(DELIMITER, API_PREFIX, String.valueOf(id)));
    }
}